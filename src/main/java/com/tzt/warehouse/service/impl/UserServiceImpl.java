package com.tzt.warehouse.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzt.warehouse.comm.Enum.WareHouseEnum;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.comm.exception.BusinessException;
import com.tzt.warehouse.comm.exception.ErrorCodeEnum;
import com.tzt.warehouse.comm.utlis.MinioUtils;
import com.tzt.warehouse.comm.utlis.RedisCache;
import com.tzt.warehouse.comm.utlis.StrUtils;
import com.tzt.warehouse.comm.utlis.UserUtlis;
import com.tzt.warehouse.entity.LoginUser;
import com.tzt.warehouse.entity.SysUserRole;
import com.tzt.warehouse.entity.User;
import com.tzt.warehouse.entity.dto.*;
import com.tzt.warehouse.entity.vo.UserVo;
import com.tzt.warehouse.mapper.UserDao;
import com.tzt.warehouse.service.DeptPositionService;
import com.tzt.warehouse.service.SysUserRoleService;
import com.tzt.warehouse.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author：帅气的汤
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
    @Resource
    private BCryptPasswordEncoder passwordEncoder;
    @Resource
    private RedisCache redisCache;
    @Resource
    private MinioUtils minioUtils;

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Resource
    private DeptPositionService deptPositionService;
    @Value("${minio.bucketName}")
    private String bucketName;
    @Value("${minio.endpoint}")
    private String endpoint;

    @Override
    public ResponseResult<Object> register(RegisterDto registerDto) {
        User user = new User();
        BeanUtil.copyProperties(registerDto, user);
        user.setPassword(new BCryptPasswordEncoder().encode(registerDto.getPassword()));
        user.setStatus("2");
        if (checkIdCard(user.getIdCard())) {
            try {
                this.save(user);
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(user.getId()).setRoleId("2");
                sysUserRoleService.save(sysUserRole);
            } catch (Exception e) {
                log.error("注册失败，失败原因：{}", user, e);
            }
            return ResponseResult.success();
        }
        return new ResponseResult<>(ErrorCodeEnum.USER_EXIST);
    }

    public static void main(String[] args) {
        String admin = new BCryptPasswordEncoder().encode("admin");
        System.out.println(admin);
    }

    @Override
    public ResponseResult<Object> updateUser(User user) {
        if (!StringUtils.hasText(user.getId())) {
            return new ResponseResult<>(ErrorCodeEnum.NULL_ID);
        }
        // 如果要修改密码。先把明文密码加密 在存
        if (StringUtils.hasText(user.getPassword())) {
            User one = this.getOne(new LambdaQueryWrapper<User>().eq(User::getId, user.getId()));
            if (!user.getPassword().equals(one.getPassword())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }
        this.updateById(user);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> userList(UserDto userDto) {
        Page<UserVo> userList = baseMapper.getUserList(new Page<>(userDto.getCurrent(),userDto.getSize()),userDto);
        return ResponseResult.success(userList);
    }

    @Override
    public ResponseResult<Object> updatePassword(PasswordDto passwordDto) {
        LoginUser loginUser = UserUtlis.get();

        if (!passwordEncoder.matches(passwordDto.getOldPassword(), loginUser.getPassword())) {
            return new ResponseResult<>(ErrorCodeEnum.ACCOUNT_ERROR);
        }
        User user = new User().setId(loginUser.getUser().getId())
                .setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
        updateUser(user);
        redisCache.deleteObject(WareHouseEnum.LOGIN_KEY + user.getId());
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> updateAvatar(MultipartFile avatar) {
        User userCache = UserUtlis.get().getUser();
        log.info("登录的用户信息：{}", JSON.toJSON(userCache));
        if (!StrUtils.checkFileType(avatar)) {
            return new ResponseResult<>(ErrorCodeEnum.SAVE_ERROR);
        }
        String originalFilename = avatar.getOriginalFilename();
        String avatarName = IdUtil.simpleUUID() + originalFilename;
        try {
            minioUtils.putObject(bucketName, avatar, avatarName, avatar.getContentType());
        } catch (Exception e) {
            log.error("上传到minio失败：{}",e.getMessage());
            throw new BusinessException(ErrorCodeEnum.SAVE_ERROR);
        }
        String avatarUrl = endpoint + "/" + bucketName + "/" + avatarName;
        log.info("上传图片成功，图片地址：{}",avatarUrl);
        if (StringUtils.hasText(userCache.getAvatar())){
            log.info("开始删除用户原始头像");
            try {
                User one = this.getOne(new LambdaQueryWrapper<User>().eq(User::getId, userCache.getId()));
                String[] split = one.getAvatar().split("/");

                String oldAvatar = split[split.length-1];
                minioUtils.removeObject(bucketName, oldAvatar);
            } catch (Exception e) {
                log.info("删除用户头像异常：{}",userCache.getId());
                throw new RuntimeException(e);
            }
        }

        userCache.setAvatar(avatarUrl);
        this.updateById(userCache);

        // 从缓存中取到user 更新缓存
        // LoginUser loginUser = (LoginUser)redisCache.getCacheObject(WareHouseEnum.LOGIN_KEY + user.getId());
        // loginUser.setUser(user);
        // redisCache.setCacheObject(WareHouseEnum.LOGIN_KEY + user.getId(), loginUser,1, TimeUnit.HOURS);

        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> newRegister(SearchDTO searchDTO) {
        Page<User> page = this.page(new Page<>(searchDTO.getCurrent(), searchDTO.getSize()), new LambdaQueryWrapper<User>().eq(User::getStatus, "2"));
        return new ResponseResult(page);
    }

    /**
     * 修改用户部门职位
     */
    @Override
    public ResponseResult<Object> updateUserDept(UserDeptDto userDeptDto) {
        boolean update = this.update(new LambdaUpdateWrapper<User>()
                .eq(User::getId, userDeptDto.getUserId())
                .set(StrUtil.isNotBlank(userDeptDto.getDeptId()), User::getDeptId, userDeptDto.getDeptId())
                .set(StrUtil.isNotBlank(userDeptDto.getPositionId()), User::getPositionId, userDeptDto.getPositionId()));
        return ResponseResult.success(update);
    }

    /**
     * 检测身份证是否存在
     *
     * @param idCard 身份证
     * @return
     */
    private Boolean checkIdCard(String idCard) {
        User user = getOne(new QueryWrapper<User>().eq("id_card", idCard));
        return ObjectUtil.isEmpty(user);
    }
}

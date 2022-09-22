package com.tzt.warehouse.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzt.warehouse.comm.Enum.WareHouseEnum;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.comm.exception.ErrorCodeEnum;
import com.tzt.warehouse.comm.utlis.MinioUtils;
import com.tzt.warehouse.comm.utlis.RedisCache;
import com.tzt.warehouse.comm.utlis.StrUtils;
import com.tzt.warehouse.comm.utlis.UserUtlis;
import com.tzt.warehouse.entity.LoginUser;
import com.tzt.warehouse.entity.SysUserRole;
import com.tzt.warehouse.entity.User;
import com.tzt.warehouse.entity.dto.PasswordDto;
import com.tzt.warehouse.entity.dto.RegisterDto;
import com.tzt.warehouse.entity.dto.SearchDTO;
import com.tzt.warehouse.entity.dto.UserDto;
import com.tzt.warehouse.mapper.UserDao;
import com.tzt.warehouse.service.SysUserRoleService;
import com.tzt.warehouse.service.UserService;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public ResponseResult<Object> updateUser(User user) {
        if (!StringUtils.hasText(user.getId())) {
            return new ResponseResult<>(ErrorCodeEnum.NULL_ID);
        }
        // 如果要修改密码。先把明文密码加密 在存
        if (StringUtils.hasText(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        this.updateById(user);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> userList(UserDto userDto) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(userDto.getUserName()), User::getUserName, userDto.getUserName());
        wrapper.like(StringUtils.hasText(userDto.getPhone()), User::getPhone, userDto.getPhone());
        wrapper.like(StringUtils.hasText(userDto.getIdCard()), User::getIdCard, userDto.getIdCard());
        wrapper.eq(StringUtils.hasText(userDto.getSex()), User::getSex, userDto.getSex());
        wrapper.eq(StringUtils.hasText(userDto.getUserType()), User::getUserType, userDto.getUserType());
        wrapper.eq(StringUtils.hasText(userDto.getDepartment()), User::getDepartment, userDto.getDepartment());
        wrapper.eq(StringUtils.hasText(userDto.getPosition()), User::getPosition, userDto.getPosition());
        wrapper.eq(StringUtils.hasText(userDto.getStatus()), User::getStatus, userDto.getStatus());
        wrapper.between(StringUtils.hasText(userDto.getMax()) && StringUtils.hasText(userDto.getMin()), User::getSalary, userDto.getMax(), userDto.getMin());
        Page<User> page = this.page(new Page<>(userDto.getCurrent(), userDto.getSize()), wrapper);
        return new ResponseResult(page);
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
        if (!StrUtils.checkFileType(avatar)) {
            return new ResponseResult<>(ErrorCodeEnum.SAVE_ERROR);
        }
        String originalFilename = avatar.getOriginalFilename();

        try {

            minioUtils.putObject("text", avatar, avatar.getName(), avatar.getContentType());

            // File localFile = new File("E:\\web-code",originalFilename);
            //获得项目的类路径
            // String path = ResourceUtils.getURL("classpath:").getPath();
            // // //空文件夹在编译时不会打包进入target中
            // // File uploadDir = new File(path+"/static/avatar/user");
            // // if (!uploadDir.exists()) {
            // //     System.out.println("上传头像路径不存在，正在创建...");
            // //     uploadDir.mkdir();
            // // }
            // String fileSeparator = System.getProperty("file.separator");
            // File file = new File(System.getProperty("user.dir") + fileSeparator + "a.txt");
            // file.createNewFile();
            // File localFile = new File(path + "/static/" ,avatar.getOriginalFilename());
            // localFile.createNewFile();
            // String absolutePath = localFile.getAbsolutePath();
            //
            //
            // String fileName = System.currentTimeMillis() / 1000 + "-" + avatar.getOriginalFilename();
            // FileOutputStream outputStream = new FileOutputStream(localFile);
            // outputStream.write(avatar.getBytes());
            // outputStream.close();
            // avatar.transferTo(localFile);
        } catch (Exception e) {
            e.printStackTrace();

        }
        log.info("文件名：{}", originalFilename);
        return null;
    }

    @Override
    public ResponseResult<Object> newRegister(SearchDTO searchDTO) {
        Page<User> page = this.page(new Page<>(searchDTO.getCurrent(), searchDTO.getSize()), new LambdaQueryWrapper<User>().eq(User::getStatus, "2"));
        return new ResponseResult(page);
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

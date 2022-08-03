package com.tzt.warehouse.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzt.warehouse.comm.Enum.WareHouseEnum;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.comm.exception.ErrorCodeEnum;
import com.tzt.warehouse.comm.utlis.RedisCache;
import com.tzt.warehouse.comm.utlis.UserUtlis;
import com.tzt.warehouse.dao.UserDao;
import com.tzt.warehouse.entity.LoginUser;
import com.tzt.warehouse.entity.User;
import com.tzt.warehouse.entity.dto.PasswordDto;
import com.tzt.warehouse.entity.dto.RegisterDto;
import com.tzt.warehouse.entity.dto.UserDto;
import com.tzt.warehouse.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Override
    public ResponseResult<Object> register(RegisterDto registerDto) {
        User user = new User();
        BeanUtil.copyProperties(registerDto, user);
        user.setPassword(new BCryptPasswordEncoder().encode(registerDto.getPassword()));
        user.setStatus("2");
        if (checkIdCard(user.getIdCard())) {
            try {
                this.save(user);
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
        if (StringUtils.hasText(user.getPassword())){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        this.updateById(user);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult<Object> userList(UserDto userDto) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.hasText(userDto.getUserName()), "user_name", userDto.getUserName());
        wrapper.like(StringUtils.hasText(userDto.getPhone()), "phone", userDto.getPhone());
        wrapper.like(StringUtils.hasText(userDto.getIdCard()), "id_card", userDto.getIdCard());
        wrapper.eq(StringUtils.hasText(userDto.getSex()), "sex", userDto.getSex());
        wrapper.eq(StringUtils.hasText(userDto.getUserType()), "user_type", userDto.getUserType());
        wrapper.eq(StringUtils.hasText(userDto.getDepartment()), "department", userDto.getDepartment());
        wrapper.eq(StringUtils.hasText(userDto.getPosition()), "position", userDto.getPosition());
        wrapper.between(StringUtils.hasText(userDto.getMax()) && StringUtils.hasText(userDto.getMin()), "salary", userDto.getMax(), userDto.getMin());
        return new ResponseResult(this.page(new Page<>(userDto.getCurrent(), userDto.getSize()), wrapper));
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

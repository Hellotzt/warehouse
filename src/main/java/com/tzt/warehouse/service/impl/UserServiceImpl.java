package com.tzt.warehouse.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.comm.exception.ErrorCodeEnum;
import com.tzt.warehouse.dao.UserDao;
import com.tzt.warehouse.entity.User;
import com.tzt.warehouse.entity.dto.UserDto;
import com.tzt.warehouse.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author：帅气的汤
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService  {
    @Override
    public ResponseResult<Object> register(UserDto userDto) {
        User user = new User();
        BeanUtil.copyProperties(userDto, user);
        user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        user.setStatus("2");
        if (checkIdCard(user.getIdCard())) {
            try {
                this.save(user);
            } catch (Exception e) {
                log.error("注册失败，失败原因：{}",user,e);
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
        this.updateById(user);
        return ResponseResult.success();
    }

    /**
     * 检测身份证是否存在
     * @param idCard 身份证
     * @return
     */
    private Boolean checkIdCard(String idCard){
        User user = getOne(new QueryWrapper<User>().eq("id_card", idCard));
        return ObjectUtil.isEmpty(user);
    }
}

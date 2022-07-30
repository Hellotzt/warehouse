package com.tzt.warehouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.entity.User;
import com.tzt.warehouse.entity.dto.UserDto;

/**
 * @author 帅气的汤
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param userDto 用户信息
     * @return
     */
    ResponseResult<Object> register(UserDto userDto);

    /**
     * 管理员修改用户
     * @param user 用户信息
     * @return
     */
    ResponseResult<Object> updateUser(User user);
}
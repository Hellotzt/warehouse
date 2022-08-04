package com.tzt.warehouse.controller;

import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.entity.User;
import com.tzt.warehouse.entity.dto.PasswordDto;
import com.tzt.warehouse.entity.dto.RegisterDto;
import com.tzt.warehouse.entity.dto.UserDto;
import com.tzt.warehouse.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author：帅气的汤
 */
@RestController
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/user/register")
    public ResponseResult<Object> register(@RequestBody @Valid RegisterDto registerDto){
        return userService.register(registerDto);
    }

    /**
     * 管理员修改用户信息
     */
    @PostMapping("/user/updateUser")
    @PreAuthorize("hasAuthority('system:user:update')")
    public ResponseResult<Object> updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    /**
     * 用户列表
     */
    @PostMapping("/user/userList")
    @PreAuthorize("hasAuthority('system:user:update')")
    public ResponseResult<Object> userList(@RequestBody UserDto userDto){
        return userService.userList(userDto);
    }

    /**
     * 修改密码
     */
    @PostMapping("/user/updatePassword")
    public ResponseResult<Object> updatePassword(@RequestBody@Valid PasswordDto passwordDto){
        return userService.updatePassword(passwordDto);
    }
}

package com.tzt.warehouse.controller;

import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.entity.User;
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

    @PostMapping("/user/register")
    public ResponseResult<Object> register(@RequestBody @Valid UserDto userDto){
        return userService.register(userDto);
    }

    @PostMapping("/user/updateUser")
    @PreAuthorize("hasAuthority('system:user:update')")
    public ResponseResult<Object> updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }
}

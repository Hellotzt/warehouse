package com.tzt.warehouse.controller;

import cn.hutool.core.util.ObjectUtil;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.comm.exception.ErrorCodeEnum;
import com.tzt.warehouse.entity.User;
import com.tzt.warehouse.entity.dto.PasswordDto;
import com.tzt.warehouse.entity.dto.RegisterDto;
import com.tzt.warehouse.entity.dto.SearchDTO;
import com.tzt.warehouse.entity.dto.UserDto;
import com.tzt.warehouse.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;

/**
 * @author：帅气的汤
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public ResponseResult<Object> register(@RequestBody @Valid RegisterDto registerDto){
        return userService.register(registerDto);
    }

    /**
     * 管理员修改用户信息
     */
    @PostMapping("/updateUser")
    @PreAuthorize("hasAuthority('system:user:update')")
    public ResponseResult<Object> updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    /**
     * 用户列表
     */
    @PostMapping("/userList")
    @PreAuthorize("hasAuthority('system:user:update')")
    public ResponseResult<Object> userList(@RequestBody UserDto userDto){
        return userService.userList(userDto);
    }

    /**
     * 修改用户头像
     */
    @PostMapping("/updateAvatar")
    // @PreAuthorize("hasAuthority('system:user:update')")
    public ResponseResult<Object> updateAvatar(@RequestBody MultipartFile avatar) throws IOException {
        if (ObjectUtil.isEmpty(avatar)){
            return new ResponseResult<>(ErrorCodeEnum.ACCOUNT_NULL);
        }
        return userService.updateAvatar(avatar);
    }

    /**
     * 修改密码
     */
    @PostMapping("/updatePassword")
    public ResponseResult<Object> updatePassword(@RequestBody@Valid PasswordDto passwordDto){
        return userService.updatePassword(passwordDto);
    }

    /**
     * 查询新注册
     */
    @PostMapping("/newRegister")
    public ResponseResult<Object> newRegister(@RequestBody SearchDTO searchDTO){
        return userService.newRegister(searchDTO);
    }
}

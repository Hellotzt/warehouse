package com.tzt.warehouse.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.entity.User;
import com.tzt.warehouse.entity.dto.PasswordDto;
import com.tzt.warehouse.entity.dto.RegisterDto;
import com.tzt.warehouse.entity.dto.SearchDTO;
import com.tzt.warehouse.entity.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 帅气的汤
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     * @param userDto 用户信息
     * @return
     */
    ResponseResult<Object> register(RegisterDto registerDto);

    /**
     * 管理员修改用户
     * @param user 用户信息
     * @return
     */
    ResponseResult<Object> updateUser(User user);

    /**
     * 用户列表
     * @param userDto
     * @return
     */
    ResponseResult<Object> userList(UserDto userDto);

    /**
     * 修改密码
     * @param passwordDto
     * @return
     */
    ResponseResult<Object> updatePassword(PasswordDto passwordDto);

    /**
     * 修改用户头像
     * @param avatar
     * @return
     */
    ResponseResult<Object> updateAvatar(MultipartFile avatar) throws IOException;

    ResponseResult<Object> newRegister(SearchDTO searchDTO);
}

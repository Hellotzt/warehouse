package com.tzt.warehouse.service;

import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.entity.dto.LoginDto;

import javax.servlet.http.HttpServletRequest;

public interface LoginService {
    ResponseResult<Object> login(LoginDto loginDto, HttpServletRequest request);

    ResponseResult logout(String refreshToken);

    ResponseResult<Object> refreshToken(String refreshToken);
}

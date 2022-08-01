package com.tzt.warehouse.comm.handler;

import com.alibaba.fastjson.JSON;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.comm.utlis.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author：帅气的汤
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ResponseResult result = new ResponseResult(HttpStatus.UNAUTHORIZED.value(), "用户认证失败请重新登陆");
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response,json);
    }
}

package com.tzt.warehouse.service.impl;

import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.comm.exception.BusinessException;
import com.tzt.warehouse.comm.exception.ErrorCodeEnum;
import com.tzt.warehouse.comm.utlis.IpUtil;
import com.tzt.warehouse.comm.utlis.JwtUtil;
import com.tzt.warehouse.comm.utlis.RedisCache;
import com.tzt.warehouse.entity.LoginUser;
import com.tzt.warehouse.entity.dto.LoginDto;
import com.tzt.warehouse.service.LoginService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author：帅气的汤
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisCache redisCache;

    @Override
    public ResponseResult<Object> login(LoginDto loginDto,HttpServletRequest request) {
        //验证码校验
        String verify = null;
        try {
            verify = redisCache.getCacheObject("verify:"+IpUtil.getIpAddress(request)).toString();
        } catch (Exception e) {
                return new ResponseResult<>(ErrorCodeEnum.VERIFY_ERROR);
        }
        if (StringUtils.hasText(verify) ){
            if (!loginDto.getVerify().equals(verify)) {
                return new ResponseResult<>(ErrorCodeEnum.VERIFY_ERROR);
            }
            redisCache.deleteObject("verify:"+IpUtil.getIpAddress(request));
        }

        SecurityContextHolder.getContext().getAuthentication();
        // RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // HttpServletRequest request = null;
        // if (requestAttributes != null) {
        //     request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // }
        //
        //
        // if (request == null) {
        //     return null;
        // }
        // 获取到token  在处理就是了


        //AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getIdCard(),loginDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //如果认证没通过，给出对应的提示
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //如果认证通过了 用userid生成一个jwt jwt存入ResponseResult返回
        Object principal = authenticate.getPrincipal();
        System.out.println("--------"+principal);
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把完整的用户信息存入redis， userId作为key
        redisCache.setCacheObject("login:"+userId, loginUser);
        HashMap<Object, Object> map = new HashMap<>(4);
        map.put("token", jwt);
        map.put("userType",loginUser.getUser().getUserType());
        return new ResponseResult(0,map );
    }

    @Override
    public ResponseResult logout() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getUser().getId();
        redisCache.deleteObject("login:"+userId);
        return new ResponseResult(200, "注销成功");
    }

    public void checkVerify(String newVerify, String sessionVerify) {
        if (sessionVerify != null) {
            if (!newVerify.equals(sessionVerify)) {
                System.out.println("验证码错误");
                throw new BusinessException(ErrorCodeEnum.VERIFY_ERROR);
            }
        } else {
            throw new BusinessException(ErrorCodeEnum.VERIFY_ERROR);
        }
    }
}

package com.tzt.warehouse.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.tzt.warehouse.comm.Enum.WareHouseEnum;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.comm.exception.BusinessException;
import com.tzt.warehouse.comm.exception.ErrorCodeEnum;
import com.tzt.warehouse.comm.utlis.IpUtil;
import com.tzt.warehouse.comm.utlis.JwtUtil;
import com.tzt.warehouse.comm.utlis.RedisCache;
import com.tzt.warehouse.entity.LoginUser;
import com.tzt.warehouse.entity.dto.LoginDto;
import com.tzt.warehouse.service.LoginService;
import com.tzt.warehouse.service.SysUserRoleService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author：帅气的汤
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisCache redisCache;
    @Resource
    private SysUserRoleService sysUserRoleService;

    @Override
    public ResponseResult<Object> login(LoginDto loginDto, HttpServletRequest request) {
        log.info("请求登录接口报文：{}", JSON.toJSON(loginDto));
        //验证码校验
        String verify = null;
        try {
            verify = redisCache.getCacheObject("verify:" + IpUtil.getIpAddress(request)).toString();
        } catch (Exception e) {
            return new ResponseResult<>(ErrorCodeEnum.VERIFY_ERROR);
        }
        if (StringUtils.hasText(verify)) {
            if (!loginDto.getVerify().equals(verify)) {
                return new ResponseResult<>(ErrorCodeEnum.VERIFY_ERROR);
            }
            redisCache.deleteObject("verify:" + IpUtil.getIpAddress(request));
        }
        //AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getIdCard(), loginDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //如果认证没通过，给出对应的提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }
        //如果认证通过了 用userid生成一个jwt jwt存入ResponseResult返回
        Object principal = authenticate.getPrincipal();
        System.out.println("--------" + principal);
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId();
        String jwt = JwtUtil.createJWT(userId);

        String refreshToken = JwtUtil.createLongJwt(userId);
        //把完整的用户信息存入redis， userId作为key
        redisCache.setCacheObject(WareHouseEnum.LOGIN_KEY + userId, loginUser, 1, TimeUnit.HOURS);
        redisCache.setCacheObject(WareHouseEnum.LONG_TOKEN + refreshToken, loginUser, 14, TimeUnit.DAYS);
        HashMap<Object, Object> map = new HashMap<>(6);
        map.put("token", jwt);
        map.put("refreshToken", refreshToken);
        String roleId = sysUserRoleService.getById(userId).getRoleId();
        map.put("userType", roleId);
        log.info("登录成功:{}", JSON.toJSON(map));
        return new ResponseResult(200, map);
    }

    @Override
    public ResponseResult logout(String refreshToken) {
        Claims claims = null;
        try {
            claims = JwtUtil.parseLongJWT(refreshToken);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodeEnum.TOKEN_ERROR);
        }
        String userId = claims.getSubject();
        redisCache.deleteObject(WareHouseEnum.LOGIN_KEY + userId);
        redisCache.deleteObject(WareHouseEnum.LONG_TOKEN + refreshToken);
        return new ResponseResult("注销成功");
    }

    @Override
    public ResponseResult<Object> refreshToken(String refreshToken) {
        String userId;
        Claims claims = null;
        try {
            claims = JwtUtil.parseLongJWT(refreshToken);
        } catch (Exception e) {
            throw new BusinessException(ErrorCodeEnum.TOKEN_ERROR);
        }
        userId = claims.getSubject();
        LoginUser loginUser = (LoginUser) redisCache.getCacheObject(WareHouseEnum.LONG_TOKEN + refreshToken);
        if (ObjectUtil.isNull(loginUser)){
            return new ResponseResult<>(ErrorCodeEnum.TOKEN_ERROR);
        }
        redisCache.deleteObject(WareHouseEnum.LONG_TOKEN + refreshToken);
        redisCache.setCacheObject(WareHouseEnum.LOGIN_KEY + userId, loginUser, 1, TimeUnit.HOURS);

        String jwt = JwtUtil.createJWT(userId);
        // 长token过期时间14天

        String token = JwtUtil.createLongJwt(userId);
        redisCache.setCacheObject(WareHouseEnum.LONG_TOKEN + token, loginUser.getUser(), 14, TimeUnit.DAYS);
        HashMap<Object, Object> map = new HashMap<>(6);
        map.put("token", jwt);
        map.put("refreshToken", token);
        String roleId = sysUserRoleService.getById(userId).getRoleId();
        map.put("userType", roleId);
        log.info("登录成功:{}", JSON.toJSON(map));
        return new ResponseResult( map);
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

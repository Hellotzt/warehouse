package com.tzt.warehouse.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.tzt.warehouse.comm.base.ResponseResult;
import com.tzt.warehouse.comm.utlis.IpUtil;
import com.tzt.warehouse.comm.utlis.MinioUtils;
import com.tzt.warehouse.entity.dto.LoginDto;
import com.tzt.warehouse.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author：帅气的汤
 */
@RestController
@Slf4j
public class LoginController {
    @Resource
    private LoginService loginService;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private MinioUtils minioUtils;

    @PostMapping("/user/login")
    public ResponseResult<Object> login(@RequestBody @Valid LoginDto loginDto, HttpServletRequest request){

        // try {
        //     minioUtils.createBucket("test2");
        // } catch (Exception e) {
        //     throw new RuntimeException(e);
        // }
        // System.out.println(basisUrl);
        return loginService.login(loginDto,request);
    }

    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }

    @GetMapping("/verify")
    public void verify(HttpServletResponse response,HttpServletRequest request) {
        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 10);
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");

        try {
            // 输出到页面
            captcha.write(response.getOutputStream());
            String ip = IpUtil.getIpAddress(request);

            redisTemplate.opsForValue().set("verify:"+ip, captcha.getCode());
            redisTemplate.expire("verify:"+ip, 120, TimeUnit.SECONDS);
            // 关闭流
            response.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

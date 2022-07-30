package com.tzt.warehouse.config;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzt.warehouse.entity.User;
import com.tzt.warehouse.service.UserService;
import com.tzt.warehouse.service.impl.MyUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author ：jerry
 * @date ：Created in 2022/1/26 14:50
 * @description：自定义密码验证规则
 * @version: V1.1
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    @Lazy
    private UserService userService;

    @Resource
    @Lazy
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private MyUserDetailsServiceImpl userDetailsService;


    /**
     * 自定义密码验证
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String idCard = authentication.getName();
        //表单提交的用户名
        String presentedPassword = (String)authentication.getCredentials();
        // 根据用户名获取用户信息
        User sysUser = userService.getOne(new QueryWrapper<User>().eq("id_card", idCard));
        if (ObjectUtil.isEmpty(sysUser)) {
            throw new BadCredentialsException("用户不存在");
        } else {
            UserDetails userDetails = userDetailsService.loadUserByUsername(sysUser.getUserName());

            if (authentication.getCredentials() == null) {
                throw new BadCredentialsException("凭证为空");
            } else if (!passwordEncoder.matches(presentedPassword, sysUser.getPassword())) {
                System.out.println("encodedPassword:"+presentedPassword);
                System.out.println("password:"+sysUser.getPassword());
                throw new BadCredentialsException("密码错误");
            } else {
                UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(), userDetails.getAuthorities());
                result.setDetails(authentication.getDetails());
                return result;
            }
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

}


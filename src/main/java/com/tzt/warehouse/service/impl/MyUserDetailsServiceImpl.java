package com.tzt.warehouse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tzt.warehouse.mapper.MenuDao;
import com.tzt.warehouse.mapper.UserDao;
import com.tzt.warehouse.entity.LoginUser;
import com.tzt.warehouse.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author：帅气的汤
 */
@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserDao userDao;
    @Resource
    private MenuDao menuDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户信息

        User user = userDao.selectOne(new QueryWrapper<User>().eq("user_name", username));
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误");
        }
        List<String> list = menuDao.selectParamsByUserId(user.getId());

        return new LoginUser(user,list);
    }
}

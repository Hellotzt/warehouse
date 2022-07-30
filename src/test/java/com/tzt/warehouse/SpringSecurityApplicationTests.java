package com.tzt.warehouse;

import com.tzt.warehouse.dao.MenuDao;
import com.tzt.warehouse.dao.UserDao;
import com.tzt.warehouse.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class SpringSecurityApplicationTests {
    @Resource
    protected UserDao userDao;
    @Resource
    private MenuDao menuDao;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Test
    void test() {
        System.out.println(menuDao.selectParamsByUserId(1L));
    }

    @Test
    void contextLoads() {
        System.out.println(passwordEncoder.encode("admin"));
        List<User> users = userDao.selectList(null);
        System.out.println(users);
    }

}

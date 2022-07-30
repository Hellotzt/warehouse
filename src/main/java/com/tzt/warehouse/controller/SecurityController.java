package com.tzt.warehouse.controller;

import com.tzt.warehouse.entity.LoginUser;
import com.tzt.warehouse.comm.utlis.UserUtlis;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author：帅气的汤
 */
@RestController
public class SecurityController {

    @GetMapping("/test")
    // @PreAuthorize("hasAuthority('system:dept:list222')")
    // @PreAuthorize("@ex.hasAuthority('system:dept:list')")   // 获取容器中bean的名字为ex的对象
    public String hello(){
        LoginUser loginUser1 = UserUtlis.get();
        System.out.println("你好");
        return "hello security!";
    }

    @GetMapping("/test/1")
    // @PreAuthorize("hasAuthority('system:dept:list222')")
    // @PreAuthorize("@ex.hasAuthority('system:dept:list')")   // 获取容器中bean的名字为ex的对象
    public String hello1(){
        LoginUser loginUser1 = UserUtlis.get();
        System.out.println("你好");
        return "hello security!1";
    }

    @GetMapping("/test/2")
    @PreAuthorize("hasAuthority('system:dept:list222')")
    // @PreAuthorize("@ex.hasAuthority('system:dept:list')")   // 获取容器中bean的名字为ex的对象
    public String hello2(){
        System.out.println("你好");
        return "hello security!2";
    }
}

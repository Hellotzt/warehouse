package com.tzt.warehouse.comm.utlis;

import com.tzt.warehouse.entity.LoginUser;

public class UserUtlis {
    private UserUtlis(){
    }

    private static final ThreadLocal<LoginUser> LOCAL = new ThreadLocal();

    public static void set(LoginUser userParams){
        LOCAL.set(userParams);;
    }

    public static LoginUser get(){
        return  LOCAL.get();
    }

    public static void remove(){
        LOCAL.remove();
    }

}
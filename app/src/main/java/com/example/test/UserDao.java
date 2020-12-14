package com.example.test;


import android.content.Context;

/**
 *这是一个用户登录注册接口
 * @author Asus
 */
public interface UserDao {
    /**
     * 这是判断登录是否成功的方法
     * @param username 用户名
     * @param password 用户的密码
     * @return  用户是否登录成功
     */
    public abstract boolean isloging(Context context, String username, String password) ;
    /**
     * 这是注册方法
     *
     */
    public abstract boolean register(Context context, User user);

}

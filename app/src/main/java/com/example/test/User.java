package com.example.test;

/**
 *
 * @author Asus
 */
public class User {
    private String username;//用户名
    private String password;//用户密码
    public User(){}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


}

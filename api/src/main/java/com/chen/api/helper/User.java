package com.chen.api.helper;

import java.io.Serializable;

/**
 * Author: Chen
 * Date: 2018/6/6
 * Desc:
 */
public class User implements Serializable {
    private String userId;//用户ID
    private String userName;//用户昵称
    private String token;//token

    public User(String userId, String userName, String token) {
        this.userId = userId;
        this.userName = userName;
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

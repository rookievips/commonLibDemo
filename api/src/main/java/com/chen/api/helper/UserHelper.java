package com.chen.api.helper;

import android.text.TextUtils;

import com.chen.api.utils.SPUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Author: Chen
 * Date: 2018/7/4
 * Desc:
 */
public class UserHelper extends SPUtil {

    private static final String KEY_LOGIN = "KEY_LOGIN";
    private static final String KEY_USER_INFO = "KEY_USER_INFO";

    /**
     * 登录
     *
     * @param user
     */
    public static void login(User user) {
        saveUser(user);
        setSharedBooleanData(KEY_LOGIN, true);
    }

    /**
     * 登出
     */
    public static void logout() {
        clear();
        deleteUser();
        setSharedBooleanData(KEY_LOGIN, false);
    }

    /**
     * 查询登录状态,默认false
     *
     * @return
     */
    public static boolean isLogin() {
        return getSharedBooleanData(KEY_LOGIN, false);
    }

    /**
     * 获取本地用户数据
     *
     * @return
     */
    private static User getUser() throws Exception {
        String userJson = getSharedStringData(KEY_USER_INFO, null);
        if (TextUtils.isEmpty(userJson)) {
            throw new Exception("local userInfo null");
        }
        User user = null;
        try {
            user = new Gson().fromJson(userJson, User.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        if (user == null) {
            throw new Exception("local user json element wrong");
        }
        return user;
    }

    /**
     * 获取token
     *
     * @return
     */
    public static String getToken() {
        try {
            return getUser().getToken();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取用户ID
     *
     * @return
     */
    public static String getUserId() {
        try {
            return getUser().getUserId();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取用户昵称
     *
     * @return
     */
    public static String getUserName() {
        try {
            return getUser().getUserName();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 保存用户数据
     *
     * @param user
     */
    private static void saveUser(User user) {
        String userJson = new Gson().toJson(user);
        setSharedStringData(KEY_USER_INFO, userJson);
    }

    /**
     * 删除用户数据
     */
    private static void deleteUser() {
        setSharedStringData(KEY_USER_INFO, null);
    }
}

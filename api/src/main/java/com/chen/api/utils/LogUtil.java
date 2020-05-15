package com.chen.api.utils;

import android.util.Log;

import com.chen.api.BuildConfig;


/**
 * Author: Chen
 * Date: 2018/6/8
 * Desc: Log管理工具类
 */
public class LogUtil {
    //定义一个全局的tag
    private static final String TAG = "APP_LOG";
    private static boolean DEBUG = BuildConfig.DEBUG;


    /**
     * 任何信息都会输出verbose(详细)
     *
     * @param message
     */
    public static void v(String message) {
        if (!DEBUG)
            return;
        Log.v(TAG, message);
    }

    public static void v(String tag, String message) {
        if (!DEBUG)
            return;
        Log.v(tag, message);
    }

    /**
     * debug信息
     *
     * @param message
     */
    public static void d(String message) {
        if (!DEBUG)
            return;
        Log.d(TAG, message);
    }

    public static void d(String tag, String message) {
        if (!DEBUG)
            return;
        Log.d(tag, message);
    }

    /**
     * info
     *
     * @param message
     */
    public static void i(String message) {
        if (!DEBUG)
            return;
        Log.i(TAG, message);
    }

    public static void i(String tag, String message) {
        if (!DEBUG)
            return;
        Log.i(tag, message);
    }

    /**
     * warn
     *
     * @param message
     */
    public static void w(String message) {
        if (!DEBUG)
            return;
        Log.w(TAG, message);
    }

    public static void w(String tag, String message) {
        if (!DEBUG)
            return;
        Log.w(tag, message);
    }

    /**
     * error
     *
     * @param message
     */
    public static void e(String message) {
        if (!DEBUG)
            return;
        Log.e(TAG, message);
    }

    public static void e(String tag, String message) {
        if (!DEBUG)
            return;
        Log.e(tag, message);
    }
}

package com.chen.api.utils;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chen.api.R;
import com.chen.api.base.BaseApplication;

/**
 * Author: Chen
 * Date: 2018/7/4
 * Desc: Toast管理类
 */
public class ToastUtil {
    private static Toast toast1;
    private static Toast toast2;

    /**
     * 初始化Toast
     *
     * @param msg
     * @param duration
     * @param isCenter
     * @return
     */
    @SuppressLint("ShowToast")
    private static Toast initToast(CharSequence msg, int duration, boolean isCenter) {
        if (toast1 == null) {
            toast1 = Toast.makeText(BaseApplication.getBaseAppContext(), msg, duration);
        } else {
            toast1.setText(msg);
            toast1.setDuration(duration);
        }
        if (isCenter) {
            toast1.setGravity(Gravity.CENTER, 0, 0);
        }
        return toast1;
    }

    /**
     * 短时间显示Toast
     *
     * @param msg
     */
    public static void showShort(CharSequence msg) {
        initToast(msg, Toast.LENGTH_SHORT, false).show();
    }

    /**
     * 短时间显示Toast,可决定是否在屏幕中间显示
     *
     * @param msg
     * @param isCenter
     */
    public static void showShort(CharSequence msg, boolean isCenter) {
        initToast(msg, Toast.LENGTH_SHORT, isCenter).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param strResId
     */
    public static void showShort(int strResId) {
        initToast(BaseApplication.getBaseAppResources().getText(strResId), Toast.LENGTH_SHORT, false).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param strResId
     * @param isCenter
     */
    public static void showShort(int strResId, boolean isCenter) {
        initToast(BaseApplication.getBaseAppResources().getText(strResId), Toast.LENGTH_SHORT, isCenter).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param msg
     */
    public static void showLong(CharSequence msg) {
        initToast(msg, Toast.LENGTH_LONG, false).show();
    }

    /**
     * 长时间显示Toast,是否显示在屏幕中间
     *
     * @param msg
     * @param isCenter
     */
    public static void showLong(CharSequence msg, boolean isCenter) {
        initToast(msg, Toast.LENGTH_LONG, isCenter).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param strResId
     */
    public static void showLong(int strResId) {
        initToast(BaseApplication.getBaseAppResources().getText(strResId), Toast.LENGTH_LONG, false).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param strResId
     * @param isCenter
     */
    public static void showLong(int strResId, boolean isCenter) {
        initToast(BaseApplication.getBaseAppResources().getText(strResId), Toast.LENGTH_LONG, isCenter).show();
    }

    /**
     * 自定义显示时间的Toast
     *
     * @param msg
     * @param duration
     */
    public static void show(CharSequence msg, int duration) {
        initToast(msg, duration, false).show();
    }

    /**
     * 自定义显示时间的Toast
     *
     * @param msg
     * @param duration
     */
    public static void show(CharSequence msg, int duration, boolean isCenter) {
        initToast(msg, duration, isCenter).show();
    }

    /**
     * 自定义显示时间的Toast
     *
     * @param strResId
     * @param duration
     */
    public static void show(int strResId, int duration) {
        initToast(BaseApplication.getBaseAppResources().getText(strResId), duration, false).show();
    }

    /**
     * 自定义显示时间的Toast
     *
     * @param strResId
     * @param duration
     */
    public static void show(int strResId, int duration, boolean isCenter) {
        initToast(BaseApplication.getBaseAppResources().getText(strResId), duration, isCenter).show();
    }

    public static void showToastImg(String msg){
        showToastImg(msg,R.drawable.net_error);
    }

    /**
     * 显示有Image的Toast
     *
     * @param msg
     * @param imgRes
     */
    public static void showToastImg(String msg, int imgRes) {
        if (toast2 == null) {
            toast2 = new Toast(BaseApplication.getBaseAppContext());
        }

        View view = LayoutInflater.from(BaseApplication.getBaseAppContext()).inflate(R.layout.toast_custom, null);
        TextView tv = view.findViewById(R.id.toast_custom_tv);
        tv.setText(msg);
        ImageView iv = view.findViewById(R.id.toast_custom_iv);
        iv.setImageResource(imgRes);
        toast2.setView(view);
        toast2.setDuration(Toast.LENGTH_SHORT);
        toast2.setGravity(Gravity.CENTER, 0, 0);
        toast2.show();
    }
}

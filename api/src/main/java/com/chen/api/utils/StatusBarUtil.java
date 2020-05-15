package com.chen.api.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Author: Chen
 * Date: 2019/7/26
 * Desc: 针对Android6.0以上机型
 *
 * 注！！！本工具类实现目标：
 * Android 4.4的机器状态栏都是默认黑色;Android 5.0/5.1的机器状态栏都是浅灰色;Android 6.0的机器状态栏背景可根据需要设置不同颜色，状态栏内容可以设置黑/白两种
 * Android6.0设置了沉浸式状态栏之后状态栏内容不能更改为黑色,Android7.0以上不受影响可以改为黑色
 * 注！！！如果设置了沉浸式状态栏,需要在对应页面布局文件设置两个标题栏(两个标题栏文件区别在于其中一个比另一个高出状态栏高度)，根据机型Android版本显示/隐藏哪个
 *
 */
public class StatusBarUtil {

    public static void setStatusBar(@NonNull Activity activity, int statusBarBgColor) {
        configStatusBar(activity, statusBarBgColor, true);
    }

    public static void setStatusBar(@NonNull Activity activity, int statusBarBgColor, boolean statusBarDarkContent) {
        configStatusBar(activity, statusBarBgColor, statusBarDarkContent);
    }

    public static void setTranslucentStatusBar(@NonNull Activity activity) {
        configTranslucentStatusBar(activity, false);
    }

    public static void setTranslucentStatusBar(@NonNull Activity activity, boolean statusBarDarkContent) {
        configTranslucentStatusBar(activity, statusBarDarkContent);
    }


    /**
     * @param activity
     * @param statusBarBgColor     状态栏背景色
     * @param statusBarDarkContent 状态栏内容（即图标）是否深色 true：深色,false：浅色
     */
    private static void configStatusBar(@NonNull Activity activity, int statusBarBgColor, boolean statusBarDarkContent) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//Android6.0以上
            //状态栏背景颜色
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(statusBarBgColor);
            if (statusBarDarkContent) {
                //状态栏内容深色,(styles中设置<item name="android:windowLightStatusBar">true</item>true表示设置状态栏内容深色Android6.0以上可以设置,注：6.0设置了沉浸式windowTranslucentStatus之后无效，7.0以上不受影响)
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//Android5.0-6.0使用类系统的浅灰色
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#4D000000"));
        }
    }

    /**
     * 设置使用沉浸式状态栏
     *
     * @param activity
     * @param statusBarDarkContent 状态栏内容（即图标）是否深色 true：深色,false：浅色
     */
    private static void configTranslucentStatusBar(@NonNull Activity activity, boolean statusBarDarkContent) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//Android6.0以上
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//沉浸式状态栏，状态栏空间会被下面内容顶上去(styles中设置：<item name="android:windowTranslucentStatus">true</item>)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && statusBarDarkContent) {
                //在设置了沉浸式状态栏之后，必须是Android7.0以上设置状态栏内容深色才起作用
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//Android5.0-6.0使用类系统的浅灰色
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#4D000000"));
        }
    }
}

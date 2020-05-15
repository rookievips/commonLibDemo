package com.chen.api.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class VersionUtil {

    /**
     * 获取APP 版本号(整型)
     *
     * @param context
     * @return
     */
    public static int versionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    /**
     * 获取APP 版本名称(字符串)
     *
     * @param context
     * @return
     */
    public static String versionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pi;
    }
}

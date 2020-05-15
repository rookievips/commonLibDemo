package com.chen.api.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 检测手机上是否安装了某个软件,通过包名的方式
 * ("com.baidu.BaiduMap","com.autonavi.minimap",检测是否安装百度高德地图)
 */

public class AppInstallUtil {

    public static boolean isAppInstalled(Context context, String packageName){
        //获取packageManager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已经安装的包信息
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<>();
        if (installedPackages != null){
            for (PackageInfo packageInfo : installedPackages) {
                String packName = packageInfo.packageName;
                packageNames.add(packName);
            }
        }
        return packageNames.contains(packageName);
    }
}

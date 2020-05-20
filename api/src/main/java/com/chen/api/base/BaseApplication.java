package com.chen.api.base;

import android.content.Context;
import android.content.res.Resources;
import android.support.multidex.MultiDexApplication;


public class BaseApplication extends MultiDexApplication {

    private static BaseApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

//    private void initSte() {
//        //Chrome调试配置(数据库调试)
//        Stetho.initialize(
//                Stetho.newInitializerBuilder(this)
//                        .enableDumpapp(
//                                Stetho.defaultDumperPluginsProvider(this))
//                        .enableWebKitInspector(
//                                Stetho.defaultInspectorModulesProvider(this))
//                        .build());
//    }

    public static Context getBaseAppContext() {
        return context;
    }

    public static Resources getBaseAppResources() {
        return context.getResources();
    }
}

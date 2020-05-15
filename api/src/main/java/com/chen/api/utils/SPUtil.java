package com.chen.api.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.chen.api.base.BaseApplication;


/**
 * Author: Chen
 * Date: 2018/6/22
 * Desc: ShardPreferences工具类
 */
public class SPUtil {
    private static SharedPreferences sp;

    private static void init() {
        if (sp == null) {
            sp = BaseApplication.getBaseAppContext().getSharedPreferences("sp_app", Context.MODE_PRIVATE);//注：不要用默认方式创建sp
        }
    }

    /**
     * int 类型
     *
     * @param key
     * @param value
     */
    public static void setSharedIntData(String key, int value) {
        if (sp == null) {
            init();
        }
        sp.edit().putInt(key, value).commit();
    }

    public static int getSharedIntData(String key, int defValue) {
        if (sp == null) {
            init();
        }
        return sp.getInt(key, defValue);
    }

    /**
     * long类型
     *
     * @param key
     * @param value
     */
    public static void setSharedLongData(String key, long value) {
        if (sp == null) {
            init();
        }
        sp.edit().putLong(key, value).commit();
    }

    public static long getSharedLongData(String key, long defValue) {
        if (sp == null) {
            init();
        }
        return sp.getLong(key, defValue);
    }

    /**
     * float 类型
     *
     * @param key
     * @param value
     */
    public static void setSharedFloatData(String key, float value) {
        if (sp == null) {
            init();
        }
        sp.edit().putFloat(key, value).commit();
    }

    public static float getSharedFloatData(String key, float defValue) {
        if (sp == null) {
            init();
        }
        return sp.getFloat(key, defValue);
    }

    /**
     * boolean 类型
     *
     * @param key
     * @param value
     */
    public static void setSharedBooleanData(String key, boolean value) {
        if (sp == null) {
            init();
        }
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getSharedBooleanData(String key, boolean defValue) {
        if (sp == null) {
            init();
        }
        return sp.getBoolean(key, defValue);
    }

    /**
     * String类型
     *
     * @param key
     * @param value
     */
    public static void setSharedStringData(String key, String value) {
        if (sp == null) {
            init();
        }
        sp.edit().putString(key, value).commit();
    }

    public static String getSharedStringData(String key, String defValue) {
        if (sp == null) {
            init();
        }
        return sp.getString(key, defValue);
    }

    /**
     * 清空缓存数据
     */
    protected static void clear() {
        if (sp == null) {
            init();
        }
        sp.edit().clear();
        sp.edit().commit();
    }

}

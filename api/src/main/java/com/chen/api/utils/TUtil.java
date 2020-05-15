package com.chen.api.utils;

import java.lang.reflect.ParameterizedType;

/**
 * Author: Chen
 * Date: 2018/7/4
 * Desc: 类转换初始化
 */
@SuppressWarnings("all")
public class TUtil {

    public static <T> T getT(Object o) {
        try {
            return ((Class<T>) ((ParameterizedType) o.getClass().getGenericSuperclass()).getActualTypeArguments()[0]).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

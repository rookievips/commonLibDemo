package com.chen.commonlib.app;

import com.chen.api.http.RequestEntity;

/**
 * Author: Chen
 * Date: 2020/5/19 17:45
 * Des:
 */
public class RequestEntityMap extends RequestEntity {

    private String appDomain = "purchaser";

    public String getAppDomain() {
        return appDomain;
    }

    public void setAppDomain(String appDomain) {
        this.appDomain = appDomain;
    }
}

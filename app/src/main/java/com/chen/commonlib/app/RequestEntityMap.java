package com.chen.commonlib.app;

import com.chen.api.http.RequestEntity;

/**
 * Author: Chen
 * Date: 2020/5/19 17:45
 * Des:
 */
public class RequestEntityMap extends RequestEntity {

    private String appDomain = "purchaser";
    private String cityId = "31";

    public String getAppDomain() {
        return appDomain;
    }

    public void setAppDomain(String appDomain) {
        this.appDomain = appDomain;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}

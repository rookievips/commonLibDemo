package com.chen.api.http;

import android.os.Build;

import com.chen.api.base.BaseApplication;
import com.chen.api.utils.VersionUtil;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class RequestEntity {

    private String cmd;
    private String appVersion = VersionUtil.versionName(BaseApplication.getBaseAppContext());
    private Map<String, Object> deviceInfo;
    private Map parameters = new HashMap();

    public RequestEntity() {
        deviceInfo = new HashMap<>();
        deviceInfo.put("os", "android");
        deviceInfo.put("osVersion", Build.VERSION.RELEASE);
        deviceInfo.put("model", Build.MODEL);
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public Map<String, Object> getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(Map<String, Object> deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getCmd() {
        return cmd;
    }

    public Map getParameters() {
        return parameters;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    @SuppressWarnings("unchecked")
    public void putParameter(String key, Object value) {
        parameters.put(key, value);
    }

    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this);
    }
}

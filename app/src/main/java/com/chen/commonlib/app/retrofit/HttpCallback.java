package com.chen.commonlib.app.retrofit;


public interface HttpCallback<T> {
    void onResponse(T responseEntity);

    void onResponseError(int errorCode, String msg);

    void onInvalidToken();

    void onFailed(int failCode, String msg);

    void onComplete();
}

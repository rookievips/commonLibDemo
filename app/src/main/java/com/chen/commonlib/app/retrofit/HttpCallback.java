package com.chen.commonlib.app.retrofit;


public interface HttpCallback<T> {

    void onStart();

    void onSuccess(T responseEntity);

    void onFail(int code, String msg);

    void onComplete();
}

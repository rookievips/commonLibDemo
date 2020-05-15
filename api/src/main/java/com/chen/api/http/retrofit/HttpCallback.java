package com.chen.api.http.retrofit;


public interface HttpCallback<T> {
    void onSuccess(T responseEntity);

    void onFailed(int code, String msg);

    void onComplete();
}

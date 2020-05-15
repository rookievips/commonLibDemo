package com.chen.commonlib.app.option.volley;


import com.chen.commonlib.app.option.ResponseEntity;


public interface OnRequestListener<T> {

    //在UI线程执行
    void onStart();

    //返回json数据中的response字段，在子线程中执行
    T jsonToObj(String responseStr);

    //返回失败，在UI线程执行
    void onFail(int failCode, String msg);

    //返回失败，在UI线程执行
    void onResponseError(int failCode, String msg);

    //在UI线程执行
    void onResponse(ResponseEntity<T> responseEntity);
}

package com.chen.commonlib.app.retrofit;


public interface HttpConstant {
    //403
    String FORBIDDEN ="服务器请求被拒绝";
    //404
    String NOT_FOUND = "服务器异常，请稍后再试";
    //408
    String REQUEST_TIMEOUT = "请求超时";
    //500
    String INTERNAL_SERVER_ERROR = "服务器错误";
    //504
    String GATEWAY_TIMEOUT = "网关超时";


    String UN_KNOW_ERROR = "未知错误";
    int CODE_UN_KNOW_ERROR = 1000;

    String NO_INTERNET = "没有网络，请检查网络后重试";
    int CODE_NO_INTERNET = 1001;

    String CONNECT_FAIL = "连接失败";
    int CODE_CONNECT_FAIL = 1002;

    String CONNECT_TIME_OUT = "连接超时";
    int CODE_CONNECT_TIME_OUT = 1003;


    int CODE_RESPONSE_SUCCESS = 200;
    int CODE_INVALID_TOKEN = 401;


}

package com.chen.api.http.retrofit;


public interface HttpMsgConfig {
    //403
    String FORBIDDEN ="服务器请求被拒绝";
    //404
    String NOT_FOUND = "服务器异常，请稍后再试";
    //408
    String REQUEST_TIMEOUT = "请求超时";
    //500
    String INTERNAL_SERVER_ERROR = "服务器错误";
    //504
    String NO_INTERNET = "连接失败,请检查网络后重试";

}

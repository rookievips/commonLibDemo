package com.chen.commonlib.app.retrofit;


import com.chen.api.http.RequestEntity;
import com.chen.api.http.ResponseEntity;
import com.chen.commonlib.app.RequestEntityMap;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Author: Chen
 * Date: 2019/7/23
 * Desc:
 */
public interface HttpStores {

    /**
     * 登录
     *
     * @param requestEntity
     * @return
     */
    @POST("/purchase/cgi")
    Observable<ResponseEntity<Map<String, Object>>> login(
            @Body RequestEntity requestEntity
    );

    @POST
    Observable<ResponseEntity<Map<String, Object>>> getData(
            @Body RequestEntityMap requestEntityMap,
            @Url String url
    );


    @FormUrlEncoded
    @POST
    @Headers("Content-Type:application/x-www-form-urlencoded;charset=utf-8")
    Observable<Map<String,Object>> test(
            @Url String url,
            @FieldMap Map<String,String> params
    );

    @POST
    Observable<Map<String,Object>> test1(
            @Url String url,
            @Body Map<String,String> params
    );


}

package com.chen.commonlib.app.retrofit;


import com.chen.api.http.RequestEntity;
import com.chen.api.http.ResponseEntity;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

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
    @POST("/api/purchase")
    Observable<ResponseEntity<Map<String, Object>>> login(
            @Body RequestEntity requestEntity
    );

}

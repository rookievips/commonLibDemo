package com.chen.commonlib.app.option.retrofit;


import com.chen.commonlib.app.option.RequestEntity;
import com.chen.commonlib.app.option.ResponseEntity;

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
    @POST("/api/supply/cgi")
    Observable<ResponseEntity<Map<String, Object>>> login(
            @Body RequestEntity requestEntity
    );

}

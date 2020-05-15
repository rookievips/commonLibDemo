package com.chen.commonlib.app.option.retrofit;

import android.util.SparseArray;

import com.chen.api.base.BaseApplication;
import com.chen.api.helper.UserHelper;
import com.chen.commonlib.app.option.Cmd;
import com.chen.commonlib.app.option.Constant;
import com.chen.commonlib.app.option.RequestEntity;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@SuppressWarnings("all")
public class Http {
    //网络超时时长,单位：秒
    private static final int TIME_OUT = 10;
    //缓存有效时长60秒可读取 单位:秒
    private static final int MAX_AGE = 60;
    //默认请求方式
    private static final int DEFAULT_HOST = 0;
    //自定义请求方式可以根据需要添加
    private static final int CUSTOM_HOST_ONE = 1;

    private Retrofit retrofit;
    private HttpStores httpStores;

    //用于存放Http实例
    public static SparseArray<Http> retrofitManager = new SparseArray<>();
    //缓存文件设置，大小：10M
    private static Cache cache = new Cache(new File(BaseApplication.getBaseAppContext().getCacheDir(), "retrofit"), 1024 * 1024 * 10);

    private Http(int hostType, String baseUrl) {
        //log日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //请求头拦截器
        Interceptor headerInterceptor = chain -> {
            Request request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json;charset=UTF-8")
                    .build();
            return chain.proceed(request);
        };

        //Token拦截器
        Interceptor tokenInterceptor = chain -> {
            HttpUrl oldHttpUrl = chain.request().url();
            HttpUrl newHttpUrl = null;

            RequestBody requestBody = chain.request().body();
            if (UserHelper.getToken() != null && requestBody != null) {
                boolean noToken = noToken(requestBody, Cmd.CMD_LOGIN);//不需要token的接口在这里添加就好了
                if (!noToken) {
                    newHttpUrl = oldHttpUrl.newBuilder().addQueryParameter("access_token", UserHelper.getToken()).build();
                }
            }

            Request request = chain.request().newBuilder()
                    .url(newHttpUrl == null ? oldHttpUrl : newHttpUrl)
                    .build();
            return chain.proceed(request);
        };

        //网络缓存拦截器(只对GET请求有效,OKHttp默认不支持POST缓存)
        Interceptor cacheInterceptor = chain -> {
            Request request = chain.request();
            Response response = chain.proceed(request);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + MAX_AGE)
                    .build();
        };

        OkHttpClient okHttpClientDefault = new OkHttpClient.Builder()
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(headerInterceptor)
                .addInterceptor(tokenInterceptor)
                .addNetworkInterceptor(cacheInterceptor)
                .cache(cache)
                .build();

        OkHttpClient okHttpClientCustom = new OkHttpClient.Builder()
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(cacheInterceptor)
                .cache(cache)
                .build();

        if (hostType == DEFAULT_HOST) {
            retrofit = new Retrofit.Builder()
                    .client(okHttpClientDefault)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(baseUrl)
                    .build();
        } else {
            retrofit = new Retrofit.Builder()
                    .client(okHttpClientCustom)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(baseUrl)
                    .build();
        }

        httpStores = retrofit.create(HttpStores.class);
    }

    /**
     * 判断当前接口是否不需要token
     *
     * @param requestBody 请求体（内含当前接口）
     * @param cmd         string数组：不需要token的接口
     * @return
     * @throws IOException
     */
    private static boolean noToken(RequestBody requestBody, String... cmd) throws IOException {
        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        String requestJson = buffer.readUtf8();
        RequestEntity requestEntity = new Gson().fromJson(requestJson, RequestEntity.class);
        String tempCmd = requestEntity.getCmd();

        for (String c : cmd) {
            if (tempCmd.equals(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 默认方式
     *
     * @return
     */
    public static HttpStores getDefault() {
        Http http = retrofitManager.get(DEFAULT_HOST);
        if (http == null) {
            http = new Http(DEFAULT_HOST, Constant.HOST);
            retrofitManager.put(DEFAULT_HOST, http);
        }
//        LogUtil.i("retrofitSize:",retrofitManager.size() + "");
        return http.httpStores;
    }

    /**
     * 自定义方式
     *
     * @param hostType 根据需要自己添加hostType，需要几个就可以添加几个
     * @param baseUrl
     * @return
     */
    public static HttpStores getCustom(int hostType, String baseUrl) {
        Http http = retrofitManager.get(hostType);
        if (http == null) {
            http = new Http(hostType, baseUrl);
            retrofitManager.put(hostType, http);
        }
//        LogUtil.i("retrofitSize:",retrofitManager.size() + "");
        return http.httpStores;
    }

}

package com.chen.commonlib.app.option.volley;

import android.content.Context;
import android.util.Log;

import com.chen.api.http.volley.DefaultRetryPolicy;
import com.chen.api.http.volley.Request;
import com.chen.api.http.volley.RequestQueue;
import com.chen.api.http.volley.RetryPolicy;
import com.chen.api.http.volley.toolbox.Volley;

public class RequestManager {

    public static final int TIMEOUT = 80 * 1000;

    public static final int RETRY_COUNT = 0;

    private static RequestManager instance;
    private RequestQueue mRequestQueue;
    private boolean flag = false;

    private RequestManager() {}

    public void init(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        flag = true;
    }

    public static RequestManager getInstance() {
        if (instance == null) {
            synchronized (RequestManager.class) {
                if (instance == null) {
                    instance = new RequestManager();
                }
            }
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }



    public void cancel(String tag) {
        Log.e("currentTag:", tag);
        if (tag != null && flag)
        mRequestQueue.cancelAll(tag);
    }

    public void request(String tag, HttpRequest request) {
        Log.e("currentTag:", tag);
        if (request != null) {
            request.onStart();
            RetryPolicy retryPolicy = new DefaultRetryPolicy(TIMEOUT, RETRY_COUNT, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            request.setRetryPolicy(retryPolicy);
            request.setTag(tag);
            request(request);
        }
    }

    public <T> void request(Request<T> request) {
        mRequestQueue.add(request);
    }
}

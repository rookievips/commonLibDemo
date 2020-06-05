package com.chen.api.http;

import android.text.TextUtils;
import android.util.Log;

import com.chen.api.http.volley.AuthFailureError;
import com.chen.api.http.volley.NetworkResponse;
import com.chen.api.http.volley.Request;
import com.chen.api.http.volley.Response;
import com.chen.api.http.volley.VolleyError;
import com.chen.api.http.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
public class HttpRequest<T> extends Request<ResponseEntity<T>> {

    //返回数据为空
    public static final int FAIL_DATA_EMPTY = 1111;
    //网络故障
    public static final int ERROR_NET_FAULT = 2222;

    private Map<String, String> headers;
    private Object data;
    private OnRequestListener<T> onRequestListener;

    public HttpRequest(Object data, String baseUrl, OnRequestListener<T> onRequestListener) {
        super(Method.POST, baseUrl, null);
        this.data = data;
        this.headers = null;
        this.onRequestListener = onRequestListener;
    }

    @Override
    public Map<String, String> getHeaders() {
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put("Accept", "application/json");
        return headers;
    }

    @Override
    public String getBodyContentType() {
        return "application/json; charset=UTF-8";
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (data != null) {
            try {
                String jsonStr = data.toString();
                Log.e("VolleyRequest:", "request=" + jsonStr);
                if (jsonStr != null) {
                    return jsonStr.getBytes("utf-8");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return super.getBody();
    }

    // UI线程执行
    @Override
    protected void deliverResponse(ResponseEntity<T> responseEntity) {
        if (responseEntity != null) {
            int statusCode = responseEntity.getStatusCode();
            String message = responseEntity.getMessage();

            if (statusCode == 200) {
                T response = responseEntity.getResponse();
                if (response == null) {
                    onFail(FAIL_DATA_EMPTY, "response obj back null");
                } else {
                    onResponse(responseEntity);
                }
            }else {
                if (TextUtils.isEmpty(message)) {
                    message = "No errorInfo";
                }
                onResponseError(statusCode, message);
            }
        } else {
            onFail(FAIL_DATA_EMPTY, "response entity obj back null");
        }

    }

    @Override
    public void deliverError(VolleyError error) {
        String message = error.getMessage();
        NetworkResponse networkResponse = error.networkResponse;
        if (networkResponse != null) {
            int statusCode = networkResponse.statusCode;
            if (statusCode == HttpURLConnection.HTTP_NOT_FOUND) {
                onFail(statusCode, "服务器无响应");
            } else if (statusCode == HttpURLConnection.HTTP_CLIENT_TIMEOUT || statusCode == HttpURLConnection.HTTP_GATEWAY_TIMEOUT) {
                onFail(statusCode, "连接超时");
            } else {
                onFail(statusCode, message);
            }
        } else {
            onFail(ERROR_NET_FAULT, message);
        }

    }

    // 子线程执行
    @Override
    protected Response<ResponseEntity<T>> parseNetworkResponse(NetworkResponse response) {
        byte[] data = response.data;
        try {
            String parsed = new String(data, "utf8");
            Log.e("VolleyResponse:", "response=" + parsed);
            ResponseEntity<T> result = new Gson().fromJson(parsed, ResponseEntity.class);
            if (result != null) {
                String responseStr = getResponseStr(parsed);

                if (!TextUtils.isEmpty(responseStr)) {
                    T t = jsonToObj(responseStr);
                    result.setResponse(t);
                }
            }
            return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getResponseStr(String parsed) {
        try {
            JSONObject jo = new JSONObject(parsed);
            if (jo.has("response")) {
                return jo.getString("response");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    //返回json数据中的response字段，在子线程中执行
    public T jsonToObj(String responseStr) {
        return onRequestListener.jsonToObj(responseStr);
    }

    //在UI线程执行，请求发送前
    public void onStart() {
        onRequestListener.onStart();
    }

    //返回失败，在UI线程执行
    public void onFail(int failCode, String msg) {
        onRequestListener.onFail(failCode, msg);
    }

    //返回成功，在UI线程执行，接收后台错误码和错误信息
    public void onResponseError(int errorCode, String msg) {
        onRequestListener.onResponseError(errorCode, msg);
    }

    //返回成功，在UI线程执行，接受数据体
    public void onResponse(ResponseEntity<T> response) {
        onRequestListener.onResponse(response);
    }

}


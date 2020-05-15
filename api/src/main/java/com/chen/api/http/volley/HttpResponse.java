package com.chen.api.http.volley;

import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    protected Map<String, String> headers = new HashMap<String, String>();

    protected int responseCode = -1;

    protected String responseMessage;

    protected HttpEntity httpEntity;

    public String addHeader(String key, String value) {

        return headers.put(key, value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public HttpEntity getEntity() {
        return httpEntity;
    }

    public void setEntity(HttpEntity httpEntity) {
        this.httpEntity = httpEntity;
    }

}

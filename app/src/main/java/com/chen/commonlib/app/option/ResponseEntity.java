package com.chen.commonlib.app.option;

public class ResponseEntity<T> {

    private String serverTime;
    private int statusCode;

    private T response;
    private ResponseError error;

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public ResponseError getError() {
        return error;
    }

    public void setError(ResponseError error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "ResponseEntity{" +
                "serverTime='" + serverTime + '\'' +
                ", statusCode=" + statusCode +
                ", response=" + response +
                ", error=" + error +
                '}';
    }
}

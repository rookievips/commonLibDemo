package com.chen.api.http;

public class ResponseEntity<T> {

    private String serverTime;
    private int statusCode;
    private String message;

    private T response;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ResponseEntity{" +
                "serverTime='" + serverTime + '\'' +
                ", statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", response=" + response +
                '}';
    }
}

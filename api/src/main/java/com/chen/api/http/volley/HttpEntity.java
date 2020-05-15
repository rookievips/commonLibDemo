package com.chen.api.http.volley;

import java.io.IOException;
import java.io.InputStream;

public class HttpEntity {

    protected InputStream in;
    protected int len = -1;
    protected String encode;
    protected String contentType;

    public void setContent(InputStream in) {
        this.in = in;
    }

    public InputStream getContent() {
        return in;
    }

    public void setContentLength(int len) {
        this.len = len;
    }

    public int getContentLength() {
        return len;
    }

    public void setContentEncoding(String encode) {
        this.encode = encode;
    }

    public String getContentEncoding() {
        return encode;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public void consumeContent() throws IOException {
        if (in != null) {
            in.close();
        }
    }

}

package com.chen.commonlib.app.retrofit;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Iterator;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import static okhttp3.internal.Util.UTF_8;


@SuppressWarnings("all")
public class CusGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    CusGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            String response = value.string();
            JSONObject mJSONObject = null;
            try {
                mJSONObject = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
                value.close();
                throw new ServerException(HttpConstant.CODE_JSON, "can not to json obj.");
            }
            Iterator<String> iterator = mJSONObject.keys();
            String responseCodeName = "", responseMessageName = "";
            while (iterator != null && iterator.hasNext()) {
                String name = iterator.next();
                if (name.equals("code") || name.equals("statusCode")) {
                    responseCodeName = name;
                } else if (name.equals("msg") || name.equals("message") || name.equals("errorMsg") || name.equals("errorMessage")) {
                    responseMessageName = name;
                }
            }
            int code = mJSONObject.optInt(responseCodeName, -1);
            if (code != -1) {
                if (code == HttpConstant.CODE_RESPONSE_SUCCESS) {
                    MediaType mediaType = value.contentType();
                    Charset charset = mediaType != null ? mediaType.charset(UTF_8) : UTF_8;
                    InputStream inputStream = new ByteArrayInputStream(response.getBytes());
                    JsonReader jsonReader = gson.newJsonReader(new InputStreamReader(inputStream, charset));
                    T result = adapter.read(jsonReader);
                    if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                        value.close();
                        throw new JsonIOException("JSON document was not fully consumed.");
                    }
                    return result;

                } else if (code == HttpConstant.CODE_INVALID_TOKEN) {
                    String message = mJSONObject.optString(responseMessageName);
                    value.close();
                    throw new TokenInvalidException(code,message);

                } else {
                    String message = mJSONObject.optString(responseMessageName);
                    value.close();
                    throw new ServerException(code, message);

                }
            } else {
                MediaType mediaType = value.contentType();
                Charset charset = mediaType != null ? mediaType.charset(UTF_8) : UTF_8;
                InputStream inputStream = new ByteArrayInputStream(response.getBytes());
                JsonReader jsonReader = gson.newJsonReader(new InputStreamReader(inputStream, charset));
                T result = adapter.read(jsonReader);
                if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                    value.close();
                    throw new JsonIOException("JSON document was not fully consumed.");
                }
                return result;

            }
        } finally {
            value.close();
        }
    }
}

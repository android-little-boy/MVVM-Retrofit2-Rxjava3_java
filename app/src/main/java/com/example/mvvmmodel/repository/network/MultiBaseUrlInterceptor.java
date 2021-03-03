package com.example.mvvmmodel.repository.network;

import android.util.Log;

import com.example.mvvmmodel.config.HeaderConfig;
import com.example.mvvmmodel.config.UrlConfig;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MultiBaseUrlInterceptor implements Interceptor {
    private static final String TAG = MultiBaseUrlInterceptor.class.getName();
    private static final String ORIGIN_BASE_URL = UrlConfig.SYS_URL;

    @Override
    public Response intercept(Chain chain) throws IOException {

        //获取旧的请求信息
        Request oldRequest = chain.request();
        Request.Builder builder = oldRequest.newBuilder();
        HttpUrl oldHttpUrl = oldRequest.url();
        String oldUrl = oldHttpUrl.toString();
        List<String> headerValues = oldRequest.headers(HeaderConfig.KEY_NAME);

        //解析请求头，通过得到的newBaseUrl，再做转换，返回最终的url
        if (headerValues != null && !headerValues.isEmpty()) {
            builder.removeHeader(HeaderConfig.KEY_NAME);
            Log.i(TAG, "oldHttpUrl--->>>>" + oldUrl);
            String headerValue = headerValues.get(0);
            HttpUrl newBaseUrl;
            if (HeaderConfig.SYS_NAME.equals(headerValue)) {
                newBaseUrl = HttpUrl.parse(UrlConfig.SYS_URL);
            } else if (HeaderConfig.LOG_NAME.equals(headerValue)) {
                newBaseUrl = HttpUrl.parse(UrlConfig.LOG_URL);
            } else {
                newBaseUrl = oldHttpUrl;
            }
            if (newBaseUrl != null) {
                //如果原始baseUrl为空或者与新的baseUrl相等，则没必要做转换，直接return旧的请求
                if (newBaseUrl.toString().equals(ORIGIN_BASE_URL) || ORIGIN_BASE_URL.isEmpty()) {
                    Log.i(TAG, "newBaseUrl  equals ORIGIN_BASE_URL,return oldRequest");
                    return chain.proceed(oldRequest);
                }
                Log.i(TAG, "newBaseUrl--->>>>" + newBaseUrl);
                String newUrl = newBaseUrl + oldUrl.substring(ORIGIN_BASE_URL.length());
                Log.i(TAG, "newUrl--->>>>" + newUrl);
                Request newRequest = oldRequest
                        .newBuilder()
                        .url(newUrl)
                        .build();
                return chain.proceed(newRequest);
            }
        }
        return chain.proceed(oldRequest);
    }
}

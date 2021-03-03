package com.example.mvvmmodel.repository.network;

import com.example.mvvmmodel.config.UrlConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServiceManager {
    /**
     * 默认连接超时时间 5s
     */
    private static final int DEFAULT_CONNECT_TIME_OUT = 5;
    /**
     * 默认写超时时间
     */
    private static final int DEFAULT_WRITE_TIME_OUT = 10;
    /**
     * 默认读超时时间
     */
    private static final int DEFAULT_READ_TIME_OUT = 10;
    private final Retrofit mRetrofit;
    private static RetrofitServiceManager retrofitServiceManager;

    private RetrofitServiceManager() {
        // 创建 OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new MultiBaseUrlInterceptor());

        // 创建Retrofit
        mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(UrlConfig.SYS_URL)
                .build();
    }

    /**
     * 获取RetrofitServiceManager
     *
     * @return RetrofitServiceManager实例
     */
    public static RetrofitServiceManager getInstance() {
        if (retrofitServiceManager == null) {
            retrofitServiceManager = new RetrofitServiceManager();
        }
        return retrofitServiceManager;
    }



    /**
     * 获取对应的Service
     *
     * @param service Service 的 class
     * @param <T>     对应的service
     * @return retrofit实例
     */
    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }
}

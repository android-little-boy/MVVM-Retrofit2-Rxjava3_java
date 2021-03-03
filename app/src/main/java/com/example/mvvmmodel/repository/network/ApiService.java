package com.example.mvvmmodel.repository.network;


import com.example.mvvmmodel.model.BaseModel;
import com.example.mvvmmodel.model.BaseResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;

import static com.example.mvvmmodel.config.HeaderConfig.URL_TYPE_SYS;

public interface ApiService {

    @Headers(URL_TYPE_SYS)
    @GET("xxx")
    Observable<BaseResponse<List<BaseModel>>> getXxx();
}

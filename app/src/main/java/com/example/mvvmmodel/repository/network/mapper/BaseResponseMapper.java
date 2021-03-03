package com.example.mvvmmodel.repository.network.mapper;

import com.example.mvvmmodel.exception.ServerException;
import com.example.mvvmmodel.model.BaseResponse;
import com.example.mvvmmodel.model.ResponseWrapper;

import io.reactivex.rxjava3.functions.Function;

public class BaseResponseMapper<T> implements Function<BaseResponse, T> {
    @Override
    public T apply(BaseResponse baseResponse) {
        if (null == baseResponse) {
            throw new ServerException(ResponseWrapper.STATE_FAILURE, ResponseWrapper.NO_RESPONSE);
        }
        if (ResponseWrapper.STATE_FAILURE == baseResponse.getState()) {
            throw new ServerException(ResponseWrapper.STATE_FAILURE,
                    baseResponse.getErrorMessage());
        }
        return (T) baseResponse.getValue();
    }
}

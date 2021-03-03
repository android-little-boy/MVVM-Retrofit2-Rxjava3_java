package com.example.mvvmmodel.repository.network.mapper;

import com.example.mvvmmodel.exception.ServerException;
import com.example.mvvmmodel.model.ResponseWrapper;

import java.io.IOException;

import io.reactivex.rxjava3.functions.Function;
import okhttp3.ResponseBody;

/**
 * @author huangwj
 * @date 2021/3/3
 * @description 自定义结果转换
 */
public abstract class BaseCustomResponseMapper<T> implements Function<ResponseBody, T> {

    @Override
    public T apply(ResponseBody responseBody) throws IOException {
        if (null == responseBody) {
            throw new ServerException(ResponseWrapper.STATE_FAILURE, ResponseWrapper.NO_RESPONSE);
        }
        return parse(responseBody.string());
    }

    /**
     * 根据业务具体地解析数据
     *
     * @param jsonStr 待解析的Json数据
     * @return 自定义的返回对象
     */
    protected abstract T parse(String jsonStr);
}

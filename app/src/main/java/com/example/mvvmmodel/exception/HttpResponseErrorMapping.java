package com.example.mvvmmodel.exception;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Function;

/**
 * @author huangwj
 * @date 2021/3/3
 * @description 网络返回数据异常预处理，定义成为想要的异常，剥离异常处理逻辑
 */
public class HttpResponseErrorMapping<T> implements Function<Throwable, Observable<T>> {
    @Override
    public Observable<T> apply(Throwable throwable) {
        return Observable.error(ExceptionInterceptor.handleException(throwable));
    }
}

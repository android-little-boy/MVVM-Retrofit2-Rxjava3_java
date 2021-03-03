package com.example.mvvmmodel.repository.network;

import com.example.mvvmmodel.exception.HttpResponseErrorMapping;
import com.example.mvvmmodel.model.BaseResponse;
import com.example.mvvmmodel.repository.network.mapper.BaseCustomResponseMapper;
import com.example.mvvmmodel.repository.network.mapper.BaseResponseMapper;
import com.example.mvvmmodel.rxjava.SchedulerTransformer;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;

/**
 * @author huangwj
 * @date 2021/3/3
 * @description Observable转换器，将Observable结果进行转换，得到期望的数据
 */
public class ObservableConverter<T> {
    private final SchedulerTransformer<T> schedulerTransformer;

    public ObservableConverter() {
        schedulerTransformer = new SchedulerTransformer<>();
    }

    /**
     * 通用转换，转换返回类型为BaseResponse<T>的数据
     *
     * @param observer 待转换observer
     * @return 转换后的Observable
     */
    public Observable<T> transform(Observable<BaseResponse<T>> observer) {
        return observer.map(new BaseResponseMapper<T>())
                .onErrorResumeNext(new HttpResponseErrorMapping())
                .compose(schedulerTransformer);
    }

//    /**
//     * 通用转换，转换返回类型为BaseResponse<T>的数据
//     *
//     * @param observer 待转换observer
//     * @return 转换后的Observable
//     */
//    public Observable transformOnIOThread(Observable<BaseResponse<T>> observer) {
//        return observer.map(new BaseResponseMapper<T>())
//                .onErrorResumeNext(new HttpResponseErrorMapping())
//                .compose(RxSchedulers.applySchedulers2());
//    }


    /**
     * 转换类型为ResponseBody的数据，自定义转换
     *
     * @param observer                 待转换observer
     * @param baseCustomResponseMapper 自定义转换结果对象
     * @return 转换后的Observable
     */
    public Observable<T> transform(Observable<ResponseBody> observer, BaseCustomResponseMapper<T> baseCustomResponseMapper) {
        return observer.map(baseCustomResponseMapper)
                .onErrorResumeNext(new HttpResponseErrorMapping())
                .compose(schedulerTransformer);
    }

//    /**
//     * 转换类型为ResponseBody的数据，自定义转换
//     *
//     * @param observer                 待转换observer
//     * @param baseCustomResponseMapper 自定义转换结果对象
//     * @return 转换后的Observable
//     */
//    public Observable<T> transformOnIOThread(Observable<ResponseBody> observer, BaseCustomResponseMapper<T> baseCustomResponseMapper) {
//        return observer.map(baseCustomResponseMapper)
//                .onErrorResumeNext(new HttpResponseErrorMapping())
//                .compose(RxSchedulers.applySchedulers2());
//    }

}

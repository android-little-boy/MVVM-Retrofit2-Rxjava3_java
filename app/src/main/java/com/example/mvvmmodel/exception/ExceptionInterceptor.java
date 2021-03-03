package com.example.mvvmmodel.exception;

import android.util.Log;

import com.example.mvvmmodel.model.ResponseWrapper;
import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.text.ParseException;
import java.util.Objects;

import retrofit2.HttpException;

/**
 * @author huangwj
 * @date 2021/3/3
 * @description 异常拦截器，拦截服务器返回异常，自定义成为我们需要的异常再返回出去
 */
class ExceptionInterceptor {

    private ExceptionInterceptor(){}

    private static final String TAG = ExceptionInterceptor.class.getName();
    /**
     * 后台接口重写了以下四个Http错误码，覆盖了原有的Http错误，需要额外处理获取出错误信息
     */
    private static final int SERVICE_LOGIC_ERROR = 400;
    private static final int UNAUTHORIZED = 401;
    private static final int NOT_FOUND = 404;
    private static final int INTERNAL_SERVER_ERROR = 500;
    /**
     * 常用的Http错误码
     */
    private static final int FORBIDDEN = 403;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ApiException handleException(Throwable e) {
        Log.d(TAG, "handleException: "+e.getMessage());
        ApiException ex;
        // HTTP错误
        if (e instanceof HttpException) {
            ex = new ApiException(e, ResponseWrapper.HTTP_ERROR);
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case SERVICE_LOGIC_ERROR:
                case UNAUTHORIZED:
                case NOT_FOUND:
                case INTERNAL_SERVER_ERROR:
                    ex.setMessage("网络错误");
                    if (httpException.response() != null
                            && httpException.response().errorBody() != null) {
                        try {
                            String errorBody = Objects.requireNonNull(httpException.response().errorBody()).string();
                            if (errorBody != null && !errorBody.isEmpty()) {
                                JSONObject jsonObject = new JSONObject(errorBody);
                                if (jsonObject.has(ResponseWrapper.KEY_STATE)) {
                                    ex.setCode(jsonObject.getInt(ResponseWrapper.KEY_STATE));
                                    ex.setMessage(jsonObject.getString(ResponseWrapper.ERROR_MESSAGE));
                                }
                            }
                        } catch (JSONException | IOException e1) {
                            Log.e(TAG, "解析json时发生异常");
                        }
                    }
                    break;

                case FORBIDDEN:
                case REQUEST_TIMEOUT:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                case GATEWAY_TIMEOUT:
                default:
                    ex.setMessage("网络错误");
                    break;
            }
        } else
            // 服务器返回错误
            if (e instanceof ServerException) {
                ServerException resultException = (ServerException) e;
                ex = new ApiException(resultException, resultException.getCode());
                ex.setMessage(resultException.getMessage());
                // 解析错误
            } else if (e instanceof ErrorResponseStateException) {
                ex = new ApiException(e, ResponseWrapper.ERROR_RESPONSE_STATE);
                ex.setMessage("请求成功，结果异常");
            } else if (e instanceof JsonParseException
                    || e instanceof JSONException
                    || e instanceof ParseException) {
                ex = new ApiException(e, ResponseWrapper.PARSE_ERROR);
                ex.setMessage("解析错误");
            } else
                // 网络错误
                if (e instanceof ConnectException) {
                    ex = new ApiException(e, ResponseWrapper.NETWORK_ERROR);
                    ex.setMessage("连接失败");

                } else {
                    // 未知错误
                    ex = new ApiException(e, ResponseWrapper.UNKNOWN);
                    ex.setMessage("未知错误");
                }
        return ex;
    }
}

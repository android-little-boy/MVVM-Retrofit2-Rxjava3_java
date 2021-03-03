package com.example.mvvmmodel.model;

public class ResponseWrapper {
    //TODO 具体看后台返回数据
    /**
     * 'State' = 0, success
     */
    public static final int STATE_SUCCESS = 0;

    /**
     * 'State' = 1, failure
     */
    public static final int STATE_FAILURE = 1;

    public static final String NO_RESPONSE = "返回结果为空";

    /**
     * 协议出错
     */
    public static final int HTTP_ERROR = 1003;


    /**
     * Field 'State'
     */
    public static final String KEY_STATE = "state";
    /**
     * Field 'ErrorMessage'
     */
    public static final String ERROR_MESSAGE = "errorMessage";

    /**
     * 错误的返回状态
     */
    public static final int ERROR_RESPONSE_STATE = 1004;

    /**
     * 解析错误
     */
    public static final int PARSE_ERROR = 1001;

    /**
     * 网络错误
     */
    public static final int NETWORK_ERROR = 1002;

    /**
     * 未知错误
     */
    public static final int UNKNOWN = 1000;
}

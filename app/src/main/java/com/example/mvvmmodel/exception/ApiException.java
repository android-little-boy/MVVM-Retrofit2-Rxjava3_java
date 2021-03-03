package com.example.mvvmmodel.exception;


/**
 * @author huangwj
 * @date 2021/3/3
 */

public class ApiException extends Exception {

    /**
     * 错误状态码
     */
    private int code;

    /**
     * 错误信息
     */
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 默认构造方法
     */
    ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}

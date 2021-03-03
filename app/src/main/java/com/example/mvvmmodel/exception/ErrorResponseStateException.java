package com.example.mvvmmodel.exception;

/**
 * @author huangwj
 * @date 2021/3/3
 * @description 网络请求，返回的状态码异常
 */
public class ErrorResponseStateException extends RuntimeException {

    public ErrorResponseStateException(String msg) {
        super(msg);
    }

    public ErrorResponseStateException() {
        super();
    }
}
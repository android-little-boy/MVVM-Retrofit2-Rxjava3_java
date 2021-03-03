package com.example.mvvmmodel.model;

public class BaseResponse<T> {
    private static final long serialVersionUID = -1858675428890171628L;

    /**
     * 返回状态，成功或者失败
     */
    private int state;
    /**
     * 错误信息，若返回成功，该项值为null
     */
    private String errorMessage;
    /**
     * 返回的具体值
     */
    private T value;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "state=" + state +
                ", errorMessage='" + errorMessage + '\'' +
                ", value=" + value +
                '}';
    }
}

package com.pcp.myapp.net;

public class BaseResponse<T> {

    public static final int SUCCESS = 0;
    public static final int FAIL = -1;

    private int code = -1;
    private String msg;
    private T data;

    public int getErrorCode() {
        return code;
    }

    public void setErrorCode(int errorCode) {
        this.code = errorCode;
    }

    public String getErrorMsg() {
        return msg;
    }

    public void setErrorMsg(String errorMsg) {
        this.msg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}


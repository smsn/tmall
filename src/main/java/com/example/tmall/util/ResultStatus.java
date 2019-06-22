package com.example.tmall.util;

/**
 * ResultStatus
 */
public class ResultStatus {

    private static int SUCCESS_CODE = 0;
    private static int FAIL_CODE = 1;

    private int code;
    private String message;
    private Object data;

    public ResultStatus(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResultStatus success() {
        return new ResultStatus(SUCCESS_CODE, "success", null);
    }

    public static ResultStatus success(Object data) {
        return new ResultStatus(SUCCESS_CODE, "success", data);
    }

    public static ResultStatus fail(String message) {
        return new ResultStatus(FAIL_CODE, message, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
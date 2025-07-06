package com.hdd.winterSolsticeBlog.common.vo;

public class JsonResult<T> {
    private T data;
    private String message;
    private int code;
    private Long timestamp;

    public JsonResult() {
        this.timestamp = System.currentTimeMillis();
    }

    public JsonResult(T data, String message, int code) {
        this.data = data;
        this.message = message;
        this.code = code;
        this.timestamp = System.currentTimeMillis();
    }

    // getter/setter 方法
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }
    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }

    // 静态方法
    public static <T> JsonResult<T> success(T data) {
        return new JsonResult<>(data, "Success", 200);
    }

    public static <T> JsonResult<T> failure(String message, int code) {
        return new JsonResult<>(null, message, code);
    }
}
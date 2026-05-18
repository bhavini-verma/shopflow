package com.dailycodework.dreamshop.response;

public class ApiResponse {
    private String message;
    private java.lang.Object data;

    public ApiResponse(String message, java.lang.Object data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public java.lang.Object getData() {
        return data;
    }

    public void setData(java.lang.Object data) {
        this.data = data;
    }
}

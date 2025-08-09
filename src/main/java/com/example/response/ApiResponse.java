package com.example.response;

import org.springframework.http.HttpStatus;

/**
 *
 * <p>
 *
 * </p>
 *
 * @author Lee
 * @version 1.0
 * @since 2025/8/6
 */
public class ApiResponse<T> {
    private T data;
    private HttpStatus httpStatus;
    private String message;

    public ApiResponse() {
    }

    public ApiResponse(HttpStatus status, String message) {
        this.httpStatus = status;
        this.message = message;
        this.data = null;
    }

    public ApiResponse(T data, HttpStatus status, String message) {
        this.data = data;
        this.httpStatus = status;
        this.message = message;
    }
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

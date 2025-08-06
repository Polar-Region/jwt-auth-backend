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

    public void setData(T data) {
    }

    public void setMessage(String message) {
    }

    public void setHttpStatus(HttpStatus httpStatus) {
    }

    public ApiResponse(T data, String message, HttpStatus httpStatus) {
        setData(data);
        setMessage(message);
        setHttpStatus(httpStatus);
    }
}

package com.example.interceptor;

import com.example.exception.BaseHttpException;
import com.example.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {
    @ExceptionHandler(value = {Throwable.class})
    public ResponseEntity<String> handleException(Throwable ex) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex instanceof BaseHttpException) {
            httpStatus = ((BaseHttpException) ex).getHttpStatus();
        }
        return new ResponseEntity<>(new ApiResponse<String>("",ex.getMessage(), httpStatus).toString(), httpStatus);
    }
}

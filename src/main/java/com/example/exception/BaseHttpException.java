package com.example.exception;

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
public interface BaseHttpException {
    HttpStatus getHttpStatus();
}

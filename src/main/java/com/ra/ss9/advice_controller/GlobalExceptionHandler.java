package com.ra.ss9.advice_controller;

import com.ra.ss9.model.dto.response.DataErrorResponse;
import com.ra.ss9.model.dto.response.DataResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    // Xử lý lỗi chung (NullPointerException, RuntimeException, ...)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<DataErrorResponse<String>> handleException(Exception e) {
        log.error("Lỗi không mong muốn: {}", e.getMessage(), e);
        DataErrorResponse<String> errorResponse = new DataErrorResponse<>(
                e.getMessage() != null ? e.getMessage() : "Đã xảy ra lỗi không mong muốn!",
                "INTERNAL_SERVER_ERROR",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Xử lý lỗi dữ liệu không hợp lệ
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<DataErrorResponse<String>> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("Dữ liệu đầu vào không hợp lệ: {}", e.getMessage(), e);
        DataErrorResponse<String> errorResponse = new DataErrorResponse<>(
                e.getMessage(),
                "BAD_REQUEST",
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

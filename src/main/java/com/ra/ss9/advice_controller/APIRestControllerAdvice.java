package com.ra.ss9.advice_controller;


import com.ra.ss9.model.dto.response.DataErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class APIRestControllerAdvice {
    static final Logger log = LoggerFactory.getLogger(APIRestControllerAdvice.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DataErrorResponse<Map<String,String>>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
//        ex.getFieldErrors().forEach((error)->{
//            errors.put(error.getField(),error.getDefaultMessage());
//        });
        for(int i=0;i<ex.getAllErrors().size();i++){
            ObjectError objectError = ex.getAllErrors().get(i);
            errors.put("error - "+i, objectError.getDefaultMessage());
        }
        log.error("{} - error - {}", LocalDateTime.now().toString(),errors.toString());
        return new ResponseEntity<>(new DataErrorResponse<>("errors",errors, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<DataErrorResponse<String>> handleNoSuchElementException(NoSuchElementException ex){
        return new ResponseEntity<>(new DataErrorResponse<>("errors",ex.getLocalizedMessage(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }
}

package com.ra.ss9.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DataErrorResponse<T> {
    private String message;
    private T errors;
    private HttpStatus status;
}

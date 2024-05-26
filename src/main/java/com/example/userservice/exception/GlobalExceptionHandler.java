package com.example.userservice.exception;

import com.example.userservice.dto.GenericResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericResponseDto> handle(Exception ex) {
        log.error("Error occurred while processing request", ex);
        GenericResponseDto errorResponse = GenericResponseDto.builder()
                .result(false)
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericResponseDto> handle(MethodArgumentNotValidException ex) {
        log.error("Error occurred while processing request", ex);
        List<FieldError> fieldErrors = ex.getFieldErrors();
        GenericResponseDto errorResponse = GenericResponseDto.builder()
                .result(false)
                .build();
        if (!fieldErrors.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            fieldErrors.forEach(error -> {
                sb.append(error.getDefaultMessage())
                        .append(" ");
            });
            errorResponse.setMessage(sb.toString());
        } else {
            errorResponse.setMessage(ex.getMessage());
        }
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

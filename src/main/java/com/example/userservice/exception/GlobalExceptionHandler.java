package com.example.userservice.exception;

import com.example.userservice.dto.GenericResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}

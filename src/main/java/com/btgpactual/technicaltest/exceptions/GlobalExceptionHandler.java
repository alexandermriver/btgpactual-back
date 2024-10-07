package com.btgpactual.technicaltest.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.btgpactual.technicaltest.dto.MessageDto;

@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(TransactionExceptions.class)
    public ResponseEntity<MessageDto> handleCustomException(TransactionExceptions e) {
        return ResponseEntity.status(e.getStatus())
                .body(new MessageDto(e.getMessage()));
    };
    
};
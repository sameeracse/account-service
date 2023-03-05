package com.anz.account.controller;

import com.anz.account.dto.ApiError;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
@NoArgsConstructor
public class AccountServiceExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ApiError> handleValidationException(final Exception exception, final HttpServletRequest request) {
        log.error("Validation Exception in account service application {} ", exception.getMessage());
        return new ResponseEntity<>(ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .message("Validation exception")
                .detailedMessage(exception.getMessage())
                .build() , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiError> handleException(final Exception exception, final HttpServletRequest request) {
        log.error("Exception in account service application {} ", exception.getMessage());
        return new ResponseEntity<>(ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(LocalDateTime.now())
                .message("Internal service error!")
                .detailedMessage(exception.getMessage())
                .build() , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

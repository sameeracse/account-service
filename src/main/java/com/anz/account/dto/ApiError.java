package com.anz.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private HttpStatus status;
    private LocalDateTime timestamp;
    private String message;
    private String detailedMessage;

}

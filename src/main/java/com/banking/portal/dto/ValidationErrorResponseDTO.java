package com.banking.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorResponseDTO {
    private String error;
    private String message;
    private String path;
    private LocalDateTime timeStamp;
    private Map<String, String> fieldErrors; 
}

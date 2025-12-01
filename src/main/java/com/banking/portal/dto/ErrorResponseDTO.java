package com.banking.portal.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {

    private String error;
    private String message;
    private String path;
    private LocalDateTime timeStamp;

}

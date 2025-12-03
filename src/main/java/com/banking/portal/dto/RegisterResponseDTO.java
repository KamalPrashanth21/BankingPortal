package com.banking.portal.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDTO {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private Long phone;

    private String email;

    private String role = "CUSTOMER";

    private LocalDateTime createdAt = LocalDateTime.now();

}

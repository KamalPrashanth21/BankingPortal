package com.banking.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionRequestDTO {
    @NonNull
    private String accountNumber;
    @NonNull
    private BigDecimal amount;
    @NonNull
    private String description;
}

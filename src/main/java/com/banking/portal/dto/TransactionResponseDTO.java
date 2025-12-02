package com.banking.portal.dto;

import com.banking.portal.entity.Account;
import com.banking.portal.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDTO {
    @NonNull
    Long id;
    @NonNull
    TransactionType transactionType;
    @NonNull
    BigDecimal amount;
    @NonNull
    String description;
    @NonNull
    LocalDateTime timestamp;
    @NonNull
    Account account;
}

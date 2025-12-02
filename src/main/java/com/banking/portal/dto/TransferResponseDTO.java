package com.banking.portal.dto;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponseDTO {
    @NonNull
    String fromAccountNumber;
    @NonNull
    String toAccountNumber;
    @NonNull
    BigDecimal amount;
    @NonNull
    String description;
    @NonNull
    LocalDateTime timestamp;
}

package com.banking.portal.dto;
import lombok.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequestDTO {
    @NonNull
    String toAccountNumber;
    @NonNull
    String fromAccountNumber;
    @NonNull
    BigDecimal amount;
    @NonNull
    String description;
}

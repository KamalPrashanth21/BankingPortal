package com.banking.portal.dto;
import com.banking.portal.entity.User;
import com.banking.portal.enums.AccountStatus;
import com.banking.portal.enums.AccountType;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDTO {

    @NonNull
    private Long id;
    @NonNull
    private String accountNumber;
    @NonNull
    private BigDecimal balance;
    @NonNull
    private AccountStatus status;
    @NonNull
    private LocalDateTime createdAt;
    @NonNull
    private AccountType accountType;
    @NonNull
    private User user;
}

package com.banking.portal.Service;

import com.banking.portal.dto.TransactionResponseDTO;
import com.banking.portal.dto.TransferResponseDTO;
import com.banking.portal.enums.TransactionType;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {

    TransactionResponseDTO deposit(String username, String accountNumber, BigDecimal amount, String description);
    TransactionResponseDTO withdraw(String username, String accountNumber,BigDecimal amount, String description);
    List<TransactionResponseDTO> getTransactions(String username, String accountNumber);
    Page<@NotNull TransactionResponseDTO> getPageableTransactions(String username, String accountNumber, int page, int size);
    List<TransactionResponseDTO> getTransactionsByType(String username, String accountNumber, TransactionType type);
    TransferResponseDTO transfer(String username, String fromAccountNumber, String toAccountNumber, BigDecimal amount, String description);



}

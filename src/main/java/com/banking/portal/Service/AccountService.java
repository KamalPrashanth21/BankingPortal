package com.banking.portal.Service;

import com.banking.portal.dto.AccountResponseDTO;
import com.banking.portal.enums.AccountStatus;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {

    AccountResponseDTO createAccount(String username);
    List<AccountResponseDTO> getUserAccounts(String username);
    Page<@NotNull AccountResponseDTO> getUserAccounts(String username, int page, int size);
    @NotNull List<AccountResponseDTO> getUserAccountsByStatus(String username, AccountStatus status);
    AccountResponseDTO getAccount(String username, String accountId);



}

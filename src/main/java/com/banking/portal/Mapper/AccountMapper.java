package com.banking.portal.Mapper;

import com.banking.portal.dto.AccountResponseDTO;
import com.banking.portal.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountResponseDTO accountToAccountResponseDTO(Account account); //This converts account entity to DTO object that is returned as a reponse to the user
}

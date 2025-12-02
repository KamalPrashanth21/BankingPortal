package com.banking.portal.Mapper;

import com.banking.portal.dto.TransactionResponseDTO;
import com.banking.portal.dto.TransferResponseDTO;
import com.banking.portal.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionResponseDTO entityToDTO(Transaction transaction);
}

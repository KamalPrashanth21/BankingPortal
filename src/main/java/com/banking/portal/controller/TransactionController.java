package com.banking.portal.controller;

import com.banking.portal.Service.TransactionService;
import com.banking.portal.dto.TransactionRequestDTO;
import com.banking.portal.dto.TransactionResponseDTO;
import com.banking.portal.dto.TransferRequestDTO;
import com.banking.portal.dto.TransferResponseDTO;
import com.banking.portal.enums.TransactionType;
import com.banking.portal.security.CustomUserDetails;
import org.jetbrains.annotations.NotNull;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
    public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService=transactionService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<@NotNull TransactionResponseDTO> deposit(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody TransactionRequestDTO transactionRequestDTO){
        TransactionResponseDTO transactionResponseDTO = transactionService.deposit(userDetails.getUsername(), transactionRequestDTO.getAccountNumber(), transactionRequestDTO.getAmount(), transactionRequestDTO.getDescription());
        return new ResponseEntity<>(transactionResponseDTO, HttpStatus.OK);
    }


    @PostMapping("/withdraw")
    public ResponseEntity<@NotNull TransactionResponseDTO> withdraw(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody TransactionRequestDTO transactionRequestDTO){
        TransactionResponseDTO transactionResponseDTO = transactionService.withdraw(userDetails.getUsername(), transactionRequestDTO.getAccountNumber(),transactionRequestDTO.getAmount(),transactionRequestDTO.getDescription()); //getId() would not be null since only the authenticated object that exists can make an api call.
        return new ResponseEntity<>(transactionResponseDTO,HttpStatus.OK);
    }

    @GetMapping("/getTransactions/{accountNumber}")
    public ResponseEntity<@NotNull List<TransactionResponseDTO>> getTransactions(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable String accountNumber){
        List<TransactionResponseDTO> transactionResponseDTO = transactionService.getTransactions(userDetails.getUsername(), accountNumber);
        return new ResponseEntity<>(transactionResponseDTO,HttpStatus.OK);
    }

    @GetMapping("/getTransactions/{accountNumber}/{type}")
    public ResponseEntity<@NotNull List<TransactionResponseDTO>> getTransactionsByType(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable String accountNumber, @PathVariable TransactionType type){
        List<TransactionResponseDTO> transactionResponseDTO = transactionService.getTransactionsByType(userDetails.getUsername(), accountNumber, type);
        return new ResponseEntity<>(transactionResponseDTO,HttpStatus.OK);
    }

    @GetMapping("/getPagedTransactions/{accountNumber}")
    public ResponseEntity<@NotNull Page<@NotNull TransactionResponseDTO>> getPageableTransactions(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable String accountNumber, @RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "5")int size){
        Page<@NotNull TransactionResponseDTO> transactionResponseDTO = transactionService.getPageableTransactions(userDetails.getUsername(), accountNumber,page,size);
        return new ResponseEntity<>(transactionResponseDTO,HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<@NotNull Map<String,Object>> transfer(@AuthenticationPrincipal CustomUserDetails userDetails, @Valid @RequestBody TransferRequestDTO transferRequestDTO){
        TransferResponseDTO transferResponseDTO = transactionService.transfer(userDetails.getUsername(), transferRequestDTO.getFromAccountNumber(),transferRequestDTO.getToAccountNumber(),transferRequestDTO.getAmount(),transferRequestDTO.getDescription());
        return new ResponseEntity<>(Map.of("message","Congrats! Your fund transfer transaction is successful! ","transaction",transferResponseDTO),HttpStatus.OK);
    }

}

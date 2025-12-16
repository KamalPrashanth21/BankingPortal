package com.banking.portal.controller;

import com.banking.portal.Service.impl.AccountServiceImpl;
import com.banking.portal.dto.AccountResponseDTO;
import com.banking.portal.enums.AccountStatus;
import com.banking.portal.security.CustomUserDetails;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountServiceImpl accountService;

    public AccountController(AccountServiceImpl accountService){
        this.accountService = accountService;
    }

    @PostMapping("/createAccount")
    public ResponseEntity<@NotNull AccountResponseDTO> createAccount(@AuthenticationPrincipal CustomUserDetails userDetails){ //Here, the controller works on calling the service method only with a simple string argument & not the whole auth object.
        String username = userDetails.getUsername();
        AccountResponseDTO accountResponseDTO = accountService.createAccount(username);
        return new ResponseEntity<>(accountResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/userAccounts") //retrieves the accounts of the current user
    public ResponseEntity<@NotNull List<AccountResponseDTO>> getUserAccounts(@AuthenticationPrincipal CustomUserDetails userDetails){
        String username = userDetails.getUsername();
        return new ResponseEntity<>(accountService.getUserAccounts(username),HttpStatus.OK);
    }

    @GetMapping("/userAccounts/status")
    public ResponseEntity<@NotNull List<AccountResponseDTO>> getUserAccountsByStatus(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam AccountStatus status){
        String username= userDetails.getUsername();
        return new ResponseEntity<>(accountService.getUserAccountsByStatus(username,status),HttpStatus.OK);
    }

    @GetMapping("/pagedUserAccounts")
    public ResponseEntity<@NotNull Page<AccountResponseDTO>> getUserAccounts(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "5")int size){
        String username = userDetails.getUsername();
        return new ResponseEntity<>(accountService.getUserAccounts(username,page,size),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<@NotNull AccountResponseDTO> getAccount(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable("id") String accountId){
        String username = userDetails.getUsername();
        AccountResponseDTO accountResponseDTO = accountService.getAccount(username,accountId);
        return new ResponseEntity<>(accountResponseDTO,HttpStatus.OK);
    }

}

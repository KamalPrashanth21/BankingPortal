package com.banking.portal.Service;

import com.banking.portal.Mapper.AccountMapper;
import com.banking.portal.dto.AccountResponseDTO;
import com.banking.portal.entity.Account;
import com.banking.portal.entity.User;
import com.banking.portal.enums.AccountStatus;
import com.banking.portal.enums.AccountType;
import com.banking.portal.exception.ResourceNotFoundException;
import com.banking.portal.repository.AccountRepository;
import com.banking.portal.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;
//    private List<AccountResponseDTO> listOfAccounts;
    public AccountService(AccountRepository accountRepository, UserRepository userRepository, AccountMapper accountMapper){
        this.accountRepository = accountRepository;
        this.userRepository=userRepository;
        this.accountMapper=accountMapper;
    }

    public AccountResponseDTO createAccount(String username){//

        User user = userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User doesn't exist!"));

        Account account = new Account(generateAccountNumber(), BigDecimal.ZERO, AccountStatus.ACTIVE, LocalDateTime.now(), AccountType.SAVINGS, user);
        Account acc = accountRepository.save(account);
        return accountMapper.accountToAccountResponseDTO(acc);
    }

    public List<AccountResponseDTO> getUserAccounts(String username){
        User user = userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User doesn't exist!"));
        List<Account> listOfUserAccounts = accountRepository.findByUser(user);
//        for(Account acc : listOfUserAccounts){
//            AccountResponseDTO accountResponseDTO = accountMapper.accountToAccountResponseDTO(acc);
//            listOfAccounts.add(accountResponseDTO);
//        }
//        return listOfAccounts;
        return listOfUserAccounts.stream()
                .map(accountMapper::accountToAccountResponseDTO)
                .toList();
    }

    public Page<@NotNull AccountResponseDTO> getUserAccounts(String username, int page, int size){
        User user = userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User doesn't exist!"));
        Pageable pageable = PageRequest.of(page,size);
        Page<Account> pg = accountRepository.findByUser(user, pageable);
        return pg.map(accountMapper::accountToAccountResponseDTO);
    }

    public AccountResponseDTO getAccount(String username, String accountId){
        User user = userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User doesn't exist!"));
        Account account = accountRepository.findByAccountNumber(accountId).orElseThrow(()->new ResourceNotFoundException("Account doesn't exist!"));
        return accountMapper.accountToAccountResponseDTO(account);
    }


    public String generateAccountNumber(){
        String randomNumber = UUID.randomUUID().toString();
        return randomNumber.substring(0,10).toUpperCase();
    }



}

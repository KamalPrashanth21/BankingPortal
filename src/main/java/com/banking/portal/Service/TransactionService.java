package com.banking.portal.Service;

import com.banking.portal.Mapper.TransactionMapper;
import com.banking.portal.annotations.AuditFundTransfer;
import com.banking.portal.dto.TransactionResponseDTO;
import com.banking.portal.dto.TransferResponseDTO;
import com.banking.portal.entity.Account;
import com.banking.portal.entity.Transaction;
import com.banking.portal.entity.User;
import com.banking.portal.enums.AccountStatus;
import com.banking.portal.enums.TransactionType;
import com.banking.portal.exception.AccessForbiddenException;
import com.banking.portal.exception.AccountInactiveException;
import com.banking.portal.exception.InsufficientBalanceException;
import com.banking.portal.exception.ResourceNotFoundException;
import com.banking.portal.repository.AccountRepository;
import com.banking.portal.repository.TransactionRepository;
import com.banking.portal.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TransactionService {
    //debit,withdraw, transfer, etc.

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionService(UserRepository userRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, TransactionMapper transactionMapper){
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    public TransactionResponseDTO deposit(String username, String accountNumber, BigDecimal amount, String description){


        User user = userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User doesn't exist"));
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(()->new ResourceNotFoundException("Account doesn't exist!"));

        //We also have to check the corresponding account belongs to the respective user
        if(!(user.getId().equals(account.getUser().getId())))
            throw new AccessForbiddenException("UnAuthorized access!");

        if(account.getStatus()!=AccountStatus.ACTIVE){
            throw new AccountInactiveException("Account is "+ account.getStatus());
        }
            Transaction transaction = new Transaction(null, TransactionType.CREDIT,amount,description,LocalDateTime.now(),account);
//            transaction.setTransactionType();
//            transaction.setAmount();
//            transaction.setDescription();
//            transaction.setTimestamp();
//            transaction.setAccount();

            account.setBalance(account.getBalance().add(amount)); //the add method is a method specific to the BigDecimal object type - balance
            accountRepository.save(account);

            return transactionMapper.entityToDTO(transactionRepository.save(transaction));
    }

    public TransactionResponseDTO withdraw(String username, String accountNumber,BigDecimal amount, String description){

        User user = userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User doesn't exist"));
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(()->new ResourceNotFoundException("Account doesn't exist!"));

        //to ensure the account belongs to the respective user
        if(!(user.getId().equals(account.getUser().getId())))
            throw new AccessForbiddenException("UnAuthorized access!");

        if(account.getStatus()!=AccountStatus.ACTIVE){
            throw new AccountInactiveException("Account is "+ account.getStatus());
        }

        if(account.getBalance().compareTo(amount)<0){
            throw new InsufficientBalanceException("Account has Insufficient Balance!");
        }
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        Transaction transaction = new Transaction(null, TransactionType.DEBIT,amount,description,LocalDateTime.now(),account);
        transactionRepository.save(transaction);

        return transactionMapper.entityToDTO(transaction);
    }

    public List<TransactionResponseDTO> getTransactions(String username, String accountNumber){

        User user = userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User doesn't exist"));
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(()->new ResourceNotFoundException("Account doesn't exist!"));

        //to ensure the account belongs to the respective user
        if(!(user.getId().equals(account.getUser().getId())))
            throw new AccessForbiddenException("UnAuthorized access!");

        List<Transaction> transactionList = transactionRepository.findByAccount(account);
        return transactionList.stream()
                .map(transactionMapper::entityToDTO)
                .toList();
    }

    public Page<@NotNull TransactionResponseDTO> getPageableTransactions(String username, String accountNumber, int page, int size){

        User user = userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User doesn't exist"));
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(()->new ResourceNotFoundException("Account doesn't exist!"));

        //to ensure the account belongs to the respective user
        if(!(user.getId().equals(account.getUser().getId())))
            throw new AccessForbiddenException("UnAuthorized access!");

        Pageable pageable = PageRequest.of(page,size);
        Page<Transaction> transactionList = transactionRepository.findByAccount(account,pageable);
        return transactionList.map(transactionMapper::entityToDTO);
    }

    public List<TransactionResponseDTO> getTransactionsByType(String username, String accountNumber, TransactionType type){

        User user = userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User doesn't exist"));
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(()->new ResourceNotFoundException("Account doesn't exist!"));

        //to ensure the account belongs to the respective user
        if(!(user.getId().equals(account.getUser().getId())))
            throw new AccessForbiddenException("UnAuthorized access!");

        List<Transaction> transactionList = transactionRepository.findByAccountAndTransactionType(account,type);
        return transactionList.stream()
                .map(transactionMapper::entityToDTO)
                .toList();
    }

    @AuditFundTransfer
    public TransferResponseDTO transfer(String username, String fromAccountNumber, String toAccountNumber,BigDecimal amount, String description){

        User user = userRepository.findByUsername(username).orElseThrow(()->new ResourceNotFoundException("User doesn't exist"));
        Account senderAccount = accountRepository.findByAccountNumber(fromAccountNumber).orElseThrow(()->new ResourceNotFoundException("Sender Account doesn't exist!"));

        if(!(user.getId()==(senderAccount.getUser().getId())))
            throw new AccessForbiddenException("UnAuthorized access!");

        Account receiverAccount = accountRepository.findByAccountNumber(toAccountNumber).orElseThrow(()->new ResourceNotFoundException("Receiver Account doesn't exist!"));
        if(senderAccount.getId().equals(receiverAccount.getId())) throw new IllegalArgumentException("Both accounts are same!");

        if(senderAccount.getStatus()!=AccountStatus.ACTIVE || receiverAccount.getStatus()!=AccountStatus.ACTIVE){
            throw new AccountInactiveException("Account is not active!");
        }

        if(senderAccount.getBalance().compareTo(amount)<0)throw new InsufficientBalanceException("Balance is insufficient!");

        senderAccount.setBalance(senderAccount.getBalance().subtract(amount));//subtract from sender
        receiverAccount.setBalance(receiverAccount.getBalance().add(amount));//add to receiver

        Transaction senderTransaction = new Transaction(null,TransactionType.DEBIT,amount,description,LocalDateTime.now(),senderAccount);
        Transaction receiverTransaction = new Transaction(null,TransactionType.CREDIT,amount,description,LocalDateTime.now(),receiverAccount);

        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);

        transactionRepository.save(senderTransaction);
        transactionRepository.save(receiverTransaction);

        return new TransferResponseDTO(fromAccountNumber,toAccountNumber,amount,description,LocalDateTime.now());
    }
}

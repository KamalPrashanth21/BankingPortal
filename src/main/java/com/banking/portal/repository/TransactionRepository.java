package com.banking.portal.repository;

import com.banking.portal.entity.Account;
import com.banking.portal.entity.Transaction;
import com.banking.portal.enums.TransactionType;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<@NotNull Transaction, @NotNull Long> {
        List<Transaction> findByAccount(Account account); //Gets the list of transactions for the specific account
        List<Transaction> findByAccountAndTransactionType(Account account, TransactionType transactionType); //Gets the list of either debit or credit transactions for the specific account
        Page<@NotNull Transaction> findByAccount(Account account, Pageable pageable);
}

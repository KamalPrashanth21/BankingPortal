package com.banking.portal.repository;

import com.banking.portal.entity.Account;
import com.banking.portal.entity.User;
import com.banking.portal.enums.AccountStatus;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<@NotNull Account, @NotNull Long> {
        Optional<Account> findByAccountNumber(String accountNumber);
        List<Account> findByUser(User user);
        List<Account> findByStatusAndUser(AccountStatus status, User user);
        Page<@NotNull Account> findByUser(User user, Pageable pageable);

}

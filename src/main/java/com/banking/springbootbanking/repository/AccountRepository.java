package com.banking.springbootbanking.repository;

import com.banking.springbootbanking.model.Account;
import com.banking.springbootbanking.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByAccountNumber(String accountNumber);
    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findByLocalUser(LocalUser localUser);
}

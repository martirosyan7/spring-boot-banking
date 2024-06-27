package com.banking.springbootbanking.service.impl;

import com.banking.springbootbanking.model.Account;
import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.repository.AccountRepository;
import com.banking.springbootbanking.repository.LocalUserRepository;
import com.banking.springbootbanking.service.AccountService;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    private LocalUserRepository userRepository;

    @Override
    public Account createAccount(LocalUser localUser, String accountNumber, CurrencyType currencyType, Long balance) {
        if (accountRepository.existsByAccountNumber(accountNumber)) {
            throw new RuntimeException("Account with this account number already exists");
        }

        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setCurrencyType(currencyType);
        account.setBalance(balance);
        account.setLocalUser(localUser);

        return accountRepository.save(account);
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}

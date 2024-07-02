package com.banking.springbootbanking.service.impl;

import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.model.dto.AccountDTO;
import com.banking.springbootbanking.model.dto.mapper.AccountMapper;
import com.banking.springbootbanking.exception.AccountNotFoundException;
import com.banking.springbootbanking.model.Account;
import com.banking.springbootbanking.repository.AccountRepository;
import com.banking.springbootbanking.repository.LocalUserRepository;
import com.banking.springbootbanking.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    private LocalUserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository, LocalUserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AccountDTO createAccount(AccountDTO accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDTO getAccountById(Long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account does not exist"));
        return AccountMapper.mapToAccountDto(account);
    }


    @Override
    public List<AccountDTO> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map((account) -> AccountMapper.mapToAccountDto(account))
                .collect(Collectors.toList());
    }

    @Override
    public List<AccountDTO> getAccountsByUser(LocalUser user) {
        List<Account> accounts = accountRepository.findByLocalUser(user);
        return accounts.stream()
                .map(AccountMapper::mapToAccountDto)
                .collect(Collectors.toList());
    }
}

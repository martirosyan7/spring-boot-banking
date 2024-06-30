package com.banking.springbootbanking.service;

import com.banking.springbootbanking.model.dto.AccountDTO;

import java.util.List;

public interface AccountService {

    AccountDTO createAccount(AccountDTO accountDto);

    AccountDTO getAccountById(Long id);

    List<AccountDTO> getAllAccounts();

}

package com.banking.springbootbanking.service;

import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.model.dto.AccountDTO;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    AccountDTO createAccount(AccountDTO accountDto);

    AccountDTO getAccountById(UUID id);

    List<AccountDTO> getAllAccounts();

    List<AccountDTO> getAccountsByUser(LocalUser user);

}

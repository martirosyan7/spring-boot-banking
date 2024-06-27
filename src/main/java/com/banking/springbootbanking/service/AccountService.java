package com.banking.springbootbanking.service;

import com.banking.springbootbanking.model.Account;
import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.utils.enums.CurrencyType;

import java.util.List;

public interface AccountService {

    Account createAccount(LocalUser localUser, String accountNumber, CurrencyType currencyType, Long balance);

    Account getAccountById(Long id);

    List<Account> getAllAccounts();

}

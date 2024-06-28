package com.banking.springbootbanking.dto.mapper;

import com.banking.springbootbanking.dto.AccountDTO;
import com.banking.springbootbanking.model.Account;

public class AccountMapper {

    public static Account mapToAccount(AccountDTO accountDto) {
        Account account = new Account(
                accountDto.getId(),
                accountDto.getBalance(),
                accountDto.getAccountNumber(),
                accountDto.getLocalUserId(),
                accountDto.getCurrencyType()
        );
        return account;
    }

    public static AccountDTO mapToAccountDto(Account account) {
        AccountDTO accountDto = new AccountDTO(
                account.getId(),
                account.getBalance(),
                account.getAccountNumber(),
                account.getLocalUser(),
                account.getCurrencyType()
        );
        return accountDto;
    }
}

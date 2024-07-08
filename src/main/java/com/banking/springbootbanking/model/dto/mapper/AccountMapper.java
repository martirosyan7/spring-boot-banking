package com.banking.springbootbanking.model.dto.mapper;

import com.banking.springbootbanking.model.dto.AccountDTO;
import com.banking.springbootbanking.model.Account;

public class AccountMapper {

    public static Account mapToAccount(AccountDTO accountDto) {
        Account account = new Account(
                accountDto.getId(),
                accountDto.getBalance(),
                accountDto.getAccountNumber(),
                accountDto.getLocalUserId(),
                accountDto.getCurrencyType(),
                accountDto.getBank()
        );
        return account;
    }

    public static AccountDTO mapToAccountDto(Account account) {
        AccountDTO accountDto = new AccountDTO(
                account.getId(),
                account.getBalance(),
                account.getAccountNumber(),
                account.getLocalUser(),
                account.getCurrencyType(),
                account.getBank()
        );
        return accountDto;
    }
}

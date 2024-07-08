package com.banking.springbootbanking.model.dto.mapper;

import com.banking.springbootbanking.model.Bank;
import com.banking.springbootbanking.model.dto.BankDTO;

public class BankMapper {
    public static Bank mapToBank(BankDTO bankDto) {
        Bank bank = new Bank(
                bankDto.getId(),
                bankDto.getName(),
                bankDto.getAddress(),
                bankDto.getIssuerNumber(),
                bankDto.getAccountIdentificationNumber(),
                bankDto.getCards(),
                bankDto.getAccounts()
        );
        return bank;
    }

    public static BankDTO mapToBankDto(Bank bank) {
        BankDTO bankDto = new BankDTO(
                bank.getId(),
                bank.getName(),
                bank.getAddress(),
                bank.getIssuerNumber(),
                bank.getAccountIdentificationNumber(),
                bank.getCards(),
                bank.getAccounts()
        );
        return bankDto;
    }
}

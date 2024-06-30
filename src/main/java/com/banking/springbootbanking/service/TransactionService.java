package com.banking.springbootbanking.service;

import com.banking.springbootbanking.model.dto.TransactionDTO;
import com.banking.springbootbanking.utils.enums.CurrencyType;

import java.util.List;

public interface TransactionService {
    TransactionDTO createTransaction(TransactionDTO transactionDTO);

    TransactionDTO getTransactionById(Long id);

    TransactionDTO withdraw(String number, Long amount, CurrencyType currency);

    TransactionDTO deposit(String number, Long amount, CurrencyType currency);

    TransactionDTO transfer(String senderNumber, String recipientNumber, Long amount, String description, CurrencyType currency);

    List<TransactionDTO> getAllTransactions();
}

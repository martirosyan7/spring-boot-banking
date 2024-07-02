package com.banking.springbootbanking.service;

import com.banking.springbootbanking.model.Transaction;
import com.banking.springbootbanking.model.dto.TransactionDTO;
import com.banking.springbootbanking.utils.enums.CurrencyType;

import java.util.List;

public interface TransactionService {
    TransactionDTO createTransaction(TransactionDTO transactionDTO);

    TransactionDTO getTransactionById(Long id);

    TransactionDTO accountWithdraw(String number, Long amount, CurrencyType currency);

    TransactionDTO cardWithdraw(String number, Long amount, CurrencyType currency);

    TransactionDTO accountDeposit(String number, Long amount, CurrencyType currency);

    TransactionDTO cardDeposit(String number, Long amount, CurrencyType currency);

    TransactionDTO accountTransfer(String senderNumber, String recipientNumber, Long amount, String description, CurrencyType currency);

    TransactionDTO cardTransfer(String senderNumber, String recipientNumber, Long amount, String description, CurrencyType currency);

    List<TransactionDTO> getAllTransactions();
}

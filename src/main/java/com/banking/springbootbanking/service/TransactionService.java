package com.banking.springbootbanking.service;

import com.banking.springbootbanking.model.Transaction;
import com.banking.springbootbanking.model.dto.TransactionDTO;
import com.banking.springbootbanking.utils.enums.CurrencyType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface TransactionService {
    TransactionDTO createTransaction(TransactionDTO transactionDTO);

    TransactionDTO getTransactionById(UUID id);

    TransactionDTO accountWithdraw(String number, BigDecimal amount, CurrencyType currency);

    TransactionDTO cardWithdraw(String number, BigDecimal amount, CurrencyType currency);

    TransactionDTO accountDeposit(String number, BigDecimal amount, CurrencyType currency);

    TransactionDTO cardDeposit(String number, BigDecimal amount, CurrencyType currency);

    TransactionDTO accountTransfer(String senderNumber, String recipientNumber, BigDecimal amount, String description, CurrencyType currency);

    TransactionDTO cardTransfer(String senderNumber, String recipientNumber, BigDecimal amount, String description, CurrencyType currency);

    List<TransactionDTO> getAllTransactions();
}

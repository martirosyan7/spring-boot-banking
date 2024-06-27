package com.banking.springbootbanking.service;

import com.banking.springbootbanking.model.Transaction;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import com.banking.springbootbanking.utils.enums.TransactionStatus;
import com.banking.springbootbanking.utils.enums.TransactionType;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    public Transaction createTransaction(Long amount, String description, String senderNumber, String recipientNumber, TransactionType type, TransactionStatus status, CurrencyType currency);

    Transaction getTransactionById(Long id);

    List<Transaction> getAllTransactions();
}

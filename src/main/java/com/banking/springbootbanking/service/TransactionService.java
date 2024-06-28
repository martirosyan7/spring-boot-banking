package com.banking.springbootbanking.service;

import com.banking.springbootbanking.dto.TransactionDTO;
import com.banking.springbootbanking.model.Transaction;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import com.banking.springbootbanking.utils.enums.TransactionStatus;
import com.banking.springbootbanking.utils.enums.TransactionType;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    TransactionDTO createTransaction(TransactionDTO transactionDTO);

    TransactionDTO getTransactionById(Long id);

    List<TransactionDTO> getAllTransactions();
}

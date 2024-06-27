package com.banking.springbootbanking.service.impl;

import com.banking.springbootbanking.model.Transaction;
import com.banking.springbootbanking.repository.TransactionRepository;
import com.banking.springbootbanking.service.TransactionService;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import com.banking.springbootbanking.utils.enums.TransactionStatus;
import com.banking.springbootbanking.utils.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(Long amount, String description, String senderNumber, String recipientNumber, TransactionType type, TransactionStatus status, CurrencyType currency) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setSenderNumber(senderNumber);
        transaction.setRecipientNumber(recipientNumber);
        transaction.setType(type);
        transaction.setStatus(status);
        transaction.setCurrency(currency);

        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}

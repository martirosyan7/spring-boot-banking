package com.banking.springbootbanking.controller;


import com.banking.springbootbanking.model.Transaction;
import com.banking.springbootbanking.service.AccountService;
import com.banking.springbootbanking.service.CardService;
import com.banking.springbootbanking.service.TransactionService;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import com.banking.springbootbanking.utils.enums.TransactionStatus;
import com.banking.springbootbanking.utils.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CardService cardService;

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(Long amount, String description, String senderNumber, String recipientNumber, TransactionType type, TransactionStatus status, CurrencyType currency) {
        try {
            Transaction transaction = transactionService.createTransaction(amount, description, senderNumber, recipientNumber, type, status, currency);
            return ResponseEntity.ok(transaction);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

package com.banking.springbootbanking.controller;


import com.banking.springbootbanking.dto.LocalUserDTO;
import com.banking.springbootbanking.dto.TransactionDTO;
import com.banking.springbootbanking.model.Transaction;
import com.banking.springbootbanking.service.AccountService;
import com.banking.springbootbanking.service.CardService;
import com.banking.springbootbanking.service.TransactionService;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import com.banking.springbootbanking.utils.enums.TransactionStatus;
import com.banking.springbootbanking.utils.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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
    public ResponseEntity<TransactionDTO> createTransaction(@RequestParam Long amount, @RequestParam String description,
                                                         @RequestParam String senderNumber, @RequestParam String recipientNumber,
                                                         @RequestParam TransactionType type, @RequestParam TransactionStatus status,
                                                         @RequestParam CurrencyType currency) {
        TransactionDTO transactionDto = new TransactionDTO();
        transactionDto.setAmount(amount);
        transactionDto.setDescription(description);
        transactionDto.setSenderNumber(senderNumber);
        transactionDto.setRecipientNumber(recipientNumber);
        transactionDto.setType(type);
        transactionDto.setStatus(status);
        transactionDto.setCurrency(currency);

        TransactionDTO createdTransaction = transactionService.createTransaction(transactionDto);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        TransactionDTO transactionDto = transactionService.getTransactionById(id);
        return ResponseEntity.ok(transactionDto);
    }

    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        List<TransactionDTO> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
}

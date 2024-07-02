package com.banking.springbootbanking.controller;


import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.model.dto.AccountDTO;
import com.banking.springbootbanking.model.dto.CardDTO;
import com.banking.springbootbanking.model.dto.TransactionDTO;
import com.banking.springbootbanking.service.AccountService;
import com.banking.springbootbanking.service.CardService;
import com.banking.springbootbanking.service.TransactionService;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import com.banking.springbootbanking.utils.enums.TransactionStatus;
import com.banking.springbootbanking.utils.enums.TransactionType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/account/withdraw")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<TransactionDTO> accountWithdraw(@AuthenticationPrincipal LocalUser authenticatedUser,
                                                          @RequestParam String number,
                                                          @RequestParam Long amount,
                                                          @RequestParam CurrencyType currency) {
        List<AccountDTO> accounts = accountService.getAccountsByUser(authenticatedUser);
        if (accounts.stream().noneMatch(account -> account.getAccountNumber().equals(number))) {
            throw new RuntimeException("Account does not belong to user");
        }
        TransactionDTO transactionDto = transactionService.accountWithdraw(number, amount, currency);
        return ResponseEntity.ok(transactionDto);
    }

    @PostMapping("/card/withdraw")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<TransactionDTO> cardWithdraw(@AuthenticationPrincipal LocalUser authenticatedUser,
                                                       @RequestParam String number,
                                                       @RequestParam Long amount,
                                                       @RequestParam CurrencyType currency) {
        List<CardDTO> cards = cardService.getCardsByUser(authenticatedUser);
        if (cards.stream().noneMatch(card -> card.getCardNumber().equals(number))) {
            throw new RuntimeException("Account does not belong to user");
        }
        TransactionDTO transactionDto = transactionService.cardWithdraw(number, amount, currency);
        return ResponseEntity.ok(transactionDto);
    }

    @PostMapping("/account/deposit")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<TransactionDTO> accountDeposit(@AuthenticationPrincipal LocalUser authenticatedUser,
                                                         @RequestParam String number,
                                                         @RequestParam Long amount,
                                                         @RequestParam CurrencyType currency) {
        List<AccountDTO> accounts = accountService.getAccountsByUser(authenticatedUser);
        if (accounts.stream().noneMatch(account -> account.getAccountNumber().equals(number))) {
            throw new RuntimeException("Account does not belong to user");
        }
        TransactionDTO transactionDto = transactionService.accountDeposit(number, amount, currency);
        return ResponseEntity.ok(transactionDto);
    }

    @PostMapping("/card/deposit")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<TransactionDTO> cardDeposit(@AuthenticationPrincipal LocalUser authenticatedUser,
                                                         @RequestParam String number,
                                                         @RequestParam Long amount,
                                                         @RequestParam CurrencyType currency) {
        List<CardDTO> cards = cardService.getCardsByUser(authenticatedUser);
        if (cards.stream().noneMatch(card -> card.getCardNumber().equals(number))) {
            throw new RuntimeException("Account does not belong to user");
        }
        TransactionDTO transactionDto = transactionService.cardDeposit(number, amount, currency);
        return ResponseEntity.ok(transactionDto);
    }

    @PostMapping("/account/transfer")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<TransactionDTO> accountTransfer(@AuthenticationPrincipal LocalUser authenticatedUser,
                                                          @RequestParam String senderAccountNumber,
                                                          @RequestParam String recipientAccountNumber,
                                                          @RequestParam Long amount,
                                                          @RequestParam String description,
                                                          @RequestParam CurrencyType currency) {

        List<AccountDTO> accounts = accountService.getAccountsByUser(authenticatedUser);
        if (accounts.stream().noneMatch(account -> account.getAccountNumber().equals(senderAccountNumber))) {
            throw new RuntimeException("Account does not belong to user");
        }

        TransactionDTO transactionDto = transactionService.accountTransfer(senderAccountNumber, recipientAccountNumber, amount, description, currency);
        return ResponseEntity.ok(transactionDto);
    }

    @PostMapping("/card/transfer")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<TransactionDTO> cardTransfer(@AuthenticationPrincipal LocalUser authenticatedUser,
                                                          @RequestParam String senderAccountNumber,
                                                          @RequestParam String recipientAccountNumber,
                                                          @RequestParam Long amount,
                                                          @RequestParam String description,
                                                          @RequestParam CurrencyType currency) {

        List<CardDTO> cards = cardService.getCardsByUser(authenticatedUser);
        if (cards.stream().noneMatch(account -> account.getCardNumber().equals(senderAccountNumber))) {
            throw new RuntimeException("Account does not belong to user");
        }

        TransactionDTO transactionDto = transactionService.cardTransfer(senderAccountNumber, recipientAccountNumber, amount, description, currency);
        return ResponseEntity.ok(transactionDto);
    }


    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getAllTransactions() {
        List<TransactionDTO> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
}

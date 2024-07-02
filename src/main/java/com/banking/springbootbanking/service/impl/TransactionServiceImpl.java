package com.banking.springbootbanking.service.impl;

import com.banking.springbootbanking.model.Card;
import com.banking.springbootbanking.model.dto.TransactionDTO;
import com.banking.springbootbanking.model.dto.mapper.TransactionMapper;
import com.banking.springbootbanking.exception.TransactionNotFoundException;
import com.banking.springbootbanking.model.Account;
import com.banking.springbootbanking.model.Transaction;
import com.banking.springbootbanking.repository.AccountRepository;
import com.banking.springbootbanking.repository.CardRepository;
import com.banking.springbootbanking.repository.TransactionRepository;
import com.banking.springbootbanking.service.TransactionService;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import com.banking.springbootbanking.utils.enums.TransactionStatus;
import com.banking.springbootbanking.utils.enums.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CardRepository cardRepository;

    @Override
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = TransactionMapper.mapToTransaction(transactionDTO);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return TransactionMapper.mapToTransactionDto(savedTransaction);
    }

    @Override
    public TransactionDTO getTransactionById(Long id) {
        Transaction transaction = transactionRepository
                .findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction does not exist"));
        return TransactionMapper.mapToTransactionDto(transaction);
    }

    @Override
    public TransactionDTO accountWithdraw(String number, Long amount, CurrencyType currency) {
        Account account = accountRepository
                .findByAccountNumber(number)
                .orElseThrow(() -> new TransactionNotFoundException("Account does not exist"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTime(LocalDateTime.now());
        transaction.setLocalUser(account.getLocalUser());
        transaction.setDescription("Withdraw");
        transaction.setSenderNumber(number);
        transaction.setRecipientNumber(""); // no recipient for withdraw
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setCurrency(currency);
        transactionRepository.save(transaction);

        TransactionDTO transactionDTO = TransactionMapper.mapToTransactionDto(transaction);

        return transactionDTO;
    }

    @Override
    public TransactionDTO cardWithdraw(String number, Long amount, CurrencyType currency) {
        Card card = cardRepository
                .findByCardNumber(number)
                .orElseThrow(() -> new TransactionNotFoundException("Card does not exist"));

        if (card.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        card.setBalance(card.getBalance() - amount);
        cardRepository.save(card);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTime(LocalDateTime.now());
        transaction.setLocalUser(card.getLocalUser());
        transaction.setDescription("Withdraw");
        transaction.setSenderNumber(number);
        transaction.setRecipientNumber(""); // no recipient for withdraw
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setCurrency(currency);
        transactionRepository.save(transaction);

        TransactionDTO transactionDTO = TransactionMapper.mapToTransactionDto(transaction);

        return transactionDTO;
    }

    @Override
    public TransactionDTO accountDeposit(String number, Long amount, CurrencyType currency) {
        Account account = accountRepository
                .findByAccountNumber(number)
                .orElseThrow(() -> new TransactionNotFoundException("Account does not exist"));

        if (amount <= 0) throw new RuntimeException("Invalid amount");

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTime(LocalDateTime.now());
        transaction.setLocalUser(account.getLocalUser());
        transaction.setDescription("Deposit");
        transaction.setSenderNumber(""); // no sender for deposit
        transaction.setRecipientNumber(number);
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setCurrency(currency);
        transactionRepository.save(transaction);

        TransactionDTO transactionDTO = TransactionMapper.mapToTransactionDto(transaction);

        return transactionDTO;
    }

    @Override
    public TransactionDTO cardDeposit(String number, Long amount, CurrencyType currency) {
        Card card = cardRepository
                .findByCardNumber(number)
                .orElseThrow(() -> new TransactionNotFoundException("Card does not exist"));

        if (amount <= 0) throw new RuntimeException("Invalid amount");

        card.setBalance(card.getBalance() + amount);
        cardRepository.save(card);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTime(LocalDateTime.now());
        transaction.setLocalUser(card.getLocalUser());
        transaction.setDescription("Deposit");
        transaction.setSenderNumber(""); // no sender for deposit
        transaction.setRecipientNumber(number);
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setCurrency(currency);
        transactionRepository.save(transaction);

        TransactionDTO transactionDTO = TransactionMapper.mapToTransactionDto(transaction);

        return transactionDTO;
    }

    @Override
    public TransactionDTO accountTransfer(String senderNumber, String recipientNumber, Long amount, String description, CurrencyType currency) {
        Account sender = accountRepository
                .findByAccountNumber(senderNumber)
                .orElseThrow(() -> new TransactionNotFoundException("Sender account does not exist"));

        Account recipient = accountRepository
                .findByAccountNumber(recipientNumber)
                .orElseThrow(() -> new TransactionNotFoundException("Recipient account does not exist"));

        if (sender.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);

        accountRepository.save(sender);
        accountRepository.save(recipient);

        Transaction transaction1 = new Transaction();
        transaction1.setAmount(amount);
        transaction1.setLocalUser(sender.getLocalUser());
        transaction1.setTime(LocalDateTime.now());
        transaction1.setDescription(description);
        transaction1.setSenderNumber(senderNumber);
        transaction1.setRecipientNumber(recipientNumber);
        transaction1.setType(TransactionType.TRANSFER);
        transaction1.setStatus(TransactionStatus.COMPLETED);
        transaction1.setCurrency(currency);

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(amount);
        transaction2.setLocalUser(recipient.getLocalUser());
        transaction2.setTime(LocalDateTime.now());
        transaction2.setDescription(description);
        transaction2.setSenderNumber(senderNumber);
        transaction2.setRecipientNumber(recipientNumber);
        transaction2.setType(TransactionType.TRANSFER);
        transaction2.setStatus(TransactionStatus.COMPLETED);
        transaction2.setCurrency(currency);

        transactionRepository.save(transaction1);
        transactionRepository.save(transaction2);

        return TransactionMapper.mapToTransactionDto(transaction1);

    }

    @Override
    public TransactionDTO cardTransfer(String senderNumber, String recipientNumber, Long amount, String description, CurrencyType currency) {
        Card sender = cardRepository
                .findByCardNumber(senderNumber)
                .orElseThrow(() -> new TransactionNotFoundException("Sender card does not exist"));
        Card recipient = cardRepository
                .findByCardNumber(recipientNumber)
                .orElseThrow(() -> new TransactionNotFoundException("Recipient card does not exist"));

        if (sender.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);

        cardRepository.save(sender);
        cardRepository.save(recipient);

        Transaction transaction1 = new Transaction();
        transaction1.setAmount(amount);
        transaction1.setLocalUser(sender.getLocalUser());
        transaction1.setTime(LocalDateTime.now());
        transaction1.setDescription(description);
        transaction1.setSenderNumber(senderNumber);
        transaction1.setRecipientNumber(recipientNumber);
        transaction1.setType(TransactionType.TRANSFER);
        transaction1.setStatus(TransactionStatus.COMPLETED);
        transaction1.setCurrency(currency);

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(amount);
        transaction2.setLocalUser(recipient.getLocalUser());
        transaction2.setTime(LocalDateTime.now());
        transaction2.setDescription(description);
        transaction2.setSenderNumber(senderNumber);
        transaction2.setRecipientNumber(recipientNumber);
        transaction2.setType(TransactionType.TRANSFER);
        transaction2.setStatus(TransactionStatus.COMPLETED);
        transaction2.setCurrency(currency);

        transactionRepository.save(transaction1);
        transactionRepository.save(transaction2);

        return TransactionMapper.mapToTransactionDto(transaction1);
    }

    @Override
    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map((transaction) -> TransactionMapper.mapToTransactionDto(transaction))
                .collect(Collectors.toList());
    }
}

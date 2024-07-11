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
import com.banking.springbootbanking.service.currency.CurrencyConversionService;
import com.banking.springbootbanking.service.currency.ExchangeRateService;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import com.banking.springbootbanking.utils.enums.TransactionStatus;
import com.banking.springbootbanking.utils.enums.TransactionType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CurrencyConversionService currencyConversionService;

//    public TransactionServiceImpl(CurrencyConversionService currencyConversionService) {
//        this.currencyConversionService = currencyConversionService;
//    }

    @Override
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = TransactionMapper.mapToTransaction(transactionDTO);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return TransactionMapper.mapToTransactionDto(savedTransaction);
    }

    @Override
    public TransactionDTO getTransactionById(UUID id) {
        Transaction transaction = transactionRepository
                .findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction does not exist"));
        return TransactionMapper.mapToTransactionDto(transaction);
    }

    @Override
    @Transactional
    public TransactionDTO accountWithdraw(String number, BigDecimal amount, CurrencyType currency) {
        Account account = accountRepository
                .findByAccountNumber(number)
                .orElseThrow(() -> new TransactionNotFoundException("Account does not exist"));

        if (currencyConversionService.convert(currency, CurrencyType.USD, account.getBalance())
                .compareTo(currencyConversionService.convert(currency, CurrencyType.USD, amount)) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));
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
    @Transactional
    public TransactionDTO cardWithdraw(String number, BigDecimal amount, CurrencyType currency) {
        Card card = cardRepository
                .findByCardNumber(number)
                .orElseThrow(() -> new TransactionNotFoundException("Card does not exist"));

        if (card.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        card.setBalance(card.getBalance().subtract(amount));
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
    @Transactional
    public TransactionDTO accountDeposit(String number, BigDecimal amount, CurrencyType currency) {
        Account account = accountRepository
                .findByAccountNumber(number)
                .orElseThrow(() -> new TransactionNotFoundException("Account does not exist"));

        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new RuntimeException("Invalid amount");

        account.setBalance(account.getBalance().add(amount));
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
    @Transactional
    public TransactionDTO cardDeposit(String number, BigDecimal amount, CurrencyType currency) {
        Card card = cardRepository
                .findByCardNumber(number)
                .orElseThrow(() -> new TransactionNotFoundException("Card does not exist"));

        if (amount.compareTo(BigDecimal.ZERO) <= 0) throw new RuntimeException("Invalid amount");

        card.setBalance(card.getBalance().add(amount));
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
    @Transactional
    public TransactionDTO accountTransfer(String senderNumber, String recipientNumber, BigDecimal amount, String description, CurrencyType currency) {
        Account sender = accountRepository
                .findByAccountNumber(senderNumber)
                .orElseThrow(() -> new TransactionNotFoundException("Sender account does not exist"));

        Account recipient = accountRepository
                .findByAccountNumber(recipientNumber)
                .orElseThrow(() -> new TransactionNotFoundException("Recipient account does not exist"));

        BigDecimal senderBalanceConverted = currencyConversionService.convert(currency, CurrencyType.USD, sender.getBalance());
        BigDecimal amountConverted = currencyConversionService.convert(currency, CurrencyType.USD, amount);

        if (senderBalanceConverted.compareTo(amountConverted) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        BigDecimal amountInSenderCurrency = currencyConversionService.convert(currency, sender.getCurrencyType(), amount);
        BigDecimal amountInRecipientCurrency = currencyConversionService.convert(currency, recipient.getCurrencyType(), amount);

        sender.setBalance(sender.getBalance().subtract(amountInSenderCurrency));
        recipient.setBalance(recipient.getBalance().add(amountInRecipientCurrency));

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
    @Transactional
    public TransactionDTO cardTransfer(String senderNumber, String recipientNumber, BigDecimal amount, String description, CurrencyType currency) {
        Card sender = cardRepository
                .findByCardNumber(senderNumber)
                .orElseThrow(() -> new TransactionNotFoundException("Sender card does not exist"));
        Card recipient = cardRepository
                .findByCardNumber(recipientNumber)
                .orElseThrow(() -> new TransactionNotFoundException("Recipient card does not exist"));

        // Check if the sender has enough balance in USD
        BigDecimal senderBalanceConverted = currencyConversionService.convert(currency, CurrencyType.USD, sender.getBalance());
        BigDecimal amountConverted = currencyConversionService.convert(currency, CurrencyType.USD, amount);

        if (senderBalanceConverted.compareTo(amountConverted) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        if (!sender.getCurrencyType().equals(recipient.getCurrencyType())) {
            // Convert amount to USD first (assuming amount is in sender's currency)
            BigDecimal amountInUSD = currencyConversionService.convert(currency, CurrencyType.USD, amount);

            // Apply 1% tax on the transaction amount in USD
            BigDecimal taxRate = BigDecimal.valueOf(1.01); // BigDecimal for tax rate (1%)
            BigDecimal totalAmountInUSD = amountInUSD.multiply(taxRate);

            // Convert tax-adjusted amount in USD to sender's currency
            BigDecimal amountInSenderCurrency = currencyConversionService.convert(CurrencyType.USD, sender.getCurrencyType(), totalAmountInUSD);

            // Deduct amount from sender's balance
            sender.setBalance(sender.getBalance().subtract(amountInSenderCurrency));

            // Convert original amount in USD to recipient's currency
            BigDecimal amountInRecipientCurrency = currencyConversionService.convert(CurrencyType.USD, recipient.getCurrencyType(), amountInUSD);

            // Add amount to recipient's balance
            recipient.setBalance(recipient.getBalance().add(amountInRecipientCurrency));
        } else {
            // Same currency transfer
            sender.setBalance(sender.getBalance().subtract(amount));
            recipient.setBalance(recipient.getBalance().add(amount));
        }

        cardRepository.save(sender);
        cardRepository.save(recipient);

        // Create transactions
        LocalDateTime now = LocalDateTime.now();

        Transaction transaction1 = createTransaction(sender, recipient, amount, description, senderNumber, recipientNumber, currency, now);
        Transaction transaction2 = createTransaction(recipient, sender, amount, description, senderNumber, recipientNumber, currency, now);

        // Save transactions
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

    private Transaction createTransaction(Card sender, Card recipient, BigDecimal amount, String description, String senderNumber, String recipientNumber, CurrencyType currency, LocalDateTime time) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setLocalUser(sender.getLocalUser());
        transaction.setTime(time);
        transaction.setDescription(description);
        transaction.setSenderNumber(senderNumber);
        transaction.setRecipientNumber(recipientNumber);
        transaction.setType(TransactionType.TRANSFER);
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setCurrency(currency);
        return transaction;
    }
}

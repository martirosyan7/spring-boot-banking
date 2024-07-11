package com.banking.springbootbanking.service;

import com.banking.springbootbanking.exception.TransactionNotFoundException;
import com.banking.springbootbanking.model.Account;
import com.banking.springbootbanking.model.Card;
import com.banking.springbootbanking.model.Transaction;
import com.banking.springbootbanking.model.dto.TransactionDTO;
import com.banking.springbootbanking.model.dto.mapper.TransactionMapper;
import com.banking.springbootbanking.repository.AccountRepository;
import com.banking.springbootbanking.repository.CardRepository;
import com.banking.springbootbanking.repository.TransactionRepository;
import com.banking.springbootbanking.service.currency.CurrencyConversionService;
import com.banking.springbootbanking.service.impl.TransactionServiceImpl;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import com.banking.springbootbanking.utils.enums.TransactionStatus;
import com.banking.springbootbanking.utils.enums.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private CurrencyConversionService currencyConversionService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Account account;
    private Card card;
    private Transaction transaction;
    private TransactionDTO transactionDTO;

    @BeforeEach
    public void setUp() {
        account = new Account();
        account.setId(UUID.randomUUID());
        account.setAccountNumber("123456789");
        account.setBalance(BigDecimal.valueOf(1000));
        account.setCurrencyType(CurrencyType.USD);

        card = new Card();
        card.setId(UUID.randomUUID());
        card.setCardNumber("987654321");
        card.setBalance(BigDecimal.valueOf(500));
        card.setCurrencyType(CurrencyType.USD);

        transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setAmount(BigDecimal.valueOf(100));
        transaction.setTime(LocalDateTime.now());
        transaction.setLocalUser(account.getLocalUser());
        transaction.setDescription("Test Transaction");
        transaction.setSenderNumber("123456789");
        transaction.setRecipientNumber("987654321");
        transaction.setType(TransactionType.TRANSFER);
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setCurrency(CurrencyType.USD);

        transactionDTO = TransactionMapper.mapToTransactionDto(transaction);
    }

    @Test
    public void testCreateTransaction() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        TransactionDTO result = transactionService.createTransaction(transactionDTO);
        assertNotNull(result);
        assertEquals(transactionDTO.getAmount(), result.getAmount());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void testGetTransactionById() {
        UUID transactionId = UUID.randomUUID();
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transaction));
        TransactionDTO result = transactionService.getTransactionById(transactionId);
        assertNotNull(result);
        assertEquals(transactionDTO.getAmount(), result.getAmount());
        verify(transactionRepository, times(1)).findById(transactionId);
    }

    @Test
    public void testGetTransactionById_NotFound() {
        UUID transactionId = UUID.randomUUID();
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());
        assertThrows(TransactionNotFoundException.class, () -> transactionService.getTransactionById(transactionId));
        verify(transactionRepository, times(1)).findById(transactionId);
    }

    @Test
    public void testAccountWithdraw() {
        when(accountRepository.findByAccountNumber("123456789")).thenReturn(Optional.of(account));
        when(currencyConversionService.convert(CurrencyType.USD, CurrencyType.USD, account.getBalance())).thenReturn(account.getBalance());
        when(currencyConversionService.convert(CurrencyType.USD, CurrencyType.USD, BigDecimal.valueOf(100))).thenReturn(BigDecimal.valueOf(100));
        TransactionDTO result = transactionService.accountWithdraw("123456789", BigDecimal.valueOf(100), CurrencyType.USD);
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(900), account.getBalance());
        verify(accountRepository, times(1)).save(account);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void testCardWithdraw() {
        when(cardRepository.findByCardNumber("987654321")).thenReturn(Optional.of(card));
        TransactionDTO result = transactionService.cardWithdraw("987654321", BigDecimal.valueOf(100), CurrencyType.USD);
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(400), card.getBalance());
        verify(cardRepository, times(1)).save(card);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void testAccountDeposit() {
        when(accountRepository.findByAccountNumber("123456789")).thenReturn(Optional.of(account));
        TransactionDTO result = transactionService.accountDeposit("123456789", BigDecimal.valueOf(100), CurrencyType.USD);
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(1100), account.getBalance());
        verify(accountRepository, times(1)).save(account);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void testCardDeposit() {
        when(cardRepository.findByCardNumber("987654321")).thenReturn(Optional.of(card));
        TransactionDTO result = transactionService.cardDeposit("987654321", BigDecimal.valueOf(100), CurrencyType.USD);
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(600), card.getBalance());
        verify(cardRepository, times(1)).save(card);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    public void testAccountTransfer() {
        Account recipientAccount = new Account();
        recipientAccount.setId(UUID.randomUUID());
        recipientAccount.setAccountNumber("123456788");
        recipientAccount.setBalance(BigDecimal.valueOf(500));
        recipientAccount.setCurrencyType(CurrencyType.USD);

        when(accountRepository.findByAccountNumber("123456789")).thenReturn(Optional.of(account));
        when(accountRepository.findByAccountNumber("123456788")).thenReturn(Optional.of(recipientAccount));
        when(currencyConversionService.convert(CurrencyType.USD, CurrencyType.USD, account.getBalance())).thenReturn(account.getBalance());
        when(currencyConversionService.convert(CurrencyType.USD, CurrencyType.USD, BigDecimal.valueOf(100))).thenReturn(BigDecimal.valueOf(100));

        TransactionDTO result = transactionService.accountTransfer("123456789", "123456788", BigDecimal.valueOf(100), "Transfer", CurrencyType.USD);
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(900), account.getBalance());
        assertEquals(BigDecimal.valueOf(600), recipientAccount.getBalance());
        verify(accountRepository, times(1)).save(account);
        verify(accountRepository, times(1)).save(recipientAccount);
        verify(transactionRepository, times(2)).save(any(Transaction.class));
    }

    @Test
    public void testCardTransfer() {
        Card recipientCard = new Card();
        recipientCard.setId(UUID.randomUUID());
        recipientCard.setCardNumber("987654320");
        recipientCard.setBalance(BigDecimal.valueOf(200));
        recipientCard.setCurrencyType(CurrencyType.USD);

        when(cardRepository.findByCardNumber("987654321")).thenReturn(Optional.of(card));
        when(cardRepository.findByCardNumber("987654320")).thenReturn(Optional.of(recipientCard));
        when(currencyConversionService.convert(CurrencyType.USD, CurrencyType.USD, card.getBalance())).thenReturn(card.getBalance());
        when(currencyConversionService.convert(CurrencyType.USD, CurrencyType.USD, BigDecimal.valueOf(100))).thenReturn(BigDecimal.valueOf(100));

        TransactionDTO result = transactionService.cardTransfer("987654321", "987654320", BigDecimal.valueOf(100), "Transfer", CurrencyType.USD);
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(400), card.getBalance());
        assertEquals(BigDecimal.valueOf(300), recipientCard.getBalance());
        verify(cardRepository, times(1)).save(card);
        verify(cardRepository, times(1)).save(recipientCard);
        verify(transactionRepository, times(2)).save(any(Transaction.class));
    }
}
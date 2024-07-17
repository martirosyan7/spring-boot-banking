package com.banking.springbootbanking.controller;

import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.model.dto.AccountDTO;
import com.banking.springbootbanking.model.dto.CardDTO;
import com.banking.springbootbanking.model.dto.TransactionDTO;
import com.banking.springbootbanking.service.AccountService;
import com.banking.springbootbanking.service.CardService;
import com.banking.springbootbanking.service.TransactionService;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import com.banking.springbootbanking.utils.enums.TransactionDirection;
import com.banking.springbootbanking.utils.enums.TransactionStatus;
import com.banking.springbootbanking.utils.enums.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class TransactionControllerTest {

    @InjectMocks
    private TransactionController controller;

    @Mock
    private TransactionService transactionService;

    @Mock
    private CardService cardService;

    @Mock
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTransaction() {
        BigDecimal amount = BigDecimal.valueOf(100.00);
        String description = "Test transaction";
        String senderNumber = "123456789";
        String recipientNumber = "987654321";
        TransactionType type = TransactionType.TRANSFER;
        TransactionDirection direction = TransactionDirection.INCOMING;
        TransactionStatus status = TransactionStatus.COMPLETED;
        CurrencyType currency = CurrencyType.USD;

        TransactionDTO mockTransactionDto = new TransactionDTO();
        mockTransactionDto.setId(UUID.randomUUID());
        mockTransactionDto.setAmount(amount);
        mockTransactionDto.setDescription(description);
        mockTransactionDto.setSenderNumber(senderNumber);
        mockTransactionDto.setRecipientNumber(recipientNumber);
        mockTransactionDto.setType(type);
        mockTransactionDto.setDirection(direction);
        mockTransactionDto.setStatus(status);
        mockTransactionDto.setCurrency(currency);

        when(transactionService.createTransaction(any(TransactionDTO.class))).thenReturn(mockTransactionDto);

        ResponseEntity<TransactionDTO> response = controller.createTransaction(amount, description, senderNumber,
                recipientNumber, type, direction, status, currency);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockTransactionDto, response.getBody());
    }

    @Test
    public void testGetTransactionById() {
        UUID transactionId = UUID.randomUUID();

        TransactionDTO mockTransactionDto = new TransactionDTO();
        mockTransactionDto.setId(transactionId);
        mockTransactionDto.setAmount(BigDecimal.valueOf(100.00));
        mockTransactionDto.setDescription("Test transaction");

        when(transactionService.getTransactionById(transactionId)).thenReturn(mockTransactionDto);

        ResponseEntity<TransactionDTO> response = controller.getTransactionById(transactionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transactionId, response.getBody().getId());
    }

    @Test
    public void testGetAllTransactions() {
        List<TransactionDTO> mockTransactions = new ArrayList<>();

        when(transactionService.getAllTransactions()).thenReturn(mockTransactions);

        ResponseEntity<List<TransactionDTO>> response = controller.getAllTransactions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockTransactions.size(), response.getBody().size());
    }

    //TODO: Add tests for accountWithdraw, cardWithdraw, accountDeposit, cardDeposit, accountTransfer, cardTransfer

}

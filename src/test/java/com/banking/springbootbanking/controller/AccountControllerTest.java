package com.banking.springbootbanking.controller;

import com.banking.springbootbanking.model.dto.AccountDTO;
import com.banking.springbootbanking.model.dto.BankDTO;
import com.banking.springbootbanking.model.dto.LocalUserDTO;
import com.banking.springbootbanking.model.dto.mapper.BankMapper;
import com.banking.springbootbanking.model.dto.mapper.LocalUserMapper;
import com.banking.springbootbanking.repository.AccountRepository;
import com.banking.springbootbanking.service.AccountService;
import com.banking.springbootbanking.service.BankService;
import com.banking.springbootbanking.service.LocalUserService;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import com.banking.springbootbanking.utils.generator.NumberGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @Mock
    private LocalUserService localUserService;

    @Mock
    private BankService bankService;

    @Mock
    private NumberGenerator numberGenerator;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        when(numberGenerator.getAccountRepository()).thenReturn(accountRepository);
    }

    @Test
    void testCreateAccount() {
        UUID bankId = UUID.randomUUID();
        CurrencyType currencyType = CurrencyType.USD;
        BigDecimal balance = BigDecimal.valueOf(1000.00);
        LocalUserDTO authenticatedUser = new LocalUserDTO();
        authenticatedUser.setId(UUID.randomUUID());

        BankDTO bankDTO = new BankDTO();
        bankDTO.setId(bankId);

        when(numberGenerator.generateAccountNumber()).thenReturn("1234567890");

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountNumber("1234567890");
        accountDTO.setCurrencyType(currencyType);
        accountDTO.setBalance(balance);
        accountDTO.setBank(BankMapper.mapToBank(bankDTO));

        when(bankService.getBankById(bankId)).thenReturn(bankDTO);
        when(accountService.createAccount(any(AccountDTO.class))).thenReturn(accountDTO);

        ResponseEntity<AccountDTO> responseEntity = accountController.createAccount(LocalUserMapper.mapToUser(authenticatedUser), currencyType, balance, bankId);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(accountDTO.getAccountNumber(), responseEntity.getBody().getAccountNumber());
        assertEquals(accountDTO.getCurrencyType(), responseEntity.getBody().getCurrencyType());
        assertEquals(accountDTO.getBalance(), responseEntity.getBody().getBalance());
        assertEquals(accountDTO.getBank(), responseEntity.getBody().getBank());
    }

    @Test
    void testGetAccountById() {
        UUID id = UUID.randomUUID();
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(id);
        when(accountService.getAccountById(id)).thenReturn(accountDTO);

        ResponseEntity<AccountDTO> responseEntity = accountController.getAccountById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(accountDTO, responseEntity.getBody());
    }

    @Test
    void testGetAllAccounts() {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(UUID.randomUUID());
        when(accountService.getAllAccounts()).thenReturn(Collections.singletonList(accountDTO));

        ResponseEntity<List<AccountDTO>> responseEntity = accountController.getAllAccounts();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals(accountDTO, responseEntity.getBody().get(0));
    }
}
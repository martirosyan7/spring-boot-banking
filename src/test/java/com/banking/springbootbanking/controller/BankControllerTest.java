package com.banking.springbootbanking.controller;

import com.banking.springbootbanking.model.dto.BankDTO;
import com.banking.springbootbanking.service.BankService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BankControllerTest {

    @Mock
    private BankService bankService;

    @InjectMocks
    private BankController bankController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateBank() {
        String name = "Example Bank";
        String address = "123 Main Street";
        String issuerNumber = "12345678";
        String accountIdentificationNumber = "98765432";

        BankDTO bankDto = new BankDTO();
        bankDto.setName(name);
        bankDto.setAddress(address);
        bankDto.setIssuerNumber(issuerNumber);
        bankDto.setAccountIdentificationNumber(accountIdentificationNumber);

        when(bankService.createBank(any(BankDTO.class))).thenReturn(bankDto);

        ResponseEntity<BankDTO> responseEntity = bankController.createBank(name, address, issuerNumber, accountIdentificationNumber);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(name, responseEntity.getBody().getName());
        assertEquals(address, responseEntity.getBody().getAddress());
        assertEquals(issuerNumber, responseEntity.getBody().getIssuerNumber());
        assertEquals(accountIdentificationNumber, responseEntity.getBody().getAccountIdentificationNumber());
    }
}

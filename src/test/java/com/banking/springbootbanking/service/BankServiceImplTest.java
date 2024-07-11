package com.banking.springbootbanking.service;

import com.banking.springbootbanking.exception.BankNotFoundException;
import com.banking.springbootbanking.model.Bank;
import com.banking.springbootbanking.model.dto.BankDTO;
import com.banking.springbootbanking.model.dto.mapper.BankMapper;
import com.banking.springbootbanking.repository.BankRepository;
import com.banking.springbootbanking.service.impl.BankServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BankServiceImplTest {

    @Mock
    private BankRepository bankRepository;

    @InjectMocks
    private BankServiceImpl bankService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateBank() {
        // Given
        BankDTO bankDto = new BankDTO();
        bankDto.setName("Test Bank");

        Bank bank = new Bank();
        bank.setName(bankDto.getName());
        bank.setAddress("Test Address");
        bank.setIssuerNumber("12345678");
        bank.setAccountIdentificationNumber("98765432");

        when(bankRepository.save(any(Bank.class))).thenReturn(bank);

        // When
        BankDTO createdBankDto = bankService.createBank(bankDto);

        // Then
        assertNotNull(createdBankDto);
        assertEquals(bankDto.getName(), createdBankDto.getName());
    }

    @Test
    public void testGetBankById() {
        // Given
        UUID id = UUID.randomUUID();
        Bank bank = new Bank();
        bank.setId(id);
        bank.setName("Test Bank");
        bank.setAddress("Test Address");
        bank.setIssuerNumber("12345678");
        bank.setAccountIdentificationNumber("98765432");

        when(bankRepository.findById(id)).thenReturn(Optional.of(bank));

        // When
        BankDTO foundBankDto = bankService.getBankById(id);

        // Then
        assertNotNull(foundBankDto);
        assertEquals(id, foundBankDto.getId());
        assertEquals(bank.getName(), foundBankDto.getName());
    }

    @Test
    public void testGetBankById_BankNotFound() {
        // Given
        UUID id = UUID.randomUUID();

        when(bankRepository.findById(id)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(BankNotFoundException.class, () -> bankService.getBankById(id));
    }

    @Test
    public void testGetAllBanks() {
        // Given
        List<Bank> banks = new ArrayList<>();
        Bank bank1 = new Bank();
        bank1.setId(UUID.randomUUID());
        bank1.setName("Bank 1");
        bank1.setAddress("Address 1");
        bank1.setIssuerNumber("11111111");
        bank1.setAccountIdentificationNumber("11111111");

        Bank bank2 = new Bank();
        bank2.setId(UUID.randomUUID());
        bank2.setName("Bank 2");
        bank2.setAddress("Address 2");
        bank2.setIssuerNumber("22222222");
        bank2.setAccountIdentificationNumber("22222222");

        banks.add(bank1);
        banks.add(bank2);

        when(bankRepository.findAll()).thenReturn(banks);

        // When
        List<BankDTO> bankDtos = bankService.getAllBanks();

        // Then
        assertNotNull(bankDtos);
        assertEquals(2, bankDtos.size());
        assertEquals(banks.get(0).getName(), bankDtos.get(0).getName());
        assertEquals(banks.get(1).getName(), bankDtos.get(1).getName());
    }
}

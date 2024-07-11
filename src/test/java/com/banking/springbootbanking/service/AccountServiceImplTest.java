package com.banking.springbootbanking.service;

import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.model.dto.AccountDTO;
import com.banking.springbootbanking.model.Account;
import com.banking.springbootbanking.repository.AccountRepository;
import com.banking.springbootbanking.repository.LocalUserRepository;
import com.banking.springbootbanking.exception.AccountNotFoundException;
import com.banking.springbootbanking.model.dto.mapper.AccountMapper;
import com.banking.springbootbanking.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private LocalUserRepository userRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateAccount() {
        // Prepare mock data
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(UUID.randomUUID());
        accountDTO.setAccountNumber("1234567890");
        // Mock repository behavior
        Account account = AccountMapper.mapToAccount(accountDTO);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        // Call service method
        AccountDTO createdAccountDTO = accountService.createAccount(accountDTO);

        // Verify
        assertNotNull(createdAccountDTO);
        assertEquals(accountDTO.getId(), createdAccountDTO.getId());
        assertEquals(accountDTO.getAccountNumber(), createdAccountDTO.getAccountNumber());
    }

    @Test
    public void testGetAccountById() {
        // Prepare mock data
        UUID accountId = UUID.randomUUID();
        Account account = new Account();
        account.setId(accountId);
        account.setAccountNumber("1234567890");
        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // Call service method
        AccountDTO retrievedAccountDTO = accountService.getAccountById(accountId);

        // Verify
        assertNotNull(retrievedAccountDTO);
        assertEquals(account.getId(), retrievedAccountDTO.getId());
        assertEquals(account.getAccountNumber(), retrievedAccountDTO.getAccountNumber());
    }

    @Test
    public void testGetAllAccounts() {
        // Prepare mock data
        UUID accountId1 = UUID.randomUUID();
        UUID accountId2 = UUID.randomUUID();

        Account account1 = new Account();
        account1.setId(accountId1);
        account1.setAccountNumber("1234567890");

        Account account2 = new Account();
        account2.setId(accountId2);
        account2.setAccountNumber("0987654321");

        List<Account> accounts = Arrays.asList(account1, account2);

        when(accountRepository.findAll()).thenReturn(accounts);

        // Call service method
        List<AccountDTO> accountDTOs = accountService.getAllAccounts();

        // Verify
        assertNotNull(accountDTOs);
        assertEquals(accounts.size(), accountDTOs.size());
        assertEquals(account1.getId(), accountDTOs.get(0).getId());
        assertEquals(account2.getId(), accountDTOs.get(1).getId());
        assertEquals(account1.getAccountNumber(), accountDTOs.get(0).getAccountNumber());
        assertEquals(account2.getAccountNumber(), accountDTOs.get(1).getAccountNumber());
    }

    @Test
    public void testGetAccountsByUser() {
        // Prepare mock data
        LocalUser user = new LocalUser();
        user.setId(UUID.randomUUID());

        // Create accounts associated with the user
        Account account1 = new Account();
        account1.setId(UUID.randomUUID());
        account1.setAccountNumber("1234567890");
        account1.setLocalUser(user);

        Account account2 = new Account();
        account2.setId(UUID.randomUUID());
        account2.setAccountNumber("0987654321");
        account2.setLocalUser(user);

        List<Account> accounts = Arrays.asList(account1, account2);

        when(accountRepository.findByLocalUser(user)).thenReturn(accounts);

        // Call service method
        List<AccountDTO> accountDTOs = accountService.getAccountsByUser(user);

        // Verify
        assertNotNull(accountDTOs);
        assertEquals(accounts.size(), accountDTOs.size());
        assertEquals(accounts.get(0).getId(), accountDTOs.get(0).getId());
        assertEquals(accounts.get(1).getAccountNumber(), accountDTOs.get(1).getAccountNumber());
    }

    // Add more tests as needed for exception handling, edge cases, etc.
}

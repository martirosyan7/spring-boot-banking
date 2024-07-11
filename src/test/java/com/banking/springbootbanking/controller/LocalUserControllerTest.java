package com.banking.springbootbanking.controller;

import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.model.dto.AccountDTO;
import com.banking.springbootbanking.model.dto.LocalUserDTO;
import com.banking.springbootbanking.service.AccountService;
import com.banking.springbootbanking.service.CardService;
import com.banking.springbootbanking.service.LocalUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class LocalUserControllerTest {

    @InjectMocks
    private LocalUserController controller;

    @Mock
    private LocalUserService localUserService;

    @Mock
    private AccountService accountService;

    @Mock
    private CardService cardService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser() {
        LocalUserDTO mockUserDto = new LocalUserDTO();
        mockUserDto.setUsername("testuser");
        mockUserDto.setFirstName("John");
        mockUserDto.setLastName("Doe");
        mockUserDto.setEmail("john.doe@example.com");
        mockUserDto.setPassword("password");
        mockUserDto.setAddress("123 Main St");

        when(localUserService.createUser(any(LocalUserDTO.class))).thenReturn(mockUserDto);

        ResponseEntity<LocalUserDTO> response = controller.createUser("testuser", "John", "Doe",
                "john.doe@example.com", "password", "123 Main St");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockUserDto, response.getBody());
    }

    @Test
    public void testGetUserById() {
        UUID userId = UUID.randomUUID();
        LocalUserDTO mockUserDto = new LocalUserDTO();
        mockUserDto.setId(userId);
        mockUserDto.setUsername("testuser");

        when(localUserService.getUserById(userId)).thenReturn(mockUserDto);

        ResponseEntity<LocalUserDTO> response = controller.getUserById(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userId, response.getBody().getId());
    }

    @Test
    public void testGetUserAccounts() {
        LocalUser mockUser = new LocalUser();
        mockUser.setId(UUID.randomUUID());
        mockUser.setUsername("testuser");

        List<AccountDTO> mockAccounts = new ArrayList<>();

        when(accountService.getAccountsByUser(any(LocalUser.class))).thenReturn(mockAccounts);

        ResponseEntity<List<AccountDTO>> response = controller.getUserAccounts(mockUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockAccounts.size(), response.getBody().size());
    }
}

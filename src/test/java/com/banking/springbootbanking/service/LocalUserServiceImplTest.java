package com.banking.springbootbanking.service;

import com.banking.springbootbanking.exception.LocalUserNotFoundException;
import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.model.dto.LocalUserDTO;
import com.banking.springbootbanking.model.dto.TransactionDTO;
import com.banking.springbootbanking.model.dto.mapper.LocalUserMapper;
import com.banking.springbootbanking.model.dto.mapper.TransactionMapper;
import com.banking.springbootbanking.model.api.model.LoginBody;
import com.banking.springbootbanking.repository.LocalUserRepository;
import com.banking.springbootbanking.service.encryption.EncryptionService;
import com.banking.springbootbanking.service.impl.LocalUserServiceImpl;
import com.banking.springbootbanking.service.jwt.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LocalUserServiceImplTest {

    @Mock
    private LocalUserRepository localUserRepository;

    @Mock
    private EncryptionService encryptionService;

    @Mock
    private JWTService jwtService;

    @InjectMocks
    private LocalUserServiceImpl localUserService;

    private LocalUser user;
    private LocalUserDTO userDTO;
    private LoginBody loginBody;

    @BeforeEach
    public void setup() {
        user = new LocalUser();
        user.setId(UUID.randomUUID());
        user.setUsername("testuser");
        user.setPassword("encryptedpassword");

        userDTO = new LocalUserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword("plainpassword");

        loginBody = new LoginBody();
        loginBody.setUsername("testuser");
        loginBody.setPassword("plainpassword");
    }

    @Test
    public void testCreateUser() {
        when(encryptionService.encryptPassword(userDTO.getPassword())).thenReturn("encryptedpassword");
        when(localUserRepository.save(any(LocalUser.class))).thenReturn(user);

        LocalUserDTO createdUserDTO = localUserService.createUser(userDTO);

        assertNotNull(createdUserDTO);
        assertEquals("testuser", createdUserDTO.getUsername());
        verify(localUserRepository, times(1)).save(any(LocalUser.class));
    }

    @Test
    public void testGetUserById() {
        when(localUserRepository.findById(user.getId())).thenReturn(Optional.of(user));

        LocalUserDTO foundUserDTO = localUserService.getUserById(user.getId());

        assertNotNull(foundUserDTO);
        assertEquals(user.getUsername(), foundUserDTO.getUsername());
        verify(localUserRepository, times(1)).findById(user.getId());
    }

    @Test
    public void testGetUserById_NotFound() {
        UUID id = UUID.randomUUID();
        when(localUserRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(LocalUserNotFoundException.class, () -> localUserService.getUserById(id));
    }

    @Test
    public void testGetAllUsers() {
        List<LocalUser> users = Arrays.asList(user);
        when(localUserRepository.findAll()).thenReturn(users);

        List<LocalUserDTO> userDTOs = localUserService.getAllUsers();

        assertNotNull(userDTOs);
        assertEquals(1, userDTOs.size());
        verify(localUserRepository, times(1)).findAll();
    }

    @Test
    public void testLoginUser_Success() {
        when(localUserRepository.findByUsername(loginBody.getUsername())).thenReturn(Optional.of(user));
        when(encryptionService.checkPassword(loginBody.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtService.generateJWT(user)).thenReturn("token");

        String token = localUserService.loginUser(loginBody);

        assertNotNull(token);
        assertEquals("token", token);
    }

    @Test
    public void testLoginUser_Failure() {
        when(localUserRepository.findByUsername(loginBody.getUsername())).thenReturn(Optional.of(user));
        when(encryptionService.checkPassword(loginBody.getPassword(), user.getPassword())).thenReturn(false);

        String token = localUserService.loginUser(loginBody);

        assertNull(token);
    }

    @Test
    public void testGetTransactionHistory() {
        when(localUserRepository.findById(user.getId())).thenReturn(Optional.of(user));

        Set<TransactionDTO> transactions = localUserService.getTransactionHistory(user.getId());

        assertNotNull(transactions);
        verify(localUserRepository, times(1)).findById(user.getId());
    }

    @Test
    public void testGetByUsername() {
        when(localUserRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        LocalUser foundUser = localUserService.getByUsername(user.getUsername());

        assertNotNull(foundUser);
        assertEquals(user.getUsername(), foundUser.getUsername());
        verify(localUserRepository, times(1)).findByUsername(user.getUsername());
    }

    @Test
    public void testGetByUsername_NotFound() {
        when(localUserRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        assertThrows(LocalUserNotFoundException.class, () -> localUserService.getByUsername(user.getUsername()));
    }
}
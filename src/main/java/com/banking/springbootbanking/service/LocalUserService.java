package com.banking.springbootbanking.service;

import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.model.dto.LocalUserDTO;
import com.banking.springbootbanking.model.dto.TransactionDTO;
import com.banking.springbootbanking.model.api.model.LoginBody;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface LocalUserService {

    LocalUserDTO createUser(LocalUserDTO userDto);

    LocalUserDTO getUserById(UUID id);

    List<LocalUserDTO> getAllUsers();

    String loginUser(LoginBody loginBody);

    Set<TransactionDTO> getTransactionHistory(UUID id);

    LocalUser getByUsername(String username);
}

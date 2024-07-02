package com.banking.springbootbanking.service;

import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.model.dto.LocalUserDTO;
import com.banking.springbootbanking.model.dto.TransactionDTO;
import com.banking.springbootbanking.model.api.model.LoginBody;

import java.util.List;
import java.util.Set;

public interface LocalUserService {

    LocalUserDTO createUser(LocalUserDTO userDto);

    LocalUserDTO getUserById(Long id);

    List<LocalUserDTO> getAllUsers();

    String loginUser(LoginBody loginBody);

    Set<TransactionDTO> getTransactionHistory(Long id);

    LocalUser getByUsername(String username);
}

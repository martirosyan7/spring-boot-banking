package com.banking.springbootbanking.service;

import com.banking.springbootbanking.dto.LocalUserDTO;
import com.banking.springbootbanking.model.api.model.LoginBody;

import java.util.List;

public interface LocalUserService {

    LocalUserDTO createUser(LocalUserDTO userDto);

    LocalUserDTO getUserById(Long id);

    List<LocalUserDTO> getAllUsers();

    String loginUser(LoginBody loginBody);
}

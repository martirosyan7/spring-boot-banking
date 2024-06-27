package com.banking.springbootbanking.service;

import com.banking.springbootbanking.model.LocalUser;

import java.util.List;

public interface LocalUserService {

    LocalUser createUser(String username, String firstName, String lastName, String email, String password, String address);

    LocalUser getUserById(Long id);

    List<LocalUser> getAllUsers();
}

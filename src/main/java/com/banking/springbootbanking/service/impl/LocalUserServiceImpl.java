package com.banking.springbootbanking.service.impl;

import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.repository.LocalUserRepository;
import com.banking.springbootbanking.service.LocalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalUserServiceImpl implements LocalUserService {

    @Autowired
    private LocalUserRepository localUserRepository;

    @Override
    public LocalUser createUser(String username, String firstName, String lastName, String email, String password, String address) {
        if (localUserRepository.existsByUsername(username)) {
            throw new RuntimeException("User with this username already exists");
        }
        LocalUser user = new LocalUser();
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        //TODO: add password hashing
        user.setAddress(address);

        return localUserRepository.save(user);
    }

    @Override
    public LocalUser getUserById(Long id) {
        return localUserRepository.findById(id).orElse(null);
    }

    @Override
    public List<LocalUser> getAllUsers() {
        return localUserRepository.findAll();
    }
}

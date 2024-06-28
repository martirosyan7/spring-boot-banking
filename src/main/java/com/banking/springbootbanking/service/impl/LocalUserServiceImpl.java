package com.banking.springbootbanking.service.impl;

import com.banking.springbootbanking.dto.LocalUserDTO;
import com.banking.springbootbanking.dto.mapper.LocalUserMapper;
import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.repository.LocalUserRepository;
import com.banking.springbootbanking.service.LocalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalUserServiceImpl implements LocalUserService {

    @Autowired
    private LocalUserRepository localUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public LocalUserDTO createUser(LocalUserDTO userDto) {
        LocalUser user = LocalUserMapper.mapToUser(userDto);
        LocalUser savedUser = localUserRepository.save(user);
        return LocalUserMapper.mapToUserDto(savedUser);
    }

    @Override
    public LocalUserDTO getUserById(Long id) {
        LocalUser user = localUserRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("User does not exist"));
        //TODO: Create a custom exception for this case
        return LocalUserMapper.mapToUserDto(user);
    }

    @Override
    public List<LocalUserDTO> getAllUsers() {
        List<LocalUser> users = localUserRepository.findAll();
        return users.stream()
                .map((user) -> LocalUserMapper.mapToUserDto(user))
                .collect(Collectors.toList());
    }
}

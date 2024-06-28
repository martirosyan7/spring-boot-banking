package com.banking.springbootbanking.service.impl;

import com.banking.springbootbanking.dto.LocalUserDTO;
import com.banking.springbootbanking.dto.mapper.LocalUserMapper;
import com.banking.springbootbanking.exception.LocalUserNotFoundException;
import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.model.api.model.LoginBody;
import com.banking.springbootbanking.repository.LocalUserRepository;
import com.banking.springbootbanking.service.LocalUserService;
import com.banking.springbootbanking.service.encryption.EncryptionService;
import com.banking.springbootbanking.service.jwt.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocalUserServiceImpl implements LocalUserService {

    @Autowired
    private LocalUserRepository localUserRepository;

    @Autowired
    private EncryptionService encryptionService;

    private JWTService jwtService;

    public LocalUserServiceImpl(LocalUserRepository localUserRepository, EncryptionService encryptionService, JWTService jwtService) {
        this.encryptionService = encryptionService;
        this.jwtService = jwtService;
    }

    @Override
    public LocalUserDTO createUser(LocalUserDTO userDto) {
        LocalUser user = LocalUserMapper.mapToUser(userDto);
        user.setPassword(encryptionService.encryptPassword(user.getPassword()));
        LocalUser savedUser = localUserRepository.save(user);
        return LocalUserMapper.mapToUserDto(savedUser);
    }

    @Override
    public LocalUserDTO getUserById(Long id) {
        LocalUser user = localUserRepository
                .findById(id)
                .orElseThrow(() -> new LocalUserNotFoundException("User does not exist"));
        return LocalUserMapper.mapToUserDto(user);
    }

    @Override
    public List<LocalUserDTO> getAllUsers() {
        Iterable<LocalUser> users = localUserRepository.findAll();
        List<LocalUser> userList = new ArrayList<>();
        users.forEach(userList::add);

        return userList.stream()
                .map((user) -> LocalUserMapper.mapToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public String loginUser(LoginBody loginBody) {
        Optional<LocalUser> user = localUserRepository.findByUsername(loginBody.getUsername());
        if (user.isPresent()) {
            LocalUser localUser = user.get();
            if (encryptionService.checkPassword(loginBody.getPassword(), localUser.getPassword())) {
                return jwtService.generateJWT(localUser);
            }
        }
        return null;
    }



}

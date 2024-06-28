package com.banking.springbootbanking.controller;


import com.banking.springbootbanking.dto.LocalUserDTO;
import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.model.api.model.LoginBody;
import com.banking.springbootbanking.model.api.model.LoginResponse;
import com.banking.springbootbanking.service.AccountService;
import com.banking.springbootbanking.service.CardService;
import com.banking.springbootbanking.service.LocalUserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class LocalUserController {

    @Autowired
    private LocalUserService localUserService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CardService cardService;

    @PostMapping("/register")
    public ResponseEntity<LocalUserDTO> createUser(@RequestParam String username, @RequestParam String firstName,
                                                   @RequestParam String lastName, @RequestParam String email,
                                                   @RequestParam String password, @RequestParam String address) {
        LocalUserDTO userDto = new LocalUserDTO();
        userDto.setUsername(username);
        userDto.setFirstName(firstName);
        userDto.setLastName(lastName);
        userDto.setEmail(email);
        userDto.setPassword(password);
        userDto.setAddress(address);

        LocalUserDTO createdUser = localUserService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocalUserDTO> getUserById(@PathVariable Long id) {
        LocalUserDTO userDto = localUserService.getUserById(id);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping
    public ResponseEntity<List<LocalUserDTO>> getAllUsers() {
        List<LocalUserDTO> users = localUserService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginBody loginBody) {
        String token = localUserService.loginUser(loginBody);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(token);
            return ResponseEntity.ok(loginResponse);
        }
    }

    @GetMapping("/me")
    public LocalUser getMe(@AuthenticationPrincipal LocalUser user) {
        return user;
    }
}

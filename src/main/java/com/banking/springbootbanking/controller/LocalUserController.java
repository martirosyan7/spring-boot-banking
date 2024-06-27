package com.banking.springbootbanking.controller;


import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.service.AccountService;
import com.banking.springbootbanking.service.CardService;
import com.banking.springbootbanking.service.LocalUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class LocalUserController {

    @Autowired
    private LocalUserService localUserService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CardService cardService;

    @PostMapping
    public ResponseEntity<LocalUser> createUser(String username, String firstName, String lastName, String email, String password, String address) {
        try {
            LocalUser user = localUserService.createUser(username, firstName, lastName, email, password, address);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

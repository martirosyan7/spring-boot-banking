package com.banking.springbootbanking.controller;

import com.banking.springbootbanking.model.Account;
import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.service.AccountService;
import com.banking.springbootbanking.service.LocalUserService;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
@Slf4j
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private LocalUserService localUserService;

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestParam Long userId, @RequestParam String accountNumber,
                                                 @RequestParam CurrencyType currencyType, @RequestParam Long balance) {
        LocalUser user = localUserService.getUserById(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            Account account = accountService.createAccount(user, accountNumber, currencyType, balance);
            return new ResponseEntity<>(account, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



}

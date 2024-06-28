package com.banking.springbootbanking.controller;

import com.banking.springbootbanking.dto.AccountDTO;
import com.banking.springbootbanking.dto.LocalUserDTO;
import com.banking.springbootbanking.dto.mapper.LocalUserMapper;
import com.banking.springbootbanking.model.Account;
import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.service.AccountService;
import com.banking.springbootbanking.service.LocalUserService;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@Slf4j
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private LocalUserService localUserService;

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestParam Long userId, @RequestParam String accountNumber,
                                                    @RequestParam CurrencyType currencyType, @RequestParam Long balance) {
        LocalUserDTO user = localUserService.getUserById(userId);

        AccountDTO accountDto = new AccountDTO();
        accountDto.setLocalUserId(LocalUserMapper.mapToUser(user));
        accountDto.setAccountNumber(accountNumber);
        accountDto.setCurrencyType(currencyType);
        accountDto.setBalance(balance);

        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable Long id) {
        AccountDTO accountDto = accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }


    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<AccountDTO> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

}

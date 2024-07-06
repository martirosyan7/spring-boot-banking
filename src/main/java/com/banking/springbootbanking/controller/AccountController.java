package com.banking.springbootbanking.controller;

import com.banking.springbootbanking.model.dto.AccountDTO;
import com.banking.springbootbanking.model.dto.LocalUserDTO;
import com.banking.springbootbanking.model.dto.mapper.LocalUserMapper;
import com.banking.springbootbanking.repository.AccountRepository;
import com.banking.springbootbanking.service.AccountService;
import com.banking.springbootbanking.service.LocalUserService;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import com.banking.springbootbanking.utils.generator.NumberGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@Slf4j
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private LocalUserService localUserService;

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestParam UUID userId,
                                                    @RequestParam CurrencyType currencyType,
                                                    @RequestParam BigDecimal balance) {
        LocalUserDTO user = localUserService.getUserById(userId);
        NumberGenerator numberGenerator = new NumberGenerator(LocalUserMapper.mapToUser(user), accountRepository, currencyType);

        AccountDTO accountDto = new AccountDTO();
        accountDto.setLocalUserId(LocalUserMapper.mapToUser(user));
        accountDto.setAccountNumber(numberGenerator.generateAccountNumber());
        accountDto.setCurrencyType(currencyType);
        accountDto.setBalance(balance);

        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable UUID id) {
        AccountDTO accountDto = accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }


    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<AccountDTO> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

}

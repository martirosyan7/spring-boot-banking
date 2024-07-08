package com.banking.springbootbanking.controller;


import com.banking.springbootbanking.model.dto.BankDTO;
import com.banking.springbootbanking.model.dto.LocalUserDTO;
import com.banking.springbootbanking.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/banks")
@Slf4j
public class BankController {

    @Autowired
    private BankService bankService;


    @PostMapping("/create")
    public ResponseEntity<BankDTO> createBank(@RequestParam String name,
                                              @RequestParam String address,
                                              @RequestParam String issuerNumber,
                                              @RequestParam String accountIdentificationNumber) {
        BankDTO bankDto = new BankDTO();
        bankDto.setName(name);
        bankDto.setAddress(address);
        bankDto.setIssuerNumber(issuerNumber);
        bankDto.setAccountIdentificationNumber(accountIdentificationNumber);

        BankDTO createdBank = bankService.createBank(bankDto);
        return new ResponseEntity<>(createdBank, HttpStatus.CREATED);
    }

}

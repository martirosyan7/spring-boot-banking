package com.banking.springbootbanking.service;

import com.banking.springbootbanking.model.dto.BankDTO;

import java.util.List;
import java.util.UUID;

public interface BankService {

    BankDTO createBank(BankDTO bankDto);

    BankDTO getBankById(UUID id);

    List<BankDTO> getAllBanks();

}

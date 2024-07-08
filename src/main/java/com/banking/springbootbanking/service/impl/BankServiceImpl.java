package com.banking.springbootbanking.service.impl;

import com.banking.springbootbanking.exception.BankNotFoundException;
import com.banking.springbootbanking.model.Bank;
import com.banking.springbootbanking.model.dto.BankDTO;
import com.banking.springbootbanking.model.dto.mapper.BankMapper;
import com.banking.springbootbanking.repository.BankRepository;
import com.banking.springbootbanking.service.BankService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BankServiceImpl implements BankService {

    private BankRepository bankRepository;

    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }


    @Override
    public BankDTO createBank(BankDTO bankDto) {
        Bank bank = BankMapper.mapToBank(bankDto);
        Bank savedBank = bankRepository.save(bank);
        return BankMapper.mapToBankDto(savedBank);
    }

    @Override
    public BankDTO getBankById(UUID id) {
        Bank bank = bankRepository
                .findById(id)
                .orElseThrow(() -> new BankNotFoundException("Bank does not exist"));
        return BankMapper.mapToBankDto(bank);
    }

    @Override
    public List<BankDTO> getAllBanks() {
        List<Bank> banks = bankRepository.findAll();
        return banks.stream()
                .map((bank) -> BankMapper.mapToBankDto(bank))
                .collect(Collectors.toList());
    }
}

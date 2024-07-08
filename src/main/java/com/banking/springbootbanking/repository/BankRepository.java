package com.banking.springbootbanking.repository;

import com.banking.springbootbanking.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankRepository extends JpaRepository<Bank, UUID> {
}

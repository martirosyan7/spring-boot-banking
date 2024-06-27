package com.banking.springbootbanking.repository;

import com.banking.springbootbanking.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}

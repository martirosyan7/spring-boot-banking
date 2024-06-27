package com.banking.springbootbanking.repository;

import com.banking.springbootbanking.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

    boolean existsByCardNumber(String cardNumber);
}

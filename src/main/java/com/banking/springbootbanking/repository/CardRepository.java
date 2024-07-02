package com.banking.springbootbanking.repository;

import com.banking.springbootbanking.model.Card;
import com.banking.springbootbanking.model.LocalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {

    boolean existsByCardNumber(String cardNumber);

    Optional<Card> findByCardNumber(String cardNumber);
    List<Card> findByLocalUser(LocalUser localUser);
}

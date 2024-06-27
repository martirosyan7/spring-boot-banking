package com.banking.springbootbanking.controller;


import com.banking.springbootbanking.model.Card;
import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.service.CardService;
import com.banking.springbootbanking.service.LocalUserService;
import com.banking.springbootbanking.utils.enums.CardType;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Set;

@RestController
@RequestMapping("/api/cards")
@Slf4j
public class CardController {
    @Autowired
    private CardService cardService;

    @Autowired
    private LocalUserService localUserService;

    @PostMapping
    public ResponseEntity<Card> createCard(@RequestParam Long userId, @RequestParam String cardNumber,
                                           @RequestParam Long balance, @RequestParam String pinCode,
                                           @RequestParam LocalDate validUntil, @RequestParam String cvv,
                                           @RequestParam CardType type, @RequestParam CurrencyType currencyType) {
        LocalUser user = localUserService.getUserById(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            Set<Card> userCards = user.getCards();
            if (userCards.isEmpty()) {
                log.info("User {} has no cards.", user.getId());
            } else {
                log.info("Cards for user {}: {}", user.getId(), userCards);
            }
            Card card = cardService.createCard(user, cardNumber, balance, pinCode, validUntil, cvv, type, currencyType);
            return new ResponseEntity<>(card, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

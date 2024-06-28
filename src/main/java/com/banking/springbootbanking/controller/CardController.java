package com.banking.springbootbanking.controller;


import com.banking.springbootbanking.dto.CardDTO;
import com.banking.springbootbanking.dto.LocalUserDTO;
import com.banking.springbootbanking.dto.mapper.LocalUserMapper;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
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
    public ResponseEntity<CardDTO> createCard(@RequestParam Long userId,
                                              @RequestParam String cardNumber,
                                              @RequestParam Long balance,
                                              @RequestParam String pinCode,
                                              @RequestParam LocalDate validUntil,
                                              @RequestParam String cvv,
                                              @RequestParam CardType type,
                                              @RequestParam CurrencyType currencyType) {
        LocalUserDTO localUser = localUserService.getUserById(userId);

        CardDTO cardDto = new CardDTO();
        cardDto.setLocalUser(LocalUserMapper.mapToUser(localUser));
        cardDto.setCardNumber(cardNumber);
        cardDto.setBalance(balance);
        cardDto.setPinCode(pinCode);
        cardDto.setValidUntil(validUntil);
        cardDto.setCvv(cvv);
        cardDto.setType(type);
        cardDto.setCurrencyType(currencyType);

        CardDTO createdCard = cardService.createCard(cardDto);
        return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDTO> getCardById(@PathVariable Long id) {
        CardDTO cardDto = cardService.getCardById(id);
        return ResponseEntity.ok(cardDto);
    }

    @GetMapping
    public ResponseEntity<List<CardDTO>> getAllCards() {
        List<CardDTO> cards = cardService.getAllCards();
        return ResponseEntity.ok(cards);
    }
}

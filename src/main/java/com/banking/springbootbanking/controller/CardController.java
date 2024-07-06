package com.banking.springbootbanking.controller;


import com.banking.springbootbanking.model.dto.CardDTO;
import com.banking.springbootbanking.model.dto.LocalUserDTO;
import com.banking.springbootbanking.model.dto.mapper.LocalUserMapper;
import com.banking.springbootbanking.repository.CardRepository;
import com.banking.springbootbanking.service.CardService;
import com.banking.springbootbanking.service.LocalUserService;
import com.banking.springbootbanking.utils.enums.CardType;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import com.banking.springbootbanking.utils.generator.CVVGenerator;
import com.banking.springbootbanking.utils.generator.NumberGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
@Slf4j
public class CardController {
    @Autowired
    private CardService cardService;

    @Autowired
    private LocalUserService localUserService;
    @Autowired
    private CardRepository cardRepository;

    @PostMapping
    public ResponseEntity<CardDTO> createCard(@RequestParam Long userId,
                                              @RequestParam BigDecimal balance,
                                              @RequestParam String pinCode,
                                              @RequestParam CardType type,
                                              @RequestParam CurrencyType currencyType) {
        LocalUserDTO localUser = localUserService.getUserById(userId);
        NumberGenerator numberGenerator = new NumberGenerator(LocalUserMapper.mapToUser(localUser), cardRepository, currencyType);
        LocalDate now = LocalDate.now();
        String cardNumber = numberGenerator.generateCardNumber();
        LocalDate validUntil = now.plusYears(4);

        CardDTO cardDto = new CardDTO();
        cardDto.setLocalUser(LocalUserMapper.mapToUser(localUser));
        cardDto.setCardNumber(cardNumber);
        cardDto.setBalance(balance);
        cardDto.setPinCode(pinCode);
        cardDto.setValidUntil(validUntil);
        cardDto.setCvv(CVVGenerator.generateCVV(cardNumber, validUntil));
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

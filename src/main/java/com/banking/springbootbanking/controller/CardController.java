package com.banking.springbootbanking.controller;


import com.banking.springbootbanking.model.LocalUser;
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
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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

    @PostMapping("/create")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<CardDTO> createCard(@AuthenticationPrincipal LocalUser authenticatedUser,
                                              @RequestParam String pinCode,
                                              @RequestParam CardType type,
                                              @RequestParam CurrencyType currencyType) {
        NumberGenerator numberGenerator = new NumberGenerator(authenticatedUser, cardRepository, currencyType);
        LocalDate now = LocalDate.now();
        String cardNumber = numberGenerator.generateCardNumber();
        LocalDate validUntil = now.plusYears(4);

        CardDTO cardDto = new CardDTO();
        cardDto.setLocalUser(authenticatedUser);
        cardDto.setCardNumber(cardNumber);
        cardDto.setBalance(BigDecimal.valueOf(0));
        cardDto.setPinCode(pinCode);
        cardDto.setValidUntil(validUntil);
        cardDto.setCvv(CVVGenerator.generateCVV(cardNumber, validUntil));
        cardDto.setType(type);
        cardDto.setCurrencyType(currencyType);

        CardDTO createdCard = cardService.createCard(cardDto);
        return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDTO> getCardById(@PathVariable UUID id) {
        CardDTO cardDto = cardService.getCardById(id);
        return ResponseEntity.ok(cardDto);
    }

    @GetMapping
    public ResponseEntity<List<CardDTO>> getAllCards() {
        List<CardDTO> cards = cardService.getAllCards();
        return ResponseEntity.ok(cards);
    }
}

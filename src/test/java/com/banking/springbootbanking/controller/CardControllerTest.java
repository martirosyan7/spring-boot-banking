package com.banking.springbootbanking.controller;

import com.banking.springbootbanking.model.dto.BankDTO;
import com.banking.springbootbanking.model.dto.CardDTO;
import com.banking.springbootbanking.model.dto.LocalUserDTO;
import com.banking.springbootbanking.model.dto.mapper.BankMapper;
import com.banking.springbootbanking.model.dto.mapper.LocalUserMapper;
import com.banking.springbootbanking.repository.AccountRepository;
import com.banking.springbootbanking.repository.CardRepository;
import com.banking.springbootbanking.service.BankService;
import com.banking.springbootbanking.service.CardService;
import com.banking.springbootbanking.utils.enums.CardType;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import com.banking.springbootbanking.utils.generator.CVVGenerator;
import com.banking.springbootbanking.utils.generator.NumberGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CardControllerTest {

    @Mock
    private CardService cardService;

    @Mock
    private BankService bankService;

    @Mock
    private NumberGenerator numberGenerator;

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardController cardController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        when(numberGenerator.getCardRepository()).thenReturn(cardRepository);
    }

    @Test
    void testCreateCard() {
        UUID bankId = UUID.randomUUID();
        CurrencyType currencyType = CurrencyType.USD;
        LocalUserDTO authenticatedUser = new LocalUserDTO();
        authenticatedUser.setId(UUID.randomUUID());

        BankDTO bankDTO = new BankDTO();
        bankDTO.setId(bankId);

        LocalDate now = LocalDate.now();
        String cardNumber = "1234567812345678"; // Replace with a mock generated card number
        LocalDate validUntil = now.plusYears(4);

        CardDTO cardDTO = new CardDTO();
        cardDTO.setLocalUser(LocalUserMapper.mapToUser(authenticatedUser));
        cardDTO.setCardNumber(cardNumber);
        cardDTO.setBalance(BigDecimal.valueOf(0));
        cardDTO.setValidUntil(validUntil);
        cardDTO.setCvv("123"); // Replace with a mock CVV value
        cardDTO.setType(CardType.VISA);
        cardDTO.setCurrencyType(currencyType);
        cardDTO.setBank(BankMapper.mapToBank(bankDTO));

        when(numberGenerator.generateCardNumber()).thenReturn(cardNumber);
        when(bankService.getBankById(bankId)).thenReturn(bankDTO);
        when(cardService.createCard(any(CardDTO.class))).thenReturn(cardDTO);

        ResponseEntity<CardDTO> responseEntity = cardController.createCard(LocalUserMapper.mapToUser(authenticatedUser),
                "1234", CardType.VISA, currencyType, bankId);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(cardDTO.getCardNumber(), responseEntity.getBody().getCardNumber());
        assertEquals(cardDTO.getCurrencyType(), responseEntity.getBody().getCurrencyType());
        assertEquals(cardDTO.getBalance(), responseEntity.getBody().getBalance());
        assertEquals(cardDTO.getBank(), responseEntity.getBody().getBank());
    }

    @Test
    void testGetCardById() {
        UUID id = UUID.randomUUID();
        CardDTO cardDTO = new CardDTO();
        cardDTO.setId(id);
        when(cardService.getCardById(id)).thenReturn(cardDTO);

        ResponseEntity<CardDTO> responseEntity = cardController.getCardById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(cardDTO, responseEntity.getBody());
    }

    @Test
    void testGetAllCards() {
        CardDTO cardDTO = new CardDTO();
        cardDTO.setId(UUID.randomUUID());
        when(cardService.getAllCards()).thenReturn(Collections.singletonList(cardDTO));

        ResponseEntity<List<CardDTO>> responseEntity = cardController.getAllCards();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals(cardDTO, responseEntity.getBody().get(0));
    }
}
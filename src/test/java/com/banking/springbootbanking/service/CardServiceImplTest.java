package com.banking.springbootbanking.service;

import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.model.dto.CardDTO;
import com.banking.springbootbanking.model.dto.mapper.CardMapper;
import com.banking.springbootbanking.exception.CardNotFoundException;
import com.banking.springbootbanking.model.Card;
import com.banking.springbootbanking.repository.CardRepository;
import com.banking.springbootbanking.repository.LocalUserRepository;
import com.banking.springbootbanking.service.encryption.EncryptionService;
import com.banking.springbootbanking.service.impl.CardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardServiceImplTest {

    @Mock
    private LocalUserRepository localUserRepository;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private EncryptionService encryptionService;

    @InjectMocks
    private CardServiceImpl cardService;

    private Card card;
    private CardDTO cardDTO;
    private LocalUser localUser;

    @BeforeEach
    public void setUp() {
        localUser = new LocalUser();
        localUser.setId(UUID.randomUUID());
        localUser.setUsername("testUser");

        card = new Card();
        card.setId(UUID.randomUUID());
        card.setPinCode("1234");
        card.setLocalUser(localUser);

        cardDTO = new CardDTO();
        cardDTO.setPinCode("1234");
        cardDTO.setLocalUser(localUser);
    }

    @Test
    public void testCreateCard() {
        when(encryptionService.encryptPin(anyString())).thenReturn("encryptedPin");
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        CardDTO result = cardService.createCard(cardDTO);

        assertNotNull(result);
        assertEquals(card.getId(), result.getId());
        verify(encryptionService, times(1)).encryptPin(anyString());
        verify(cardRepository, times(1)).save(any(Card.class));
    }

    @Test
    public void testGetCardById() {
        UUID cardId = UUID.randomUUID();
        card.setId(cardId); // Ensure the card has the same ID as the one we're looking for
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(card));

        CardDTO result = cardService.getCardById(cardId);

        assertNotNull(result);
        assertEquals(cardId, result.getId()); // Now both IDs should match
        verify(cardRepository, times(1)).findById(cardId);
    }

    @Test
    public void testGetCardByIdNotFound() {
        UUID cardId = UUID.randomUUID();
        when(cardRepository.findById(cardId)).thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class, () -> {
            cardService.getCardById(cardId);
        });

        verify(cardRepository, times(1)).findById(cardId);
    }

    @Test
    public void testGetAllCards() {
        when(cardRepository.findAll()).thenReturn(Collections.singletonList(card));

        List<CardDTO> result = cardService.getAllCards();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(cardRepository, times(1)).findAll();
    }

    @Test
    public void testGetCardsByUser() {
        when(cardRepository.findByLocalUser(localUser)).thenReturn(Collections.singletonList(card));

        List<CardDTO> result = cardService.getCardsByUser(localUser);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(cardRepository, times(1)).findByLocalUser(localUser);
    }
}
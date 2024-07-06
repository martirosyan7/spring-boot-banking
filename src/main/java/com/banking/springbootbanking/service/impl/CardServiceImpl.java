package com.banking.springbootbanking.service.impl;

import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.model.dto.CardDTO;
import com.banking.springbootbanking.model.dto.mapper.CardMapper;
import com.banking.springbootbanking.exception.CardNotFoundException;
import com.banking.springbootbanking.model.Card;
import com.banking.springbootbanking.repository.CardRepository;
import com.banking.springbootbanking.repository.LocalUserRepository;
import com.banking.springbootbanking.service.CardService;
import com.banking.springbootbanking.service.encryption.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private LocalUserRepository localUserRepository;

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private EncryptionService encryptionService;


    @Override
    public CardDTO createCard(CardDTO cardDto) {
        Card card = CardMapper.mapToCard(cardDto);
        card.setPinCode(encryptionService.encryptPin(card.getPinCode()));
        Card savedCard = cardRepository.save(card);
        return CardMapper.mapToCardDto(savedCard);
    }

    @Override
    public CardDTO getCardById(Long id) {
        Card card = cardRepository
                .findById(id)
                .orElseThrow(() -> new CardNotFoundException("Card does not exist"));
        return CardMapper.mapToCardDto(card);
    }

    @Override
    public List<CardDTO> getAllCards() {
        List<Card> cards = cardRepository.findAll();
        return cards.stream()
                .map((card) -> CardMapper.mapToCardDto(card))
                .collect(Collectors.toList());
    }

    @Override
    public List<CardDTO> getCardsByUser(LocalUser user) {
        List<Card> cards = cardRepository.findByLocalUser(user);
        return cards.stream()
                .map(CardMapper::mapToCardDto)
                .collect(Collectors.toList());
    }

}

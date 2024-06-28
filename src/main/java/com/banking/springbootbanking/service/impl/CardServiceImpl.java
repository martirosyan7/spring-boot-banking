package com.banking.springbootbanking.service.impl;

import com.banking.springbootbanking.dto.CardDTO;
import com.banking.springbootbanking.dto.mapper.CardMapper;
import com.banking.springbootbanking.model.Card;
import com.banking.springbootbanking.repository.CardRepository;
import com.banking.springbootbanking.repository.LocalUserRepository;
import com.banking.springbootbanking.service.CardService;
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


    @Override
    public CardDTO createCard(CardDTO cardDto) {
        Card card = CardMapper.mapToCard(cardDto);
        Card savedCard = cardRepository.save(card);
        return CardMapper.mapToCardDto(savedCard);
    }

    @Override
    public CardDTO getCardById(Long id) {
        Card card = cardRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Card does not exist"));
        //TODO: Create a custom exception for this case
        return CardMapper.mapToCardDto(card);
    }

    @Override
    public List<CardDTO> getAllCards() {
        List<Card> cards = cardRepository.findAll();
        return cards.stream()
                .map((card) -> CardMapper.mapToCardDto(card))
                .collect(Collectors.toList());
    }
}

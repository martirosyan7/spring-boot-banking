package com.banking.springbootbanking.service;

import com.banking.springbootbanking.model.dto.CardDTO;

import java.util.List;

public interface CardService {
    CardDTO createCard(CardDTO cardDto);

    CardDTO getCardById(Long id);

    List<CardDTO> getAllCards();
}

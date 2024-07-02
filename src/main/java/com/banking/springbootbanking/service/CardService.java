package com.banking.springbootbanking.service;

import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.model.dto.CardDTO;

import java.util.List;
import java.util.ListResourceBundle;

public interface CardService {
    CardDTO createCard(CardDTO cardDto);

    CardDTO getCardById(Long id);

    List<CardDTO> getAllCards();

    List<CardDTO> getCardsByUser(LocalUser user);
}

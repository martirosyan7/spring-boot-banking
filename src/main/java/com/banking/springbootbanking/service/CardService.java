package com.banking.springbootbanking.service;

import com.banking.springbootbanking.model.Card;
import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.utils.enums.CardType;
import com.banking.springbootbanking.utils.enums.CurrencyType;

import java.time.LocalDate;
import java.util.List;

public interface CardService {
    Card createCard(LocalUser user, String cardNumber, Long balance, String pinCode, LocalDate validUntil, String cvv, CardType type, CurrencyType currencyType);

    Card getCardById(Long id);

    List<Card> getAllCards();
}

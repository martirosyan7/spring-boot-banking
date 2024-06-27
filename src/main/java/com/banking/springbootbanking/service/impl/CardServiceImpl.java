package com.banking.springbootbanking.service.impl;

import com.banking.springbootbanking.model.Card;
import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.repository.CardRepository;
import com.banking.springbootbanking.repository.LocalUserRepository;
import com.banking.springbootbanking.service.CardService;
import com.banking.springbootbanking.utils.enums.CardType;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private LocalUserRepository localUserRepository;

    @Autowired
    private CardRepository cardRepository;


    @Override
    public Card createCard(LocalUser user, String cardNumber, Long balance, String pinCode, LocalDate validUntil, String cvv, CardType type, CurrencyType currencyType) {
        if (cardRepository.existsByCardNumber(cardNumber)) {
            throw new RuntimeException("Card with this card number already exists");
        }

        Card card = new Card();
        card.setLocalUser(user);
        card.setCardNumber(cardNumber);
        card.setBalance(balance);
        card.setPinCode(pinCode);
        card.setValidUntil(validUntil);
        card.setCvv(cvv);
        card.setType(type);
        card.setCurrencyType(currencyType);

        return cardRepository.save(card);
    }

    @Override
    public Card getCardById(Long id) {
        return cardRepository.findById(id).orElse(null);
    }

    @Override
    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }
}

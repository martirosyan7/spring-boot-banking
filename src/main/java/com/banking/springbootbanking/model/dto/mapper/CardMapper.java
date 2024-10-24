package com.banking.springbootbanking.model.dto.mapper;

import com.banking.springbootbanking.model.dto.CardDTO;
import com.banking.springbootbanking.model.Card;

public class CardMapper {
    public static Card mapToCard(CardDTO cardDto) {
        Card card = new Card(
                cardDto.getId(),
                cardDto.getCardNumber(),
                cardDto.getBalance(),
                cardDto.getPinCode(),
                cardDto.getValidUntil(),
                cardDto.getCvv(),
                cardDto.getLocalUser(),
                cardDto.getType(),
                cardDto.getCurrencyType(),
                cardDto.getBank()
        );
        return card;
    }

    public static CardDTO mapToCardDto(Card card) {
        CardDTO cardDto = new CardDTO(
                card.getId(),
                card.getCardNumber(),
                card.getBalance(),
                card.getPinCode(),
                card.getValidUntil(),
                card.getCvv(),
                card.getLocalUser(),
                card.getType(),
                card.getCurrencyType(),
                card.getBank()
        );
        return cardDto;
    }
}

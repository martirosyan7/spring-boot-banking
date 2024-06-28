package com.banking.springbootbanking.dto;

import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.utils.enums.CardType;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import lombok.*;

import java.time.LocalDate;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardDTO {

    private Long id;
    private String cardNumber;
    private Long balance;
    private String pinCode;
    private LocalDate validUntil;
    private String cvv;
    private LocalUser localUser;
    private CardType type;
    private CurrencyType currencyType;

}

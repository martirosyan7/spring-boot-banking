package com.banking.springbootbanking.model.dto;

import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.utils.enums.CardType;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardDTO implements Serializable {

    private UUID id;
    private String cardNumber;
    private BigDecimal balance;
    private String pinCode;
    private LocalDate validUntil;
    private String cvv;
    private LocalUser localUser;
    private CardType type;
    private CurrencyType currencyType;

}

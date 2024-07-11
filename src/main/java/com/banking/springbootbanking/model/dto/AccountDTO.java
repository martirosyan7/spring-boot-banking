package com.banking.springbootbanking.model.dto;


import com.banking.springbootbanking.model.Bank;
import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountDTO implements Serializable {
    private UUID id;
    private BigDecimal balance;
    private String accountNumber;
    private LocalUser localUser;
    private CurrencyType currencyType;
    private Bank bank;
}

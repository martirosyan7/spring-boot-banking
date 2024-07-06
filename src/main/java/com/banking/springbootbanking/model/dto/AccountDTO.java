package com.banking.springbootbanking.model.dto;


import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountDTO implements Serializable {

    private Long id;
    private BigDecimal balance;
    private String accountNumber;
    private LocalUser localUserId;
    private CurrencyType currencyType;
}

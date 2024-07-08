package com.banking.springbootbanking.model.dto;

import com.banking.springbootbanking.model.Account;
import com.banking.springbootbanking.model.Card;
import lombok.*;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BankDTO implements Serializable {
    private UUID id;
    private String name;
    private String address;
    private String issuerNumber;
    private String accountIdentificationNumber;
    private Set<Card> cards;
    private Set<Account> accounts;
}

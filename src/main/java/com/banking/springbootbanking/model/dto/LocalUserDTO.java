package com.banking.springbootbanking.model.dto;


import com.banking.springbootbanking.model.Account;
import com.banking.springbootbanking.model.Card;
import com.banking.springbootbanking.model.Transaction;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LocalUserDTO implements Serializable {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private Set<Card> cards;
    private Set<Account> accounts;
    private Set<Transaction> transactions;


}

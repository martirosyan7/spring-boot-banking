package com.banking.springbootbanking.dto;


import com.banking.springbootbanking.model.Account;
import com.banking.springbootbanking.model.Card;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LocalUserDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private Set<Card> cards;
    private Set<Account> accounts;


}

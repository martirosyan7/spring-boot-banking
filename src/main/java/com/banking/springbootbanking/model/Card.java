package com.banking.springbootbanking.model;

import com.banking.springbootbanking.utils.enums.CardType;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "card_number", nullable = false, unique = true, length = 16)
    private String cardNumber;

    @Column(name = "balance", nullable = false)
    private Float balance;

    @Column(name = "pin_code", nullable = false, length = 4)
    private String pinCode;

    @Column(name = "valid_until", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate validUntil;

    @Column(name = "cvv", nullable = false, length = 3)
    private String cvv;

    @ManyToOne(optional = false)
    @JoinColumn(name = "local_user_id", nullable = false)
    @JsonManagedReference
    private LocalUser localUser;

    @Column(name = "type", nullable = false)
    private CardType type;

    @Column(name = "currency_type", nullable = false)
    private CurrencyType currencyType;

}
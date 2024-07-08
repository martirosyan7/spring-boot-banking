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
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "card_number", nullable = false, unique = true, length = 16)
    private String cardNumber;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "pin_code", nullable = false)
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "bank_id", nullable = false)
    @JsonManagedReference
    private Bank bank;

}
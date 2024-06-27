package com.banking.springbootbanking.model;

import com.banking.springbootbanking.utils.enums.CurrencyType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "balance", nullable = false)
    private Long balance;

    @Column(name = "account_number", nullable = false, unique = true, length = 16)
    private String accountNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "local_user_id", nullable = false)
    private LocalUser localUser;

    @Column(name = "currency_type", nullable = false)
    private CurrencyType currencyType;

}
package com.banking.springbootbanking.model;

import com.banking.springbootbanking.utils.enums.CurrencyType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "account_number", nullable = false, unique = true, length = 16)
    private String accountNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "local_user_id", nullable = false)
    @JsonIgnore
    private LocalUser localUser;

    @Column(name = "currency_type", nullable = false)
    private CurrencyType currencyType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "bank_id", nullable = false)
    @JsonManagedReference
    private Bank bank;
}

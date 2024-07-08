package com.banking.springbootbanking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bank")
public class Bank {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "issuer_number", nullable = false, unique = true)
    private String issuerNumber;

    @Column(name = "account_identification_number", nullable = false, unique = true)
    private String accountIdentificationNumber;

    @OneToMany(mappedBy = "bank", orphanRemoval = true)
    @JsonBackReference
    private Set<Card> cards = new LinkedHashSet<>();

    @OneToMany(mappedBy = "bank", orphanRemoval = true)
    @JsonBackReference
    private Set<Account> accounts = new LinkedHashSet<>();

}
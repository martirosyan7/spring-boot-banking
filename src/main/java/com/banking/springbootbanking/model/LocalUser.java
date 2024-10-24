package com.banking.springbootbanking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "local_user")
public class LocalUser {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(mappedBy = "localUser", orphanRemoval = true)
    @JsonBackReference
    private Set<Card> cards = new LinkedHashSet<>();

    @OneToMany(mappedBy = "localUser", orphanRemoval = true)
    @JsonBackReference
    private Set<Account> accounts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "localUser", orphanRemoval = true)
    @JsonBackReference
    private Set<Transaction> transactions = new LinkedHashSet<>();

}
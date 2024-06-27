package com.banking.springbootbanking.model;

import com.banking.springbootbanking.utils.enums.CurrencyType;
import com.banking.springbootbanking.utils.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.banking.springbootbanking.utils.enums.TransactionStatus;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "time", nullable = false)
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime time;

    @Column(name = "description")
    private String description;

    @Column(name = "sender_number", nullable = false)
    private String senderNumber;

    @Column(name = "recipient_number", nullable = false)
    private String recipientNumber;

    @Column(name = "type", nullable = false)
    private TransactionType type;

    @Column(name = "status", nullable = false)
    private TransactionStatus status;

    @Column(name = "currency", nullable = false)
    private CurrencyType currency;

}
package com.banking.springbootbanking.model;

import com.banking.springbootbanking.utils.enums.CurrencyType;
import com.banking.springbootbanking.utils.enums.TransactionDirection;
import com.banking.springbootbanking.utils.enums.TransactionStatus;
import com.banking.springbootbanking.utils.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

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

    @Column(name = "direction", nullable = false)
    private TransactionDirection direction;

    @Column(name = "status", nullable = false)
    private TransactionStatus status;

    @Column(name = "currency", nullable = false)
    private CurrencyType currency;

    @ManyToOne
    @JoinColumn(name = "local_user_id")
    @JsonIgnore
    private LocalUser localUser;

}
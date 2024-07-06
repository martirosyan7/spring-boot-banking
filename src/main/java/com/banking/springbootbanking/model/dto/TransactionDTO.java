package com.banking.springbootbanking.model.dto;

import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import com.banking.springbootbanking.utils.enums.TransactionStatus;
import com.banking.springbootbanking.utils.enums.TransactionType;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionDTO implements Serializable {

    private UUID id;
    private BigDecimal amount;
    private LocalUser localUser;
    private LocalDateTime time;
    private String description;
    private String senderNumber;
    private String recipientNumber;
    private TransactionType type;
    private TransactionStatus status;
    private CurrencyType currency;

}

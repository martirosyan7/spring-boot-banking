package com.banking.springbootbanking.dto;

import com.banking.springbootbanking.utils.enums.CurrencyType;
import com.banking.springbootbanking.utils.enums.TransactionStatus;
import com.banking.springbootbanking.utils.enums.TransactionType;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionDTO {

    private Long id;
    private Long amount;
    private LocalDateTime time;
    private String description;
    private String senderNumber;
    private String recipientNumber;
    private TransactionType type;
    private TransactionStatus status;
    private CurrencyType currency;

}

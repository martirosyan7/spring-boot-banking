package com.banking.springbootbanking.dto.mapper;

import com.banking.springbootbanking.dto.TransactionDTO;
import com.banking.springbootbanking.model.Transaction;

public class TransactionMapper {
    public static Transaction mapToTransaction(TransactionDTO transactionDto) {
        Transaction transaction = new Transaction(
                transactionDto.getId(),
                transactionDto.getAmount(),
                transactionDto.getTime(),
                transactionDto.getDescription(),
                transactionDto.getSenderNumber(),
                transactionDto.getRecipientNumber(),
                transactionDto.getType(),
                transactionDto.getStatus(),
                transactionDto.getCurrency()
        );
        return transaction;
    }

    public static TransactionDTO mapToTransactionDto(Transaction transaction) {
        TransactionDTO transactionDto = new TransactionDTO(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getTime(),
                transaction.getDescription(),
                transaction.getSenderNumber(),
                transaction.getRecipientNumber(),
                transaction.getType(),
                transaction.getStatus(),
                transaction.getCurrency()
        );
        return transactionDto;
    }
}

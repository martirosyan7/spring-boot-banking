package com.banking.springbootbanking.model.dto.mapper;

import com.banking.springbootbanking.model.dto.TransactionDTO;
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
                transactionDto.getDirection(),
                transactionDto.getStatus(),
                transactionDto.getCurrency(),
                transactionDto.getLocalUser()
        );
        return transaction;
    }

    public static TransactionDTO mapToTransactionDto(Transaction transaction) {
        TransactionDTO transactionDto = new TransactionDTO(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getLocalUser(),
                transaction.getTime(),
                transaction.getDescription(),
                transaction.getSenderNumber(),
                transaction.getRecipientNumber(),
                transaction.getType(),
                transaction.getDirection(),
                transaction.getStatus(),
                transaction.getCurrency()
        );
        return transactionDto;
    }
}

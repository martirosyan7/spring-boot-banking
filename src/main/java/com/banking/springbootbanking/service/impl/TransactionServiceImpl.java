package com.banking.springbootbanking.service.impl;

import com.banking.springbootbanking.dto.TransactionDTO;
import com.banking.springbootbanking.dto.mapper.TransactionMapper;
import com.banking.springbootbanking.exception.TransactionNotFoundException;
import com.banking.springbootbanking.model.Transaction;
import com.banking.springbootbanking.repository.TransactionRepository;
import com.banking.springbootbanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = TransactionMapper.mapToTransaction(transactionDTO);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return TransactionMapper.mapToTransactionDto(savedTransaction);
    }

    @Override
    public TransactionDTO getTransactionById(Long id) {
        Transaction transaction = transactionRepository
                .findById(id)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction does not exist"));
        return TransactionMapper.mapToTransactionDto(transaction);
    }

    @Override
    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map((transaction) -> TransactionMapper.mapToTransactionDto(transaction))
                .collect(Collectors.toList());
    }
}

package com.banking.springbootbanking.service.scheduler;

import com.banking.springbootbanking.model.Transaction;
import com.banking.springbootbanking.repository.TransactionRepository;
import com.banking.springbootbanking.utils.enums.TransactionDirection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class TransactionValidationService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Scheduled(cron = "0 * * * * ?")
    public void checkTransactionBalances() {
        List<Transaction> transactions = transactionRepository.findAll();

        BigDecimal sentAmount = transactions.stream()
                .filter(t -> t.getDirection() == TransactionDirection.SEND)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal receivedAmount = transactions.stream()
                .filter(t -> t.getDirection() == TransactionDirection.RECEIVE)
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalAmount = sentAmount.subtract(receivedAmount);

        if (totalAmount.compareTo(BigDecimal.ZERO) != 0) {
            log.error("Transaction imbalance detected! Total amount: " + totalAmount);
        } else {
            log.info("All transactions are balanced. Total amount: " + totalAmount);
        }
    }
}


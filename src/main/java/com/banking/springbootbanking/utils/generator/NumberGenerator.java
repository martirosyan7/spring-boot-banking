package com.banking.springbootbanking.utils.generator;

import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.repository.AccountRepository;
import com.banking.springbootbanking.repository.CardRepository;
import com.banking.springbootbanking.utils.enums.CurrencyType;

import java.util.Random;

public class NumberGenerator {
    private LocalUser localUser;
    private CardRepository cardRepository;
    private AccountRepository accountRepository;
    private final String issuerIdentificationNumber = "440806";
    private final String accountIdentificationNumber = "1570";
    private CurrencyType currencyType;
    private Random random = new Random();

    public NumberGenerator(LocalUser localUser, CardRepository cardRepository, CurrencyType currencyType) {
        this.localUser = localUser;
        this.cardRepository = cardRepository;
        this.currencyType = currencyType;
    }

    public NumberGenerator(LocalUser localUser, AccountRepository accountRepository, CurrencyType currencyType) {
        this.localUser = localUser;
        this.accountRepository = accountRepository;
        this.currencyType = currencyType;
    }

    private String generateRandomMiddleDigits() {
        return String.format("%09d", random.nextInt(1000000000));
    }

    public String generateCardNumber() {
        String cardNumber;
        do {
            Long userId = localUser.getId();
            String middleDigits = generateRandomMiddleDigits();
            int checkDigit = calculateCheckDigit(issuerIdentificationNumber, middleDigits);

            cardNumber = String.format("%s%s%d", issuerIdentificationNumber, middleDigits, checkDigit);
        } while (cardRepository.existsByCardNumber(cardNumber));

        return cardNumber;
    }

    public String generateAccountNumber() {
        String accountNumber;
        do {
            Long userId = localUser.getId();
            String middleDigits = generateRandomMiddleDigits();
            int currencyDigits = currencyType.getCurrencyDigits();


            accountNumber = String.format("%s%s%d", accountIdentificationNumber, middleDigits, currencyDigits);
        } while (accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }

    private int calculateCheckDigit(String issuerId, String middleDigits) {
        String cardNumber = issuerId + middleDigits;
        int sum = 0;
        boolean isOdd = true;

        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = cardNumber.charAt(i) - '0';

            if (isOdd) {
                sum += digit;
            } else {
                sum += digit * 2;
                if (digit >= 5) {
                    sum -= 9;
                }
            }

            isOdd = !isOdd;
        }

        return (sum * 9) % 10;
    }
}

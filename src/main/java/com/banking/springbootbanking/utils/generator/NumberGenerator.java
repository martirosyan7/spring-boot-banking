package com.banking.springbootbanking.utils.generator;

import com.banking.springbootbanking.model.Bank;
import com.banking.springbootbanking.model.LocalUser;
import com.banking.springbootbanking.repository.AccountRepository;
import com.banking.springbootbanking.repository.CardRepository;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;
import java.util.UUID;

@Setter
@Getter
public class NumberGenerator {
    private LocalUser localUser;
    private CardRepository cardRepository;
    private AccountRepository accountRepository;
    private Bank bank;
    private CurrencyType currencyType;
    private Random random = new Random();

    public NumberGenerator(LocalUser localUser, CardRepository cardRepository, Bank bank, CurrencyType currencyType) {
        this.localUser = localUser;
        this.cardRepository = cardRepository;
        this.bank = bank;
        this.currencyType = currencyType;
    }

    public NumberGenerator(LocalUser localUser, AccountRepository accountRepository, Bank bank, CurrencyType currencyType) {
        this.localUser = localUser;
        this.accountRepository = accountRepository;
        this.bank = bank;
        this.currencyType = currencyType;
    }

    private String generateRandomMiddleDigits() {
        return String.format("%09d", random.nextInt(1000000000));
    }

    public String generateCardNumber() {
        String cardNumber;
        do {
            UUID userId = localUser.getId();
            String middleDigits = generateRandomMiddleDigits();
            int checkDigit = calculateCheckDigit(bank.getIssuerNumber(), middleDigits);

            cardNumber = String.format("%s%s%d", bank.getIssuerNumber(), middleDigits, checkDigit);
        } while (cardRepository.existsByCardNumber(cardNumber));

        return cardNumber;
    }

    public String generateAccountNumber() {
        String accountNumber;
        do {
            UUID userId = localUser.getId();
            String middleDigits = generateRandomMiddleDigits();
            int currencyDigits = currencyType.getCurrencyDigits();


            accountNumber = String.format("%s%s%d", bank.getAccountIdentificationNumber(), middleDigits, currencyDigits);
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

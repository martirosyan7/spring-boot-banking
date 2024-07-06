package com.banking.springbootbanking.service.currency;

import com.banking.springbootbanking.utils.enums.CurrencyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Map;


@Service
public class CurrencyConversionService {

    @Autowired
    private ExchangeRateService exchangeRateService;

    public BigDecimal convert(CurrencyType fromCurrency, CurrencyType toCurrency, BigDecimal amount) {
        Map<String, BigDecimal> rates;
        try {
            rates = exchangeRateService.getExchangeRates();
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Failed to get exchange rates: " + e.getMessage());
        }

        if (rates.containsKey(fromCurrency.toString()) && rates.containsKey(toCurrency.toString())) {
            BigDecimal fromRate = rates.get(fromCurrency.toString());
            BigDecimal toRate = rates.get(toCurrency.toString());
            return amount.divide(fromRate, MathContext.DECIMAL128).multiply(toRate).setScale(2, RoundingMode.HALF_UP);
        } else {
            throw new IllegalArgumentException("Invalid currency code: " + fromCurrency + " or " + toCurrency);
        }
    }
}

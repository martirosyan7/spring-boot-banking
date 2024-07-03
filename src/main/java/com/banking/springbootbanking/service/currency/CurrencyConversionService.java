package com.banking.springbootbanking.service.currency;

import com.banking.springbootbanking.utils.enums.CurrencyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class CurrencyConversionService {

    @Autowired
    private ExchangeRateService exchangeRateService;

    public Float convert(CurrencyType fromCurrency, CurrencyType toCurrency, Float amount) {
        Map<String, Float> rates;
        try {
            rates = exchangeRateService.getExchangeRates();
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("Failed to get exchange rates: " + e.getMessage());
        }

        if (rates.containsKey(fromCurrency.toString()) && rates.containsKey(toCurrency.toString())) {
            Float fromRate = rates.get(fromCurrency.toString());
            Float toRate = rates.get(toCurrency.toString());
            return amount / fromRate * toRate;
        } else {
            throw new IllegalArgumentException("Invalid currency code: " + fromCurrency + " or " + toCurrency);
        }
    }
}

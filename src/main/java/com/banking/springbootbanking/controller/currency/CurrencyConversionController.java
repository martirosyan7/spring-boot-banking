package com.banking.springbootbanking.controller.currency;


import com.banking.springbootbanking.service.currency.CurrencyConversionService;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/currency")
public class CurrencyConversionController {

    @Autowired
    private CurrencyConversionService currencyConversionService;

    @GetMapping("/convert")
    public BigDecimal convertCurrency(
            @RequestParam CurrencyType fromCurrency,
            @RequestParam CurrencyType toCurrency,
            @RequestParam BigDecimal amount) {
        try {
            return currencyConversionService.convert(fromCurrency, toCurrency, amount);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}


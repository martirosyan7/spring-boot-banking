package com.banking.springbootbanking.controller.currency;


import com.banking.springbootbanking.service.currency.CurrencyConversionService;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/currency")
public class CurrencyConversionController {

    @Autowired
    private CurrencyConversionService currencyConversionService;

    @GetMapping("/convert")
    public double convertCurrency(
            @RequestParam CurrencyType fromCurrency,
            @RequestParam CurrencyType toCurrency,
            @RequestParam Float amount) {
        try {
            return currencyConversionService.convert(fromCurrency, toCurrency, amount);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}


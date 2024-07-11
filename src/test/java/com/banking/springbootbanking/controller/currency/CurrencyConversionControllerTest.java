package com.banking.springbootbanking.controller.currency;

import com.banking.springbootbanking.service.currency.CurrencyConversionService;
import com.banking.springbootbanking.utils.enums.CurrencyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CurrencyConversionControllerTest {

    @Mock
    private CurrencyConversionService currencyConversionService;

    @InjectMocks
    private CurrencyConversionController currencyConversionController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConvertCurrency_Success() {
        CurrencyType fromCurrency = CurrencyType.USD;
        CurrencyType toCurrency = CurrencyType.EUR;
        BigDecimal amount = BigDecimal.valueOf(100);
        BigDecimal expectedConvertedAmount = BigDecimal.valueOf(85.5);

        when(currencyConversionService.convert(fromCurrency, toCurrency, amount))
                .thenReturn(expectedConvertedAmount);

        BigDecimal result = currencyConversionController.convertCurrency(fromCurrency, toCurrency, amount);

        assertEquals(expectedConvertedAmount, result);
        verify(currencyConversionService, times(1)).convert(fromCurrency, toCurrency, amount);
    }

    @Test
    public void testConvertCurrency_InvalidArguments() {
        CurrencyType fromCurrency = CurrencyType.USD;
        CurrencyType toCurrency = CurrencyType.EUR;
        BigDecimal amount = BigDecimal.valueOf(100);

        when(currencyConversionService.convert(any(), any(), any()))
                .thenThrow(new IllegalArgumentException("Invalid currency conversion"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            currencyConversionController.convertCurrency(fromCurrency, toCurrency, amount);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Invalid currency conversion", exception.getReason());

        verify(currencyConversionService, times(1)).convert(fromCurrency, toCurrency, amount);
    }
}

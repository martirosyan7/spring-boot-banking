package com.banking.springbootbanking.service.currency;

import com.banking.springbootbanking.utils.enums.CurrencyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CurrencyConversionServiceTest {

    @Mock
    private ExchangeRateService exchangeRateService;

    @InjectMocks
    private CurrencyConversionService currencyConversionService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConvert() {
        Map<String, BigDecimal> mockRates = new HashMap<>();
        mockRates.put(CurrencyType.USD.toString(), BigDecimal.valueOf(1.0));
        mockRates.put(CurrencyType.EUR.toString(), BigDecimal.valueOf(0.85));
        mockRates.put(CurrencyType.GBP.toString(), BigDecimal.valueOf(0.72));

        when(exchangeRateService.getExchangeRates()).thenReturn(mockRates);

        BigDecimal amountUSD = BigDecimal.valueOf(100.0);
        BigDecimal expectedAmountEUR = BigDecimal.valueOf(85.0).setScale(2, RoundingMode.HALF_UP);

        BigDecimal convertedAmountEUR = currencyConversionService.convert(CurrencyType.USD, CurrencyType.EUR, amountUSD);

        assertEquals(expectedAmountEUR, convertedAmountEUR);
    }

    @Test
    public void testInvalidCurrency() {
        Map<String, BigDecimal> mockRates = new HashMap<>();

        when(exchangeRateService.getExchangeRates()).thenReturn(mockRates);

        BigDecimal amountUSD = BigDecimal.valueOf(100.0);

        assertThrows(IllegalArgumentException.class, () ->
                currencyConversionService.convert(CurrencyType.USD, CurrencyType.EUR, amountUSD));
    }
}
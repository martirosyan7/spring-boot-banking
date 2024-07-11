package com.banking.springbootbanking.service.currency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ExchangeRateServiceTest {

    private ExchangeRateService exchangeRateService;

    @Mock
    private RestTemplate restTemplateMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        exchangeRateService = new ExchangeRateService(restTemplateMock);
    }

    @Test
    public void testGetExchangeRates_Success() {
        Map<String, Object> mockResponse = new HashMap<>();
        Map<String, Object> mockRates = new HashMap<>();
        mockRates.put("USD", 1.23);
        mockRates.put("EUR", 0.89);
        mockResponse.put("rates", mockRates);

        when(restTemplateMock.getForObject(any(String.class), any(Class.class)))
                .thenReturn(mockResponse);

        Map<String, BigDecimal> exchangeRates = exchangeRateService.getExchangeRates();

        assertEquals(2, exchangeRates.size());
        assertEquals(new BigDecimal("1.23"), exchangeRates.get("USD"));
        assertEquals(new BigDecimal("0.89"), exchangeRates.get("EUR"));
    }

    @Test
    public void testGetExchangeRates_MissingRates() {
        Map<String, Object> mockResponse = new HashMap<>();
        mockResponse.put("error", "Invalid API response");

        when(restTemplateMock.getForObject(any(String.class), any(Class.class)))
                .thenReturn(mockResponse);

        try {
            exchangeRateService.getExchangeRates();
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().startsWith("An error occurred: Invalid API response: missing rates"));
        }
    }

    @Test
    public void testGetExchangeRates_ApiError() {
        when(restTemplateMock.getForObject(any(String.class), any(Class.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad request"));

        try {
            exchangeRateService.getExchangeRates();
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("API request error: 400 Bad request"));
        }
    }

    @Test
    public void testGetExchangeRates_OtherError() {
        when(restTemplateMock.getForObject(any(String.class), any(Class.class)))
                .thenThrow(new RuntimeException("Unexpected error"));

        try {
            exchangeRateService.getExchangeRates();
        } catch (RuntimeException e) {
            assertEquals("An error occurred: Unexpected error", e.getMessage());
        }
    }
}

package com.banking.springbootbanking.service.currency;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExchangeRateService {

    @Value("${exchange.rate.api.url}")
    private String apiUrl;

    @Value("${exchange.rate.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public ExchangeRateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Float> getExchangeRates() {
        String url = String.format("%s/latest?access_key=%s", apiUrl, apiKey);
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null && response.containsKey("rates")) {
                Map<String, Float> rates = new HashMap<>();
                Map<String, Object> rawRates = (Map<String, Object>) response.get("rates");
                for (Map.Entry<String, Object> entry : rawRates.entrySet()) {
                    if (entry.getValue() instanceof Number) {
                        rates.put(entry.getKey(), ((Number) entry.getValue()).floatValue());
                    }
                }
                return rates;
            } else {
                throw new RuntimeException("Invalid API response: missing rates");
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw new RuntimeException("API request error: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("An error occurred: " + e.getMessage());
        }
    }
}

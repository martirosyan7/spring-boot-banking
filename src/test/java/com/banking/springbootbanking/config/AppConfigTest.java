package com.banking.springbootbanking.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AppConfigTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testRestTemplateBean() {
        assertNotNull(restTemplate);
    }
}

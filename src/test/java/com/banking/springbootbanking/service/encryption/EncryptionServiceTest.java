package com.banking.springbootbanking.service.encryption;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.main.allow-bean-definition-overriding=true"
})
public class EncryptionServiceTest {

    @Autowired
    private EncryptionService encryptionService;

    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    public void testEncryptPasswordAndCheckPassword() {
        String password = "TestPassword123";
        String hashedPassword = encryptionService.encryptPassword(password);

        assertTrue(passwordEncoder.matches(password, hashedPassword));
    }

    @Test
    public void testCheckPasswordWithInvalidPassword() {
        String password = "TestPassword123";
        String hashedPassword = encryptionService.encryptPassword(password);

        assertFalse(encryptionService.checkPassword("InvalidPassword", hashedPassword));
    }

    @Test
    public void testEncryptPinAndCheckPin() {
        String pin = "1234";
        String hashedPin = encryptionService.encryptPin(pin);

        assertTrue(passwordEncoder.matches(pin, hashedPin));
    }

    @Test
    public void testCheckPinWithInvalidPin() {
        String pin = "1234";
        String hashedPin = encryptionService.encryptPin(pin);

        assertFalse(encryptionService.checkPin("5678", hashedPin));
    }
}

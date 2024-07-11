package com.banking.springbootbanking.service.jwt;

import com.banking.springbootbanking.model.LocalUser;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource(properties = {
        "jwt.algorithm.key=secretkey",
        "jwt.issuer=testissuer",
        "jwt.expiryInSeconds=3600"
})
public class JWTServiceTest {

    @Autowired
    private JWTService jwtService;

    private LocalUser testUser;

    @BeforeEach
    public void setup() {
        UUID userId = UUID.randomUUID();
        testUser = new LocalUser(userId, "testuser", "John", "Doe", "testuser@example.com", "password", "123 Main St", null, null, null);
    }

    @Test
    public void testGenerateJWT() {
        String token = jwtService.generateJWT(testUser);
        String username = jwtService.getUsername(token);
        assertEquals("testuser", username);
    }

    @Test
    public void testGetUsernameFromInvalidToken() {
        String invalidToken = "invalidtoken";
        assertThrows(JWTDecodeException.class, () -> jwtService.getUsername(invalidToken));
    }
}

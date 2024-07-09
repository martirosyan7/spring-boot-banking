package com.banking.springbootbanking.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OpenApi3ConfigTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testOpenApi3Config() {
        // Check if the OpenApi3Config bean is loaded
        OpenApi3Config openApi3Config = context.getBean(OpenApi3Config.class);
        assertNotNull(openApi3Config);

        // Verify OpenAPI Definition annotation
        OpenAPIDefinition openAPIDefinition = OpenApi3Config.class.getAnnotation(OpenAPIDefinition.class);
        assertNotNull(openAPIDefinition);
        assertEquals("Demo Application", openAPIDefinition.info().title());
        assertEquals("v1", openAPIDefinition.info().version());

        // Verify SecurityScheme annotation
        SecurityScheme securityScheme = OpenApi3Config.class.getAnnotation(SecurityScheme.class);
        assertNotNull(securityScheme);
        assertEquals("bearerAuth", securityScheme.name());
        assertEquals(securityScheme.type(), SecuritySchemeType.HTTP);
        assertEquals("JWT", securityScheme.bearerFormat());
        assertEquals("bearer", securityScheme.scheme());
    }
}


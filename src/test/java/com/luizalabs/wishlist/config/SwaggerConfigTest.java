package com.luizalabs.wishlist.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SwaggerConfigTest {

    @Autowired
    private OpenAPI openAPI;

    @Test
    void customOpenAPI_shouldReturnOpenAPIConfig() {
        assertThat(openAPI).isNotNull();

        assertThat(openAPI.getInfo()).isNotNull();
        assertThat(openAPI.getInfo().getTitle()).isEqualTo("Wishlist Luizalabs");
        assertThat(openAPI.getInfo().getVersion()).isEqualTo("1.0");
        assertThat(openAPI.getInfo().getDescription()).isEqualTo("Wishlist service for an e-commerce platform.");
    }
}


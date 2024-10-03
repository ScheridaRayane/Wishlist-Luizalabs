package com.luizalabs.wishlist.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductNotFoundExceptionTest {

    @Test
    void testProductNotFoundExceptionMessage() {
        String errorMessage = "Product not found!";
        ProductNotFoundException exception = new ProductNotFoundException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testProductNotFoundExceptionIsARuntimeException() {
        String errorMessage = "Product not found!";
        ProductNotFoundException exception = new ProductNotFoundException(errorMessage);

        assertTrue(exception instanceof RuntimeException);
    }
}


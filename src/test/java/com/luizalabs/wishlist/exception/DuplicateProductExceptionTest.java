package com.luizalabs.wishlist.exception;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DuplicateProductExceptionTest {

    @Test
    void testDuplicateProductException() {

        String expectedMessage = "Duplicate Product!";

        DuplicateProductException exception = assertThrows(
                DuplicateProductException.class,
                () -> {
                    throw new DuplicateProductException(expectedMessage);
                }
        );

        assertEquals(expectedMessage, exception.getMessage());
    }
}


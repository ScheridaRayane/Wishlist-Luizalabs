package com.luizalabs.wishlist.exception;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CustomExceptionsTest {

    @Test
    void testWishlistLimitExceededException() {

        String expectedMessage = "Wishlist limit exceeded!";

        CustomExceptions.WishlistLimitExceededException exception = assertThrows(
                CustomExceptions.WishlistLimitExceededException.class,
                () -> {
                    throw new CustomExceptions.WishlistLimitExceededException(expectedMessage);
                }
        );

        assertEquals(expectedMessage, exception.getMessage());
    }
}

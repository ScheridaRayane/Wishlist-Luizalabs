package com.luizalabs.wishlist.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WishlistNotFoundExceptionTest {

    @Test
    void testWishlistNotFoundExceptionMessage() {
        String errorMessage = "Wishlist not found!";
        WishlistNotFoundException exception = new WishlistNotFoundException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testWishlistNotFoundExceptionIsARuntimeException() {
        String errorMessage = "Wishlist not found!";
        WishlistNotFoundException exception = new WishlistNotFoundException(errorMessage);

        assertTrue(exception instanceof RuntimeException);
    }
}


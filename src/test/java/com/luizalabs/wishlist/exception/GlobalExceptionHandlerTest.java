package com.luizalabs.wishlist.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleProductNotFoundException_ShouldReturnNotFoundResponse() {

        ProductNotFoundException ex = new ProductNotFoundException("Product not found");

        ResponseEntity<String> response = exceptionHandler.handleProductNotFoundException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Product not found");
    }

    @Test
    void handleWishlistNotFoundException_ShouldReturnNotFoundResponse() {

        WishlistNotFoundException ex = new WishlistNotFoundException("Wishlist not found");

        ResponseEntity<String> response = exceptionHandler.handleWishlistNotFoundException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Wishlist not found");
    }

    @Test
    void handleDuplicateProductException_ShouldReturnBadRequestResponse() {

        DuplicateProductException ex = new DuplicateProductException("Product already exists");

        ResponseEntity<String> response = exceptionHandler.handleDuplicateProductException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Product already exists");
    }

    @Test
    void handleWishlistLimitExceededException_ShouldReturnBadRequestResponse() {

        CustomExceptions.WishlistLimitExceededException ex = new CustomExceptions.WishlistLimitExceededException("Wishlist limit exceeded");

        ResponseEntity<String> response = exceptionHandler.handleWishlistLimitExceededException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("Wishlist limit exceeded");
    }

    @Test
    void handleRuntimeException_ShouldReturnNotFoundResponse() {

        RuntimeException ex = new RuntimeException("Unexpected error occurred");

        ResponseEntity<String> response = exceptionHandler.handleRuntimeException(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Unexpected error occurred");
    }
}


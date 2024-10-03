package com.luizalabs.wishlist.exception;

public class CustomExceptions {

    public static class WishlistLimitExceededException extends RuntimeException {
        public WishlistLimitExceededException(String message) {
            super(message);
        }
    }
}
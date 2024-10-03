package com.luizalabs.wishlist.controller;

import com.luizalabs.wishlist.dto.WishlistRequestDTO;
import com.luizalabs.wishlist.model.Product;
import com.luizalabs.wishlist.model.Wishlist;
import com.luizalabs.wishlist.service.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;

class WishlistControllerTest {

    @InjectMocks
    private WishlistController wishlistController;

    @Mock
    private WishlistService wishlistService;

    private WishlistRequestDTO request;
    private Wishlist mockWishlist;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new WishlistRequestDTO("123", new Product("123", "test", 39));
        mockWishlist = new Wishlist();
    }

    @Test
    void addProductToWishlist_shouldReturnWishlist() {
        when(wishlistService.addProduct(eq("123"), any())).thenReturn(mockWishlist);

        ResponseEntity<Wishlist> response = wishlistController.addProductToWishlist(request);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(mockWishlist);
        verify(wishlistService, times(1)).addProduct(eq("123"), any());
    }

    @Test
    void removeProductFromWishlist_shouldReturnWishlist() {
        when(wishlistService.removeProduct(eq("123"), eq("abc"))).thenReturn(mockWishlist);

        ResponseEntity<Wishlist> response = wishlistController.removeProductFromWishlist("123", "abc");

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(mockWishlist);
        verify(wishlistService, times(1)).removeProduct(eq("123"), eq("abc"));
    }

    @Test
    void getWishlist_shouldReturnWishlist() {
        when(wishlistService.getWishlistByCustomerId(eq("123"))).thenReturn(mockWishlist);

        ResponseEntity<Wishlist> response = wishlistController.getWishlist("123");

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(mockWishlist);
        verify(wishlistService, times(1)).getWishlistByCustomerId(eq("123"));
    }

    @Test
    void checkProductInWishlist_shouldReturnTrue() {
        when(wishlistService.isProductInWishlist(eq("123"), eq("abc"))).thenReturn(true);

        ResponseEntity<Boolean> response = wishlistController.checkProductInWishlist("123", "abc");

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isTrue();
        verify(wishlistService, times(1)).isProductInWishlist(eq("123"), eq("abc"));
    }

    @Test
    void checkProductInWishlist_shouldReturnFalse() {
        when(wishlistService.isProductInWishlist(eq("123"), eq("abc"))).thenReturn(false);

        ResponseEntity<Boolean> response = wishlistController.checkProductInWishlist("123", "abc");

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isFalse();
        verify(wishlistService, times(1)).isProductInWishlist(eq("123"), eq("abc"));
    }
}


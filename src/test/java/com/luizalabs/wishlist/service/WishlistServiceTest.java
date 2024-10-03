package com.luizalabs.wishlist.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.luizalabs.wishlist.exception.CustomExceptions;
import com.luizalabs.wishlist.exception.DuplicateProductException;
import com.luizalabs.wishlist.exception.ProductNotFoundException;
import com.luizalabs.wishlist.exception.WishlistNotFoundException;
import com.luizalabs.wishlist.model.Product;
import com.luizalabs.wishlist.model.Wishlist;
import com.luizalabs.wishlist.repository.WishlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class WishlistServiceTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private WishlistService wishlistService;

    private String customerId;
    private Product product;
    private Wishlist wishlist;

    @BeforeEach
    void setUp() {
        customerId = "customer1";
        product = new Product("productId1", "Product Name", 100.0);
        wishlist = new Wishlist(customerId, new ArrayList<>());
    }

    @Test
    void shouldThrowDuplicateProductExceptionWhenAddingExistingProduct() {
        wishlist.getProducts().add(product);
        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.of(wishlist));

        assertThatExceptionOfType(DuplicateProductException.class)
                .isThrownBy(() -> wishlistService.addProduct(customerId, product))
                .withMessage("Product already exists in the wishlist");
    }

    @Test
    void shouldThrowWishlistLimitExceededExceptionWhenAddingMoreThan20Products() {
        for (int i = 0; i < 20; i++) {
            wishlist.getProducts().add(new Product("productId" + i, "Product " + i, 100.0));
        }
        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.of(wishlist));

        assertThatExceptionOfType(CustomExceptions.WishlistLimitExceededException.class)
                .isThrownBy(() -> wishlistService.addProduct(customerId, new Product("productId21", "Product 21", 100.0)))
                .withMessage("Wishlist limit of 20 products exceeded");
    }

    @Test
    void shouldThrowProductNotFoundExceptionWhenRemovingNonExistingProduct() {
        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.of(wishlist));

        assertThatExceptionOfType(ProductNotFoundException.class)
                .isThrownBy(() -> wishlistService.removeProduct(customerId, "nonExistingProductId"))
                .withMessage("Product with ID nonExistingProductId not found in the wishlist");
    }

    @Test
    void shouldThrowWishlistNotFoundExceptionWhenRemovingProductFromNonExistingWishlist() {
        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.empty());

        assertThatExceptionOfType(WishlistNotFoundException.class)
                .isThrownBy(() -> wishlistService.removeProduct(customerId, product.getProductId()))
                .withMessage("Wishlist not found for customer: customer1");
    }

    @Test
    void shouldThrowExceptionWhenWishlistNotFound() {

        when(wishlistRepository.findByCustomerId("123")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                wishlistService.getWishlistByCustomerId("123")
        );

        assertEquals("Wishlist not found for customer: 123", exception.getMessage());
    }

    @Test
    void shouldReturnWishlistWhenFound() {

        Wishlist wishlist = new Wishlist("wishlistId", "123", new ArrayList<>());

        when(wishlistRepository.findByCustomerId("123")).thenReturn(Optional.of(wishlist));

        Wishlist result = wishlistService.getWishlistByCustomerId("123");

        assertNotNull(result);
        assertEquals(wishlist, result);
    }

    @Test
    void isProductInWishlist_ShouldReturnTrue_WhenProductIsInWishlist() {
        String customerId = "customer123";
        String productId = "product123";

        Wishlist wishlist = new Wishlist();
        Product product = new Product();
        product.setProductId(productId);

        wishlist.setProducts(Arrays.asList(product));

        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.of(wishlist));

        boolean result = wishlistService.isProductInWishlist(customerId, productId);
        assertTrue(result);
    }

    @Test
    void isProductInWishlist_ShouldReturnFalse_WhenProductIsNotInWishlist() {
        String customerId = "customer123";
        String productId = "product456";

        Wishlist wishlist = new Wishlist();
        Product product = new Product();
        product.setProductId("product123");

        wishlist.setProducts(Collections.singletonList(product));

        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.of(wishlist));

        boolean result = wishlistService.isProductInWishlist(customerId, productId);
        assertFalse(result);
    }

    @Test
    void shouldCreateWishlistWhenNoneExists() {

        when(wishlistRepository.findByCustomerId("123")).thenReturn(Optional.empty());

        Wishlist result = wishlistService.getOrCreateWishlist("123");

        assertNotNull(result);
        assertEquals("123", result.getCustomerId());
        assertTrue(result.getProducts().isEmpty());
    }

    @Test
    void shouldReturnExistingWishlistWhenItExists() {

        Wishlist wishlist = new Wishlist("wishlistId", "123", new ArrayList<>());

        when(wishlistRepository.findByCustomerId("123")).thenReturn(Optional.of(wishlist));

        Wishlist result = wishlistService.getOrCreateWishlist("123");

        assertNotNull(result);
        assertEquals(wishlist, result);
    }
}


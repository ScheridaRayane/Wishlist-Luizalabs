package com.luizalabs.wishlist.service;

import com.luizalabs.wishlist.exception.DuplicateProductException;
import com.luizalabs.wishlist.exception.ProductNotFoundException;
import com.luizalabs.wishlist.exception.WishlistNotFoundException;
import com.luizalabs.wishlist.model.Product;
import com.luizalabs.wishlist.model.Wishlist;
import com.luizalabs.wishlist.exception.CustomExceptions.WishlistLimitExceededException;
import com.luizalabs.wishlist.repository.WishlistRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    @Autowired
    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public Wishlist addProduct(String customerId, Product product) {
        Wishlist wishlist = wishlistRepository.findByCustomerId(customerId)
                .orElseGet(() -> new Wishlist(customerId, new ArrayList<>()));

        boolean productExists = wishlist.getProducts().stream()
                .anyMatch(p -> p.getProductId().equals(product.getProductId()));

        if (productExists) {
            throw new DuplicateProductException("Product already exists in the wishlist");
        }

        if (wishlist.getProducts().size() >= 20) {
            throw new WishlistLimitExceededException("Wishlist limit of 20 products exceeded");
        }

        wishlist.getProducts().add(product);
        return wishlistRepository.save(wishlist);
    }

    public Wishlist removeProduct(String customerId, String productId) {
        Wishlist wishlist = wishlistRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new WishlistNotFoundException("Wishlist not found for customer: " + customerId));

        boolean productExists = wishlist.getProducts().stream()
                .anyMatch(p -> p.getProductId().equals(productId));

        if (!productExists) {
            throw new ProductNotFoundException("Product with ID " + productId + " not found in the wishlist");
        }

        wishlist.getProducts().removeIf(p -> p.getProductId().equals(productId));
        return wishlistRepository.save(wishlist);
    }

    public Wishlist getWishlistByCustomerId(String customerId) {
        return wishlistRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new RuntimeException("Wishlist not found for customer: " + customerId));
    }

    public boolean isProductInWishlist(String customerId, String productId) {
        Wishlist wishlist = getWishlistByCustomerId(customerId);
        return wishlist.getProducts().stream().anyMatch(p -> p.getProductId().equals(productId));
    }

    public Wishlist getOrCreateWishlist(String customerId) {
        return wishlistRepository.findByCustomerId(customerId)
                .orElse(new Wishlist(null, customerId, new ArrayList<>()));
    }
}
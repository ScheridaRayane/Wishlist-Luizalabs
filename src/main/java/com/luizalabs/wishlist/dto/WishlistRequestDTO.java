package com.luizalabs.wishlist.dto;

import com.luizalabs.wishlist.model.Product;
import lombok.Data;

@Data
public class WishlistRequestDTO {
    private String customerId;
    private Product product;

    public WishlistRequestDTO(String customerId, Product product) {
        this.customerId = customerId;
        this.product = product;
    }
}

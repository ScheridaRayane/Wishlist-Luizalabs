package com.luizalabs.wishlist.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "wishlists")
@Data
@AllArgsConstructor
public class Wishlist {
    @Id
    private String id;
    private String customerId;
    private List<Product> products;

    public Wishlist() {
    }

    public Wishlist(String customerId, List<Product> products) {
        this.customerId = customerId;
        this.products = products;
    }
}

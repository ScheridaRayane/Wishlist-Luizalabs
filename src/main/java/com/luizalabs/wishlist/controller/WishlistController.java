package com.luizalabs.wishlist.controller;

import com.luizalabs.wishlist.dto.WishlistRequestDTO;
import com.luizalabs.wishlist.model.Wishlist;
import com.luizalabs.wishlist.service.WishlistService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Operation(summary = "Add a product to the wishlist")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product added successfully"),
            @ApiResponse(responseCode = "400", description = "Product already exists in the wishlist")
    })
    @PostMapping("/add")
    public ResponseEntity<Wishlist> addProductToWishlist(@RequestBody WishlistRequestDTO request) {
        Wishlist wishlist = wishlistService.addProduct(request.getCustomerId(), request.getProduct());
        return ResponseEntity.ok(wishlist);
    }

    @Operation(summary = "Remove a product from the wishlist")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product removed successfully"),
            @ApiResponse(responseCode = "400", description = "Product with ID CUSTOMER_ID not found in the wishlist")
    })
    @DeleteMapping("/remove/{customerId}/{productId}")
    public ResponseEntity<Wishlist> removeProductFromWishlist(@PathVariable String customerId, @PathVariable String productId) {
        Wishlist wishlist = wishlistService.removeProduct(customerId, productId);
        return ResponseEntity.ok(wishlist);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer Id founded successfully"),
            @ApiResponse(responseCode = "400", description = "Wishlist not found for customer: CUSTOMER_ID")
    })
    @Operation(summary = "Get the wishlist for a customer")
    @GetMapping("/{customerId}")
    public ResponseEntity<Wishlist> getWishlist(@PathVariable String customerId) {
        Wishlist wishlist = wishlistService.getWishlistByCustomerId(customerId);
        return ResponseEntity.ok(wishlist);
    }

    @Operation(summary = "Check if a product is in the wishlist")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product founded successfully")
    })
    @GetMapping("/check/{customerId}/{productId}")
    public ResponseEntity<Boolean> checkProductInWishlist(@PathVariable String customerId, @PathVariable String productId) {
        boolean isInWishlist = wishlistService.isProductInWishlist(customerId, productId);
        return ResponseEntity.ok(isInWishlist);
    }
}

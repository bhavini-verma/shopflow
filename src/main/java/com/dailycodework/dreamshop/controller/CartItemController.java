package com.dailycodework.dreamshop.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dailycodework.dreamshop.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshop.response.ApiResponse;
import com.dailycodework.dreamshop.service.Cart.iCartItemService;
import com.dailycodework.dreamshop.service.Cart.iCartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cartItems")
public class CartItemController {
    private final iCartItemService cartItemService;
    private final iCartService cartService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(
            @RequestParam(required = false) Long cartId,
            @RequestParam Long productId,
            @RequestParam Integer quantity) {
        try {
            if (cartId == null) {
                cartId = cartService.initializeNewCart();
            }
            cartItemService.addItemToCart(cartId, productId, quantity);
            return new ResponseEntity<ApiResponse>(new ApiResponse("Cart Item added successfully", null), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{cartId}/item/{productId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        try {
            cartItemService.removeItemFromCart(cartId, productId);
            return new ResponseEntity<ApiResponse>(new ApiResponse("Cart Item removed successfully", null), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/cart/{cartId}/item/{productId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId, @PathVariable Long productId, @RequestParam Integer quantity) {
        try {
            cartItemService.updateItemQuantity(cartId, productId, quantity);
            return new ResponseEntity<ApiResponse>(new ApiResponse("Cart Item quantity updated successfully", null), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }
}

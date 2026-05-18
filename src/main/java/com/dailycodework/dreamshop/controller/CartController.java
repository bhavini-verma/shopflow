package com.dailycodework.dreamshop.controller;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailycodework.dreamshop.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshop.model.Cart;
import com.dailycodework.dreamshop.response.ApiResponse;
import com.dailycodework.dreamshop.service.Cart.iCartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final iCartService cartService;

    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId) {
        try {
            Cart cart = cartService.getCart(cartId);
            return new ResponseEntity<ApiResponse>(new ApiResponse("Cart fetched successfully", cart), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId) {
        try {
            cartService.clearCart(cartId);
            return new ResponseEntity<ApiResponse>(new ApiResponse("Cart cleared successfully!", null), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{cartId}/cart/total-price")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId) {
        try {
            BigDecimal totalPrice = cartService.getTotalPrice(cartId);
            return new ResponseEntity<ApiResponse>(new ApiResponse("Cart total price fetched successfully", totalPrice), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
    }
}

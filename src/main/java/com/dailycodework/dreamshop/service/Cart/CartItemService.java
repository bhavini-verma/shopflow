package com.dailycodework.dreamshop.service.Cart;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.dailycodework.dreamshop.exceptions.ResourceNotFoundException;
import com.dailycodework.dreamshop.model.Cart;
import com.dailycodework.dreamshop.model.CartItem;
import com.dailycodework.dreamshop.model.Product;
import com.dailycodework.dreamshop.repository.CartItemRepository;
import com.dailycodework.dreamshop.repository.CartRepository;
import com.dailycodework.dreamshop.product.iProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService implements iCartItemService {
    private final CartItemRepository cartItemRepository;
    private final iProductService productService;
    private final CartRepository cartRepository;
    private final iCartService cartService;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = null;
        for (CartItem item : cart.getCartItems()) {
            if (item.getProduct().getId().equals(productId)) {
                cartItem = item;
                break;
            }
        }
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitprice(product.getPrice());
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.getCartItems().add(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        for (CartItem item : cart.getCartItems()) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(quantity);
                item.setUnitprice(item.getProduct().getPrice());
                item.setTotalPrice();
                break;
            }
        }
        BigDecimal totalAmount = cart.getCartItems()
                .stream()
                .map(item -> item.getTotalPrice() != null ? item.getTotalPrice() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);
        cart.getCartItems().remove(itemToRemove);
        cartRepository.save(cart);
    }

    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        for (CartItem item : cart.getCartItems()) {
            if (item.getProduct().getId().equals(productId)) {
                return item;
            }
        }
        throw new ResourceNotFoundException("Item not found");
    }
}

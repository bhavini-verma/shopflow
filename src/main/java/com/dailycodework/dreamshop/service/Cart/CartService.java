package com.dailycodework.dreamshop.service.Cart;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.dailycodework.dreamshop.model.Cart;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class CartService implements iCartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart getcart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElse(() -> new ResourceNotFoundException("Cart not found"));
        BigDecimal totalamount = cart.getTotalAmount();
        cart.setTotalAmount(totalamount);
        return null;
    }

    @Override
    public void clearCart(Long id) {
        Cart cart = getcart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);

    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getcart(id);

        return cart.getTotalAmount();
    }

}

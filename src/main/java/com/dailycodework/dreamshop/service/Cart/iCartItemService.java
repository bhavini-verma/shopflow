package com.dailycodework.dreamshop.model.CartItem;

public interface iCartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity);

    void updateCartItem(Long cartId, Long productId, int quantity);

    void removeCartItem(Long cartId, Long productId);

}

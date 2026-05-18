package com.dailycodework.dreamshop.service.Cart;

import java.math.BigDecimal;
import com.dailycodework.dreamshop.model.Cart;
import com.dailycodework.dreamshop.model.User;

public interface iCartService {
    Cart getCart(Long id);

    void clearCart(Long id);

    BigDecimal getTotalPrice(Long id);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}

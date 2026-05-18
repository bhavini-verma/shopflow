package com.dailycodework.dreamshop.service.Cart;

import java.math.BigDecimal;
import com.dailycodework.dreamshop.model.Cart;

public interface iCartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Long initializeNewCart();
}

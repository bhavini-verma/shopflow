package com.dailycodework.dreamshop.service.Cart;

import java.security.KeyStore.LoadStoreParameter;

public interface iCartService {
    Cart getcart(Long id);

    void clearCart(Long id);

    BigDecimal getTotalPrice(Long id);

}

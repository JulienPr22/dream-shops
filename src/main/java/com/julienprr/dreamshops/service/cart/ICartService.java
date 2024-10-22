package com.julienprr.dreamshops.service.cart;

import com.julienprr.dreamshops.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCartById(Long id);
    void clearCartById(Long id);
    BigDecimal getTotalPriceById(Long id);
    Long initializeNewCart();
    Cart getCartByUserId(Long userId);
}

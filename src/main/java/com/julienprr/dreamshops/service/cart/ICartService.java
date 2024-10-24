package com.julienprr.dreamshops.service.cart;

import com.julienprr.dreamshops.dto.CartDto;
import com.julienprr.dreamshops.model.Cart;
import com.julienprr.dreamshops.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCartById(Long id);
    void clearCartById(Long id);
    BigDecimal getTotalPriceById(Long id);
    Cart initializeNewCart(User user);
    Cart getCartByUserId(Long userId);

    CartDto convertToDto(Cart cart);
}

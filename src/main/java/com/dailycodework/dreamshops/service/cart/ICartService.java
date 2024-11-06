package com.dailycodework.dreamshops.service.cart;

import com.dailycodework.dreamshops.model.Cart;
import com.dailycodework.dreamshops.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCartById(Long id);
    Cart getCartByUserId(Long userId);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Cart initializeNewCart(User user);
}

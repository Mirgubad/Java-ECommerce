package com.dailycodework.dreamshops.service.cart;

import com.dailycodework.dreamshops.model.CartItem;

public interface ICartItemService {
    void addItem(Long cartId,Long productId,int quantity);
    void removeItem(Long cartId,Long productId);
    void updateItemQuantity(Long cartId,Long productId,int quantity);

}

package com.dailycodework.dreamshops.mapper;

import com.dailycodework.dreamshops.dto.CartDto;
import com.dailycodework.dreamshops.dto.CartItemDto;
import com.dailycodework.dreamshops.model.Cart;
import com.dailycodework.dreamshops.model.CartItem;

import java.util.HashSet;
import java.util.Set;

public class CartMapper {

    static CartDto mapToCartDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setTotalAmount(cart.getTotalAmount());
        Set<CartItemDto> items = new HashSet<>();
        for (CartItem item : cart.getItems()) {
            CartItemDto itemDto = new CartItemDto();
            itemDto.setId(item.getId());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setUnitPrice(item.getUnitPrice());
            itemDto.setTotalPrice(item.getTotalPrice());
            itemDto.setProduct(ProductMapper.mapToProductDto(item.getProduct()));
            items.add(itemDto);
        }
        cartDto.setItems(items);
        return cartDto;
    }
}

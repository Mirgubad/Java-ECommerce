package com.dailycodework.dreamshops.dto;

import lombok.Builder;
import lombok.Data;


import java.math.BigDecimal;

@Data
@Builder
public class CartItemDto {
    private Long id;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private ProductDto product;

    public CartItemDto() {
    }

    public CartItemDto(Long id, int quantity, BigDecimal unitPrice, BigDecimal totalPrice, ProductDto product) {
        this.id = id;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.product = product;
    }
}

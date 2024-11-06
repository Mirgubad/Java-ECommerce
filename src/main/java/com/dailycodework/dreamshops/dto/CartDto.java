package com.dailycodework.dreamshops.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class CartDto {
    private Long id;
    private BigDecimal totalAmount;
    private Set<CartItemDto> items = new HashSet<>();

    public CartDto(Long id, BigDecimal totalAmount, Set<CartItemDto> items) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.items = items;
    }

    public CartDto() {
    }

}

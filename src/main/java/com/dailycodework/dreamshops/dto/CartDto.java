package com.dailycodework.dreamshops.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
public class CartDto {
    private Long id;
    private BigDecimal totalAmount;
    private Set<CartItemDto> items = new HashSet<>();

}

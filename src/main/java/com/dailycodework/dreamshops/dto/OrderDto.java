package com.dailycodework.dreamshops.dto;

import com.dailycodework.dreamshops.enums.OrderStatus;
import com.dailycodework.dreamshops.model.User;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class OrderDto {
    private Long id;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private Set<OrderItemDto> orderItems= new HashSet<>();
    private User user;
}

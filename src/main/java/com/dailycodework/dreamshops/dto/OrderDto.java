package com.dailycodework.dreamshops.dto;

import com.dailycodework.dreamshops.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class OrderDto {
    private Long id;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private Set<OrderItemDto> orderItems= new HashSet<>();


    public OrderDto(Long id, LocalDate orderDate, BigDecimal totalAmount, OrderStatus orderStatus, Set<OrderItemDto> orderItems) {
        this.id = id;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.orderItems = orderItems;
    }

    public OrderDto() {
    }
}

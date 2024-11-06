package com.dailycodework.dreamshops.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private CartDto cart;
    private List<OrderDto> orders;

    public UserDto(Long id, String firstName, String lastName, String email, CartDto cart, List<OrderDto> orders) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cart = cart;
        this.orders = orders;
    }

    public UserDto() {
    }
}

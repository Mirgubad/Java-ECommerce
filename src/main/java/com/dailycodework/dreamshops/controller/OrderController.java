package com.dailycodework.dreamshops.controller;

import com.dailycodework.dreamshops.dto.OrderDto;
import com.dailycodework.dreamshops.exceptions.NotFoundException;
import com.dailycodework.dreamshops.model.Order;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.order.IOrderService;
import com.dailycodework.dreamshops.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;
    private final UserService userService;

    @PostMapping("/place-order")
    public ResponseEntity<ApiResponse> createOrder() {
        try {
            Long userId = userService.getAuthenticatedUser().getId();
            OrderDto order = orderService.placeOrder(userId);
            return ResponseEntity.ok(new ApiResponse( "Order created successfully", order));
        } catch (Exception e) {
           return  ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error occured", null));
        }
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrder(@PathVariable Long orderId) {
        try {
            OrderDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Order retrieved successfully", order));
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Oops!",e.getMessage()));
        }
    }

    @GetMapping("/user-orders")
    public ResponseEntity<ApiResponse> getUserOrders() {
        try {
            Long userId = userService.getAuthenticatedUser().getId();
            List<OrderDto> orders = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Orders retrieved successfully", orders));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Oops!", e.getMessage()));
        }
    }
}

package com.dailycodework.dreamshops.controller;

import com.dailycodework.dreamshops.dto.CartDto;
import com.dailycodework.dreamshops.exceptions.NotFoundException;
import com.dailycodework.dreamshops.model.Cart;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.cart.ICartService;
import com.dailycodework.dreamshops.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService cartService;
    private final ModelMapper modelMapper;
    private final IUserService userService;

    @GetMapping("/cart")
    public ResponseEntity<ApiResponse> getCart() {
        try {
            Long userId = userService.getAuthenticatedUser().getId();
            Cart cart= cartService.getCartByUserId(userId);
            CartDto cartDto = mapToCartDto(cart);
            return ResponseEntity.ok(new ApiResponse("Success", cartDto));
        } catch (NotFoundException e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("{cartId}/clear")
    public  ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId){
        try {
            cartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Cart cleared", null));
        } catch (NotFoundException e) {
           return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("cart/total-price")
    public  ResponseEntity<ApiResponse> getTotalAmount(){
        try {
            Long userId = userService.getAuthenticatedUser().getId();
            BigDecimal totalAmount = cartService.getTotalPrice(userId);
            return ResponseEntity.ok(new ApiResponse("Total amount", totalAmount));
        } catch (NotFoundException e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    private  CartDto mapToCartDto(Cart cart){
        return modelMapper.map(cart, CartDto.class);
    }

}

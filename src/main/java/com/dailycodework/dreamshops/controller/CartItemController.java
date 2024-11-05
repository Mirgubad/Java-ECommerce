package com.dailycodework.dreamshops.controller;

import com.dailycodework.dreamshops.exceptions.NotFoundException;
import com.dailycodework.dreamshops.response.ApiResponse;
import com.dailycodework.dreamshops.service.cart.ICartItemService;
import com.dailycodework.dreamshops.service.cart.ICartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private  final ICartItemService cartItemService;
    private final ICartService cartService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addCartItem(@RequestParam Long productId,
                                                   @RequestParam(required = false) Long cartId,
                                                   @RequestParam Integer quantity) {
        try {
            if(cartId == null) {
                cartId = cartService.initializeNewCart();
            }
            cartItemService.addItem(cartId, productId, quantity);

            return ResponseEntity.ok(new ApiResponse("Item added successfully",null ));

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/item/delete")
    public  ResponseEntity<ApiResponse> deleteCartItem(@RequestParam Long cartId,
                                                       @RequestParam Long productId) {
        try {
            cartItemService.removeItem(cartId,productId);
            return ResponseEntity.ok(new ApiResponse("Item deleted successfully", null));

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @PutMapping("item/update")
    public  ResponseEntity<ApiResponse> updateCartItem(@RequestParam Long cartId,
                                                       @RequestParam Long productId,
                                                       @RequestParam Integer quantity) {
        try {
            cartItemService.updateItemQuantity(cartId,productId,quantity);
            return ResponseEntity.ok(new ApiResponse("Item updated successfully", null));

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

}

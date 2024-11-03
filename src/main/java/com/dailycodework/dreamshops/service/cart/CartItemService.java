package com.dailycodework.dreamshops.service.cart;

import com.dailycodework.dreamshops.exceptions.NotFoundException;
import com.dailycodework.dreamshops.model.Cart;
import com.dailycodework.dreamshops.model.CartItem;
import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.repository.CartItemRepository;
import com.dailycodework.dreamshops.repository.CartRepository;
import com.dailycodework.dreamshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements  ICartItemService{

    private  final CartItemRepository cartItemRepository;
    private  final CartRepository cartRepository;
    private  final IProductService productService;
    private  final ICartService cartService;

    @Override
    public void addItem(Long cartId, Long productId, int quantity) {
        //get the cart
        //get the product
        //check if the product is already in the cart
        // if yes, update the quantity
        // if no , the initiate a new cart item
        Cart cart= cartService.getCartById(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(new CartItem());

        if (cartItem.getId()==null)
        {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }else{
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItem(Long cartId, Long productId) {
        Cart cart = cartService.getCartById(cartId);
        CartItem itemToRemove = getCartItemById(cartId,productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCartById(cartId);
        cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item->{
                    item.setQuantity(quantity);
                    item.setTotalPrice();
                    item.setUnitPrice(item.getProduct().getPrice());
                });

        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    private CartItem getCartItemById(Long cartId,Long productId) {
        Cart cart = cartService.getCartById(cartId);
        return getCartItemById(cartId,productId);
    }
}

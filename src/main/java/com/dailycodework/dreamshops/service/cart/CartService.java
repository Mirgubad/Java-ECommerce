package com.dailycodework.dreamshops.service.cart;

import com.dailycodework.dreamshops.exceptions.NotFoundException;
import com.dailycodework.dreamshops.model.Cart;
import com.dailycodework.dreamshops.model.CartItem;
import com.dailycodework.dreamshops.repository.CartItemRepository;
import com.dailycodework.dreamshops.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements  ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private  final AtomicLong cartIdGenerator = new AtomicLong(0);

    @Override
    public Cart getCartById(Long id) {
        Cart cart= cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cart not found"));

        BigDecimal totalAmount= cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);

        return cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void clearCart(Long id) {
    Cart cart =getCartById(id);
    cartItemRepository.deleteAllByCartId(id);
    cart.getItems().clear();
    cartRepository.deleteById(id);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCartById(id);
        return  cart.getItems()
                .stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    public Long initializeNewCart() {
        Cart cart = new Cart();
        cart.setId(cartIdGenerator.incrementAndGet());
        return cartRepository.save(cart).getId();
    }
}

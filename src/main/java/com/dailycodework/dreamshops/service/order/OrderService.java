package com.dailycodework.dreamshops.service.order;

import com.dailycodework.dreamshops.enums.OrderStatus;
import com.dailycodework.dreamshops.exceptions.NotFoundException;
import com.dailycodework.dreamshops.model.Cart;
import com.dailycodework.dreamshops.model.Order;
import com.dailycodework.dreamshops.model.OrderItem;
import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.repository.OrderRepository;
import com.dailycodework.dreamshops.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements  IOrderService{
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public Order placeOrder(Long userId) {
        return null;
    }

    private Order createOrder(Cart cart){
        Order order = new Order();
        // set user...
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setTotalAmount(calculateTotalAmount(createOrderItems(order, cart)));
        return orderRepository.save(order);
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository
                .findById(orderId)
                .orElseThrow(()-> new NotFoundException("Order not found"));
    }


    private List<OrderItem> createOrderItems(Order order, Cart cart){
        return cart.getItems()
                .stream()
                .map(cartItem ->{
                    Product product = cartItem.getProduct();
                    product.setQuantity(product.getQuantity() - cartItem.getQuantity());
                    productRepository.save(product);
                    return new OrderItem(
                            order,
                            product,
                            cartItem.getQuantity(),
                            product.getPrice());
                }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems){
        return orderItems
                .stream()
                .map(orderItem -> orderItem.getPrice()
                        .multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }
}

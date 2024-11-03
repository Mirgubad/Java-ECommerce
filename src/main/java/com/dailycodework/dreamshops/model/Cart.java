package com.dailycodework.dreamshops.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private BigDecimal totalAmount=BigDecimal.ZERO;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<CartItem> items;

    public void addItem(CartItem cartItem) {
        items.add(cartItem);
        cartItem.setCart(this);
        setTotalAmount();
    }

    public void removeItem(CartItem cartItem) {
        items.remove(cartItem);
        cartItem.setCart(null);
        setTotalAmount();
    }

    private void setTotalAmount() {
        totalAmount= items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }
}

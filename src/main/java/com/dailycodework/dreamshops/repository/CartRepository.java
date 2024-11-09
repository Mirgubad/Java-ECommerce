package com.dailycodework.dreamshops.repository;

import com.dailycodework.dreamshops.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> getByUserId(Long userId);
}

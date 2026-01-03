package com.freshlink.cart.repository;

import com.freshlink.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCustomerEmail(String email);
}

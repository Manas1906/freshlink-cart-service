package com.freshlink.cart.service;

import com.freshlink.cart.dto.AddToCartRequest;
import com.freshlink.cart.model.*;
import com.freshlink.cart.repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final CartRepository repo;

    public CartService(CartRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Cart addToCart(String email, AddToCartRequest req) {

        Cart cart = repo.findByCustomerEmail(email)
                .orElseGet(() -> {
                    Cart c = new Cart();
                    c.setCustomerEmail(email);
                    return c;
                });

        cart.getItems().stream()
                .filter(i -> i.getProductId().equals(req.productId))
                .findFirst()
                .ifPresentOrElse(item -> {
                    item.setQuantity(item.getQuantity() + req.quantity);
                    item.setPrice(req.price);
                }, () -> {
                    CartItem i = new CartItem();
                    i.setProductId(req.productId);
                    i.setProductName(req.productName);
                    i.setPrice(req.price);
                    i.setQuantity(req.quantity);
                    i.setCart(cart);
                    cart.getItems().add(i);
                });

        return repo.save(cart);
    }


        boolean found = false;


    public Cart myCart(String email) {
        return repo.findByCustomerEmail(email).orElseThrow();
    }
}

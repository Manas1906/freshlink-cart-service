package com.freshlink.cart.service;

import com.freshlink.cart.dto.AddToCartRequest;
import com.freshlink.cart.model.*;
import com.freshlink.cart.repository.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository repo;

    public CartService(CartRepository repo) {
        this.repo = repo;
    }

    public Cart addToCart(String email, AddToCartRequest req) {

        Cart cart = repo.findByCustomerEmail(email)
                .orElseGet(() -> {
                    Cart c = new Cart();
                    c.setCustomerEmail(email);
                    return c;
                });

        boolean found = false;

        for (CartItem item : cart.getItems()) {
            if (item.getProductId().longValue() == req.productId.longValue()) {
                // merge logic
                item.setQuantity(item.getQuantity() + req.quantity);
                item.setPrice(req.price); // latest price
                found = true;
                break;
            }
        }

        if (!found) {
            CartItem newItem = new CartItem();
            newItem.setProductId(req.productId);
            newItem.setProductName(req.productName);
            newItem.setPrice(req.price);
            newItem.setQuantity(req.quantity);
            newItem.setCart(cart);
            cart.getItems().add(newItem);
        }

        return repo.save(cart);
    }


    public Cart myCart(String email) {
        return repo.findByCustomerEmail(email).orElseThrow();
    }
}

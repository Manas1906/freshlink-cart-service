package com.freshlink.cart.controller;

import com.freshlink.cart.dto.AddToCartRequest;
import com.freshlink.cart.model.Cart;
import com.freshlink.cart.service.CartService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public Cart add(@RequestBody AddToCartRequest req, Authentication auth) {
        return service.addToCart(auth.getName(), req);
    }

    @GetMapping("/me")
    public Cart my(Authentication auth) {
        return service.myCart(auth.getName());
    }
}

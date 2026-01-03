package com.freshlink.cart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartRequest {
    public Long productId;
    public String productName;
    public double price;
    public int quantity;
}

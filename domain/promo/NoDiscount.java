package com.example.pos.domain.promo;

import com.example.pos.domain.model.Cart;
import com.example.pos.domain.model.Discount;

public class NoDiscount implements PromotionStrategy {
    @Override
    public Discount apply(Cart cart) {
        return Discount.none();
    }
}

package com.example.pos.domain.promo;

import com.example.pos.domain.model.Cart;
import com.example.pos.domain.model.Discount;

public interface PromotionStrategy {
    Discount apply(Cart cart);
}

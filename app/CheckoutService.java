package com.example.pos.app;

import com.example.pos.domain.exception.InsufficientPaymentException;
import com.example.pos.domain.model.*;
import com.example.pos.domain.promo.PromotionStrategy;

public class CheckoutService {
    private final PromotionStrategy promotion;

    public CheckoutService(PromotionStrategy promotion) {
        this.promotion = promotion;
    }

    public Money calculateTotal(Cart cart) {
        Money subtotal = cart.subtotal();
        Money discount = promotion.apply(cart).amount();
        return subtotal.subtract(discount);
    }

    public Order payAndCreateOrder(Cart cart, Money paid, OrderService orderService) {
        Money total = calculateTotal(cart);
        if (total.isZero()) {
            // 空单按业务也可以直接抛错，这里简单返回一个 0 单不允许支付
            throw new InsufficientPaymentException("Cart is empty.");
        }
        if (paid.isLessThan(total)) {
            throw new InsufficientPaymentException("Paid is not enough. Need " + total.format());
        }
        Money change = paid.subtract(total);
        Payment payment = new Payment(paid, change);

        Discount discount = promotion.apply(cart);
        return orderService.createAndSaveOrder(cart, cart.subtotal(), discount, total, payment);
    }
}

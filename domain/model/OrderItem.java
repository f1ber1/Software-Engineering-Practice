package com.example.pos.domain.model;

import java.util.Objects;

public class OrderItem {
    private final ProductId productId;
    private final String name;
    private final Money unitPrice;
    private final int quantity;

    public OrderItem(ProductId productId, String name, Money unitPrice, int quantity) {
        this.productId = Objects.requireNonNull(productId);
        this.name = Objects.requireNonNull(name);
        this.unitPrice = Objects.requireNonNull(unitPrice);
        if (quantity <= 0) throw new IllegalArgumentException("quantity must be > 0");
        this.quantity = quantity;
    }

    public ProductId productId() { return productId; }
    public String name() { return name; }
    public Money unitPrice() { return unitPrice; }
    public int quantity() { return quantity; }

    public Money lineTotal() {
        return unitPrice.multiply(quantity);
    }
}

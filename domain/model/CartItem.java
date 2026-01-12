package com.example.pos.domain.model;

import java.util.Objects;

public class CartItem {
    private final Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = Objects.requireNonNull(product);
        changeQuantity(quantity);
    }

    public Product product() { return product; }
    public int quantity() { return quantity; }

    public void increase(int qty) {
        if (qty <= 0) throw new IllegalArgumentException("qty must be > 0");
        this.quantity += qty;
    }

    public void changeQuantity(int qty) {
        if (qty <= 0) throw new IllegalArgumentException("qty must be > 0");
        this.quantity = qty;
    }

    public Money lineTotal() {
        return product.unitPrice().multiply(quantity);
    }
}

package com.example.pos.domain.model;

import java.util.Objects;

public class Product {
    private final ProductId id;
    private final String name;
    private final Money unitPrice;
    private final String code;

    public Product(String code, String name, Money unitPrice) {
        this.id = new ProductId(code);
        this.code = Objects.requireNonNull(code).trim();
        this.name = Objects.requireNonNull(name).trim();
        this.unitPrice = Objects.requireNonNull(unitPrice);
        if (this.code.isEmpty() || this.name.isEmpty()) throw new IllegalArgumentException("Product fields blank");
    }

    public ProductId id() { return id; }
    public String code() { return code; }
    public String name() { return name; }
    public Money unitPrice() { return unitPrice; }
}

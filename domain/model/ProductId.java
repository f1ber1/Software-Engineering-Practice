package com.example.pos.domain.model;

import java.util.Objects;

public final class ProductId {
    private final String value;

    public ProductId(String value) {
        this.value = Objects.requireNonNull(value).trim();
        if (this.value.isEmpty()) throw new IllegalArgumentException("ProductId is blank");
    }

    public String value() { return value; }

    @Override public String toString() { return value; }
}

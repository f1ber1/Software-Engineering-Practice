package com.example.pos.domain.model;

import java.util.UUID;

public final class OrderId {
    private final String value;

    private OrderId(String value) {
        this.value = value;
    }

    public static OrderId newId() {
        return new OrderId(UUID.randomUUID().toString());
    }

    public String value() { return value; }

    @Override public String toString() { return value; }
}

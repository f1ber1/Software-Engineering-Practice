package com.example.pos.domain.model;

import java.util.Objects;

public final class Discount {
    private final String description;
    private final Money amount;

    public Discount(String description, Money amount) {
        this.description = Objects.requireNonNull(description);
        this.amount = Objects.requireNonNull(amount);
    }

    public String description() { return description; }
    public Money amount() { return amount; }

    public static Discount none() {
        return new Discount("No discount", Money.zero());
    }
}

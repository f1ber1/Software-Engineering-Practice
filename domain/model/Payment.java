package com.example.pos.domain.model;

import java.util.Objects;

public class Payment {
    private final Money paid;
    private final Money change;

    public Payment(Money paid, Money change) {
        this.paid = Objects.requireNonNull(paid);
        this.change = Objects.requireNonNull(change);
    }

    public Money paid() { return paid; }
    public Money change() { return change; }
}

package com.example.pos.infra.receipt;

import com.example.pos.domain.model.Order;

public class TextReceiptPrinter implements ReceiptPrinter {
    @Override
    public String print(Order order) {
        return order.toReceiptText();
    }
}

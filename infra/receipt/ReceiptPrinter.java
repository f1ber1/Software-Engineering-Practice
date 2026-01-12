package com.example.pos.infra.receipt;

import com.example.pos.domain.model.Order;

public interface ReceiptPrinter {
    String print(Order order);
}

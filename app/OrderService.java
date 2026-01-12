package com.example.pos.app;

import com.example.pos.domain.model.*;
import com.example.pos.infra.repo.OrderRepository;

public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createAndSaveOrder(Cart cart, Money subtotal, Discount discount, Money total, Payment payment) {
        Order order = Order.fromCart(cart, subtotal, discount, total, payment);
        orderRepository.save(order);
        return order;
    }
}

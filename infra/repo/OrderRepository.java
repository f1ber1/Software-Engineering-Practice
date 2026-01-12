package com.example.pos.infra.repo;

import com.example.pos.domain.model.Order;

public interface OrderRepository {
    void save(Order order);
}

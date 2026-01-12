package com.example.pos.infra.repo;

import com.example.pos.domain.model.Product;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findByCode(String code);
}

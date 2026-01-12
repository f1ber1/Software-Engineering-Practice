package com.example.pos.infra.repo;

import com.example.pos.domain.model.Money;
import com.example.pos.domain.model.Product;

import java.math.BigDecimal;
import java.util.*;

public class InMemoryProductRepository implements ProductRepository {
    private final Map<String, Product> products = new HashMap<>();

    public InMemoryProductRepository() {
        // 样例商品（可自行替换/扩展）
        put(new Product("1001", "Coca Cola", Money.of(new BigDecimal("3.50"))));
        put(new Product("1002", "Bread",     Money.of(new BigDecimal("5.20"))));
        put(new Product("1003", "Milk",      Money.of(new BigDecimal("6.80"))));
        put(new Product("2001", "Apple",     Money.of(new BigDecimal("1.20"))));
    }

    private void put(Product p) {
        products.put(p.code(), p);
    }

    @Override
    public Optional<Product> findByCode(String code) {
        if (code == null) return Optional.empty();
        return Optional.ofNullable(products.get(code.trim()));
    }
}

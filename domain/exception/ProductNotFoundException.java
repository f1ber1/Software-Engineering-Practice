package com.example.pos.domain.exception;

public class ProductNotFoundException extends DomainException {
    public ProductNotFoundException(String code) {
        super("Product not found: " + code);
    }
}

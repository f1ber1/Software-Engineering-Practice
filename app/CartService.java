package com.example.pos.app;

import com.example.pos.domain.exception.ProductNotFoundException;
import com.example.pos.domain.model.Cart;
import com.example.pos.domain.model.Product;
import com.example.pos.infra.repo.ProductRepository;

public class CartService {
    private final Cart cart = new Cart();
    private final ProductRepository productRepository;

    public CartService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addItem(String code, int qty) {
        Product p = productRepository.findByCode(code)
                .orElseThrow(() -> new ProductNotFoundException(code));
        cart.addProduct(p, qty);
    }

    public void removeItem(String code) {
        cart.removeByCode(code);
    }

    public void changeQuantity(String code, int qty) {
        cart.changeQuantityByCode(code, qty);
    }

    public Cart getCart() {
        return cart;
    }

    public void clear() {
        cart.clear();
    }
}

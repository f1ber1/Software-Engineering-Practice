package com.example.pos.domain.model;

import java.util.*;

public class Cart {
    private final Map<String, CartItem> itemsByCode = new LinkedHashMap<>();

    public void addProduct(Product p, int qty) {
        CartItem existing = itemsByCode.get(p.code());
        if (existing == null) {
            itemsByCode.put(p.code(), new CartItem(p, qty));
        } else {
            existing.increase(qty);
        }
    }

    public void removeByCode(String code) {
        itemsByCode.remove(code);
    }

    public void changeQuantityByCode(String code, int qty) {
        CartItem item = itemsByCode.get(code);
        if (item == null) return;
        item.changeQuantity(qty);
    }

    public Money subtotal() {
        Money sum = Money.zero();
        for (CartItem item : itemsByCode.values()) {
            sum = sum.add(item.lineTotal());
        }
        return sum;
    }

    public List<CartItem> items() {
        return List.copyOf(itemsByCode.values());
    }

    public boolean isEmpty() {
        return itemsByCode.isEmpty();
    }

    public void clear() {
        itemsByCode.clear();
    }

    public String toDisplayText() {
        if (itemsByCode.isEmpty()) return "(empty)\n";
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-10s %-16s %8s %6s %10s%n", "CODE", "NAME", "PRICE", "QTY", "LINE"));
        sb.append("------------------------------------------------------------\n");
        for (CartItem it : itemsByCode.values()) {
            sb.append(String.format(
                    "%-10s %-16s %8s %6d %10s%n",
                    it.product().code(),
                    trimTo(it.product().name(), 16),
                    it.product().unitPrice().format(),
                    it.quantity(),
                    it.lineTotal().format()
            ));
        }
        sb.append("------------------------------------------------------------\n");
        sb.append("SUBTOTAL: ").append(subtotal().format()).append("\n");
        return sb.toString();
    }

    private static String trimTo(String s, int n) {
        if (s.length() <= n) return s;
        return s.substring(0, n - 1) + "…";
    }
}

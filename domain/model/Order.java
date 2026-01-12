package com.example.pos.domain.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private final OrderId id;
    private final LocalDateTime createdAt;
    private final List<OrderItem> items;
    private final Money subtotal;
    private final Discount discount;
    private final Money total;
    private final Payment payment;

    private Order(OrderId id,
                  LocalDateTime createdAt,
                  List<OrderItem> items,
                  Money subtotal,
                  Discount discount,
                  Money total,
                  Payment payment) {
        this.id = id;
        this.createdAt = createdAt;
        this.items = List.copyOf(items);
        this.subtotal = subtotal;
        this.discount = discount;
        this.total = total;
        this.payment = payment;
    }

    public static Order fromCart(Cart cart, Money subtotal, Discount discount, Money total, Payment payment) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem ci : cart.items()) {
            Product p = ci.product();
            orderItems.add(new OrderItem(p.id(), p.name(), p.unitPrice(), ci.quantity()));
        }
        return new Order(OrderId.newId(), LocalDateTime.now(), orderItems, subtotal, discount, total, payment);
    }

    public OrderId id() { return id; }
    public LocalDateTime createdAt() { return createdAt; }
    public List<OrderItem> items() { return items; }
    public Money subtotal() { return subtotal; }
    public Discount discount() { return discount; }
    public Money total() { return total; }
    public Payment payment() { return payment; }

    public String toReceiptText() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order: ").append(id.value()).append("\n");
        sb.append("Time : ").append(createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        sb.append("--------------------------------------------\n");
        sb.append(String.format("%-16s %8s %6s %10s%n", "ITEM", "PRICE", "QTY", "LINE"));
        for (OrderItem it : items) {
            sb.append(String.format(
                    "%-16s %8s %6d %10s%n",
                    trimTo(it.name(), 16),
                    it.unitPrice().format(),
                    it.quantity(),
                    it.lineTotal().format()
            ));
        }
        sb.append("--------------------------------------------\n");
        sb.append("Subtotal : ").append(subtotal.format()).append("\n");
        sb.append("Discount : ").append(discount.amount().format())
          .append(" (").append(discount.description()).append(")\n");
        sb.append("Total    : ").append(total.format()).append("\n");
        sb.append("Paid     : ").append(payment.paid().format()).append("\n");
        sb.append("Change   : ").append(payment.change().format()).append("\n");
        return sb.toString();
    }

    private static String trimTo(String s, int n) {
        if (s.length() <= n) return s;
        return s.substring(0, n - 1) + "…";
    }
}

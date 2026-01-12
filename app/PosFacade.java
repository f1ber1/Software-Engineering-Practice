package com.example.pos.app;

import com.example.pos.domain.model.Money;
import com.example.pos.domain.model.Order;
import com.example.pos.infra.receipt.ReceiptPrinter;

public class PosFacade {
    private final CartService cartService;
    private final CheckoutService checkoutService;
    private final OrderService orderService;
    private final ReceiptPrinter printer;

    public PosFacade(CartService cartService,
                     CheckoutService checkoutService,
                     OrderService orderService,
                     ReceiptPrinter printer) {
        this.cartService = cartService;
        this.checkoutService = checkoutService;
        this.orderService = orderService;
        this.printer = printer;
    }

    public void addItem(String productCode, int qty) {
        cartService.addItem(productCode, qty);
    }

    public void removeItem(String productCode) {
        cartService.removeItem(productCode);
    }

    public void changeQuantity(String productCode, int qty) {
        cartService.changeQuantity(productCode, qty);
    }

    public void clearCart() {
        cartService.clear();
    }

    public String viewCart() {
        return cartService.getCart().toDisplayText();
    }

    public Money checkoutTotal() {
        return checkoutService.calculateTotal(cartService.getCart());
    }

    public String payAndPrintReceipt(Money paid) {
        var cart = cartService.getCart();
        Order order = checkoutService.payAndCreateOrder(cart, paid, orderService);
        cartService.clear();
        return printer.print(order);
    }
}

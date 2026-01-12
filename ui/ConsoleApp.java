package com.example.pos.ui;

import com.example.pos.app.*;
import com.example.pos.domain.exception.DomainException;
import com.example.pos.domain.model.Money;
import com.example.pos.domain.promo.NoDiscount;
import com.example.pos.infra.receipt.TextReceiptPrinter;
import com.example.pos.infra.repo.FileOrderRepository;
import com.example.pos.infra.repo.InMemoryProductRepository;

import java.nio.file.Paths;
import java.util.Scanner;

public class ConsoleApp {

    public static void main(String[] args) {
        // ----- Infrastructure -----
        var productRepo = new InMemoryProductRepository(); // 内置样例商品
        var orderRepo = new FileOrderRepository(Paths.get("orders.txt")); // 可删掉/替换为内存
        var printer = new TextReceiptPrinter();

        // ----- Application wiring -----
        var cartService = new CartService(productRepo);
        var orderService = new OrderService(orderRepo);
        var checkoutService = new CheckoutService(new NoDiscount());
        var facade = new PosFacade(cartService, checkoutService, orderService, printer);

        // ----- UI -----
        try (Scanner sc = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                printMenu();
                System.out.print("> ");
                String choice = sc.nextLine().trim();

                try {
                    switch (choice) {
                        case "1" -> {
                            System.out.print("Enter product code: ");
                            String code = InputValidator.requireNonBlank(sc.nextLine(), "product code");
                            System.out.print("Enter quantity: ");
                            int qty = InputValidator.requirePositiveInt(sc.nextLine(), "quantity");
                            facade.addItem(code, qty);
                            System.out.println("Added. Current cart:");
                            System.out.println(facade.viewCart());
                        }
                        case "2" -> {
                            System.out.println("Current cart:");
                            System.out.println(facade.viewCart());
                        }
                        case "3" -> {
                            System.out.println("Cart subtotal/total:");
                            System.out.println("Total due: " + facade.checkoutTotal().format());
                        }
                        case "4" -> {
                            Money total = facade.checkoutTotal();
                            if (total.isZero()) {
                                System.out.println("Cart is empty.");
                                break;
                            }
                            System.out.println("Total due: " + total.format());
                            System.out.print("Enter paid amount: ");
                            var paidBd = InputValidator.requireNonNegativeDecimal(sc.nextLine(), "paid amount");
                            String receipt = facade.payAndPrintReceipt(Money.of(paidBd));
                            System.out.println("\n========== RECEIPT ==========");
                            System.out.println(receipt);
                            System.out.println("=============================\n");
                        }
                        case "5" -> {
                            System.out.print("Enter product code to remove: ");
                            String code = InputValidator.requireNonBlank(sc.nextLine(), "product code");
                            facade.removeItem(code);
                            System.out.println("Removed. Current cart:");
                            System.out.println(facade.viewCart());
                        }
                        case "6" -> {
                            System.out.print("Enter product code to change quantity: ");
                            String code = InputValidator.requireNonBlank(sc.nextLine(), "product code");
                            System.out.print("Enter new quantity (>=1): ");
                            int qty = InputValidator.requirePositiveInt(sc.nextLine(), "quantity");
                            facade.changeQuantity(code, qty);
                            System.out.println("Updated. Current cart:");
                            System.out.println(facade.viewCart());
                        }
                        case "7" -> {
                            facade.clearCart();
                            System.out.println("Cart cleared.");
                        }
                        case "0" -> running = false;
                        default -> System.out.println("Unknown option.");
                    }
                } catch (DomainException | IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("Unexpected error: " + e);
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("""
                \n=== Java POS (Console) ===
                1) Add item
                2) View cart
                3) Checkout (show total)
                4) Pay & Print receipt
                5) Remove item
                6) Change quantity
                7) Clear cart
                0) Exit
                """);
    }
}

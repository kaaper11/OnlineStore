package cli;

import dto.order.OrderDto;
import exception.CartEmptyException;
import exception.CartNotFoundException;
import exception.ClientNotFoundException;
import lombok.AllArgsConstructor;
import service.impl.DiscountServiceImpl;
import service.impl.OrderServiceImpl;

import java.util.Scanner;

@AllArgsConstructor
public class OrderCli {
    private final OrderServiceImpl orderService;
    private final DiscountServiceImpl discountService;
    private final Scanner scanner;

    public void run(Long clientId) {
        try {
            while (true) {
                System.out.println("\nZamówienia");
                System.out.println("1. Złóż zamówienie");
                System.out.println("2. Pokaż moje zamówienia");
                System.out.println("3. Złóż zamówienie async");
                System.out.println("0. Wyjdź");
                System.out.print("Wybierz opcję: ");

                switch (scanner.nextLine()) {
                    case "1" -> placeOrder(clientId);
                    case "2" -> showMyOrders(clientId);
                    case "3" -> placeOrderAsync(clientId);
                    case "0" -> {
                        return;
                    }
                    default -> System.out.println("Nieznana opcja.");
                }
            }
        } catch (ClientNotFoundException | CartNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        } catch (CartEmptyException e) {
            System.out.println(e.getMessage());
        }
    }

    private void placeOrder(Long clientId) {
        OrderDto order = orderService.placeOrder(clientId);
        System.out.println("\nZamówienie złożone!");
        System.out.println("Produkty:");
        order.products().forEach(p ->
                System.out.println("  - " + p + discountService.formatProductWithDiscount(p))
        );
        System.out.println("Suma: " + order.totalPrice() + " zł");
    }

    private void showMyOrders(Long clientId) {
        var orders = orderService.getOrdersByClientId(clientId);
        if (orders.isEmpty()) {
            System.out.println("\nBrak zamówień.");
            return;
        }
        orders.forEach(order -> {
            System.out.println("Suma: " + order.totalPrice() + " zł");
            order.products().forEach(p ->
                    System.out.println("  - " + p + discountService.formatProductWithDiscount(p))
            );
        });
    }

    private void placeOrderAsync(Long clientId) {
        System.out.println("\nPrzetwarzam...");
        orderService.placeOrderAsync(clientId)
                .thenAccept(order -> {
                    System.out.println("Zamówienie złożone! Suma: " + order.totalPrice() + " zł");
                    order.products().forEach(product -> System.out.println("  - " + product +
                            discountService.formatProductWithDiscount(product)));
                })
                .exceptionally(e -> {
                    System.out.println("Błąd: " + e.getMessage());
                    return null;
                });
    }
}
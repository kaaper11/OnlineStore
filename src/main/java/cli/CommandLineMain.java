package cli;

import dto.client.ClientResponseDto;
import lombok.AllArgsConstructor;
import service.cart.CartServiceImpl;
import service.client.ClientServiceImpl;
import service.discount.DiscountServiceImpl;
import service.invoice.InvoiceServiceImpl;
import service.order.OrderServiceImpl;
import service.productfacade.ProductFacadeServiceImpl;

import java.util.Scanner;

@AllArgsConstructor
public class CommandLineMain {
    private final Scanner scanner = new Scanner(System.in);
    private final CartServiceImpl cartService;
    private final ClientServiceImpl clientService;
    private final InvoiceServiceImpl invoiceService;
    private final OrderServiceImpl orderService;
    private final ProductFacadeServiceImpl productFacadeServiceImpl;
    private final DiscountServiceImpl discountService;


    public void start() {
        AuthCli authCli = new AuthCli(clientService, scanner);

        ClientResponseDto loggedClient = authCli.showAuthMenu();

        showMainMenu(loggedClient);
    }

    private void showMainMenu(ClientResponseDto loggedClient) {
        while (true) {
            System.out.println("\nWitaj " + loggedClient.name());
            System.out.println("1. Produkty (admin)");
            System.out.println("2. Koszyk");
            System.out.println("3. Zamówienia");
            System.out.println("4. Faktury");
            System.out.println("0. Wyloguj");
            System.out.print("Wybierz opcję: ");

            switch (scanner.nextLine()) {
                case "1" -> new ProductCli(productFacadeServiceImpl, discountService, scanner)
                        .showProductMenu(loggedClient.id());
                case "2" -> new CartCli(cartService, discountService, scanner).run(loggedClient.id());
                case "3" -> new OrderCli(orderService, discountService, scanner).run(loggedClient.id());
                case "4" -> new InvoiceCli(invoiceService, discountService, scanner).run(loggedClient.id());
                case "0" -> {
                    return;
                }
                default -> System.out.println("Nieznana opcja.");
            }
        }
    }
}

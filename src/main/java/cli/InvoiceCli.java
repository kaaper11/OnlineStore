package cli;

import dto.invoice.InvoiceDto;
import lombok.AllArgsConstructor;
import service.impl.InvoiceServiceImpl;

import java.util.Scanner;

@AllArgsConstructor
public class InvoiceCli {
    private final InvoiceServiceImpl invoiceService;
    private final Scanner scanner;

    public void run(Long clientId) {
        while (true) {
            System.out.println("\nFaktury");
            System.out.println("1. Pokaż moje faktury");
            System.out.println("0. Wyjdź");
            System.out.print("Wybierz opcję: ");

            switch (scanner.nextLine()) {
                case "1" -> showMyInvoices(clientId);
                case "0" -> { return; }
                default -> System.out.println("Nieznana opcja.");
            }
        }
    }

    private void showMyInvoices(Long clientId) {
        var invoices = invoiceService.getInvoicesByClientId(clientId);
        if (invoices.isEmpty()) {
            System.out.println("Brak faktur.");
            return;
        }
        invoices.forEach(this::printInvoice);
    }

    private void printInvoice(InvoiceDto invoice) {
        System.out.println("\nFAKTURA");
        System.out.println("ID zamówienia: " + invoice.orderId());
        System.out.println("Klient: " + invoice.client().name());
        System.out.println("Data wystawienia: " + invoice.invoiceDateTime());
        System.out.println("Produkty:");
        invoice.products().forEach(p ->
                System.out.println("  - " + p.getName() + " | " + p.getPrice() + " zł")
        );
        System.out.println("Suma: " + invoice.totalPrice() + " zł");
        System.out.println("---------------");
    }
}
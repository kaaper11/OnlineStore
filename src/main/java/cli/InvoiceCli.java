package cli;

import dto.invoice.InvoiceDto;
import lombok.AllArgsConstructor;
import service.impl.DiscountServiceImpl;
import service.impl.InvoiceServiceImpl;

import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class InvoiceCli {
    private final InvoiceServiceImpl invoiceService;
    private final DiscountServiceImpl discountService;
    private final Scanner scanner;

    public void run(Long clientId) {
        while (true) {
            System.out.println("\nFaktury");
            System.out.println("1. Pokaż moje faktury");
            System.out.println("2. Zapisz fakture");
            System.out.println("0. Wyjdź");
            System.out.print("Wybierz opcję: ");

            switch (scanner.nextLine()) {
                case "1" -> showMyInvoices(clientId);
                case "2" -> {
                    try {
                        saveInvoice(clientId);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case "0" -> {
                    return;
                }
                default -> System.out.println("Nieznana opcja.");
            }
        }
    }

    private List<InvoiceDto> showMyInvoices(Long clientId) {
        ZoneId zone = ZoneId.systemDefault();
        List<InvoiceDto> invoices = invoiceService.getInvoicesByClientId(clientId, zone);
        if (invoices.isEmpty()) {
            System.out.println("Brak faktur.");
        }
        invoices.forEach(this::printInvoice);

        return invoices;
    }

    private void saveInvoice(Long clientId) throws IOException {
        System.out.println("Którą fakture chcesz pobrać?");
        List<InvoiceDto> invoices = showMyInvoices(clientId);

        System.out.println("Wpisz numer: ");
        int number = TypeReaderCli.readInt(scanner);

        invoiceService.saveInvoiceToFile(invoices.get(number - 1).orderId());
    }

    private void printInvoice(InvoiceDto invoice) {
        System.out.println("\nFAKTURA");
        System.out.println("ID zamówienia: " + invoice.orderId());
        System.out.println("Klient: " + invoice.client().name());
        System.out.println("Data wystawienia: " + invoice.invoiceDateTime().format(DateTimeFormatter
                .ofPattern("dd-MM-yyyy HH:mm z")));
        System.out.println("Produkty:");
        invoice.products().forEach(p ->
                System.out.println("  - " + p + discountService.formatProductWithDiscount(p))
        );
        System.out.println("Suma: " + invoice.totalPrice() + " zł");
        System.out.println("---------------");
    }
}
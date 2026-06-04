package repository;

import entity.invoice.Invoice;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class InvoiceRepository {
    private final Set<Invoice> invoices = new HashSet<>();
    private Long idCounter = 0L;

    public Invoice save(Invoice invoice) {
        invoices.add(invoice);
        return invoice;
    }

    public Optional<Invoice> getInvoiceByOrderId(Long orderId) {
        return invoices.stream()
                .filter(invoice -> invoice.getOrderId().equals(orderId))
                .findFirst();
    }

    public List<Invoice> getInvoicesByClientId(Long clientId) {
        return invoices.stream()
                .filter(invoice -> invoice.getClient().getId().equals(clientId))
                .toList();
    }

    public Long getNextId() {
        return idCounter++;
    }
}

package repository;

import entity.invoice.Invoice;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InvoiceRepository {
    private final Set<Invoice> invoices = ConcurrentHashMap.newKeySet();
    private final AtomicLong idCounter = new AtomicLong(1L);

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
        return idCounter.getAndIncrement();
    }
}

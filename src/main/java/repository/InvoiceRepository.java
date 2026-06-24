package repository;

import entity.invoice.Invoice;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repository responsible for managing Invoice entities in memory.
 * It provides operations for saving and retrieving invoices based on
 * order or client identifiers using a thread-safe set.
 */
public class InvoiceRepository {
    private final Set<Invoice> invoices = ConcurrentHashMap.newKeySet();
    private final AtomicLong idCounter = new AtomicLong(1L);

    /**
     * Saves an invoice entity into the repository.
     *
     * @param invoice the invoice entity to be stored
     * @return the saved invoice instance
     */
    public Invoice save(Invoice invoice) {
        invoice.setId(getNextId());
        invoices.add(invoice);
        return invoice;
    }

    /**
     * Retrieves an invoice by the associated order identifier.
     *
     * @param orderId the identifier of the order
     * @return an Optional containing the found invoice or empty if not found
     */
    public Optional<Invoice> getInvoiceByOrderId(Long orderId) {
        return invoices.stream()
                .filter(invoice -> invoice.getOrderId().equals(orderId))
                .findFirst();
    }

    /**
     * Retrieves all invoices associated with a specific client.
     *
     * @param clientId the identifier of the client
     * @return a list of invoices belonging to the given client
     */
    public List<Invoice> getInvoicesByClientId(Long clientId) {
        return invoices.stream()
                .filter(invoice -> invoice.getClient().getId().equals(clientId))
                .toList();
    }

    private Long getNextId() {
        return idCounter.getAndIncrement();
    }
}

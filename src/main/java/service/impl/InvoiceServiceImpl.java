package service.impl;

import dto.invoice.InvoiceDto;
import entity.invoice.Invoice;
import exception.InvoiceNotFoundException;
import export.InvoiceJsonWriter;
import lombok.AllArgsConstructor;
import mapper.InvoiceMapper;
import repository.InvoiceRepository;
import service.InvoiceService;

import java.io.IOException;
import java.time.ZoneId;
import java.util.List;

/**
 * Service implementation responsible for managing invoices.
 * It provides functionality for retrieving invoices by order or client,
 * as well as exporting invoices to a JSON file.
 */
@AllArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private InvoiceRepository invoiceRepository;
    private InvoiceJsonWriter invoiceJsonWriter;

    /**
     * Retrieves an invoice by its associated order identifier.
     *
     * @param orderId the identifier of the order
     * @return the InvoiceDto corresponding to the given order
     * @throws InvoiceNotFoundException if no invoice exists for the order
     */
    @Override
    public InvoiceDto getInvoiceByOrderId(Long orderId) {
        return invoiceRepository.getInvoiceByOrderId(orderId)
                .map(InvoiceMapper::mapInvoiceToDto)
                .orElseThrow(InvoiceNotFoundException::new);
    }

    /**
     * Retrieves all invoices belonging to a specific client.
     *
     * @param clientId the identifier of the client
     * @return a list of InvoiceDto objects associated with the client
     */
    @Override
    public List<InvoiceDto> getInvoicesByClientId(Long clientId, ZoneId zone) {
        return invoiceRepository.getInvoicesByClientId(clientId).stream()
                .map(invoice -> invoice.invoiceCopyWithLocalTime(invoice.getInvoiceDateTime()
                        .withZoneSameInstant(zone)))
                .map(InvoiceMapper::mapInvoiceToDto)
                .toList();
    }

    /**
     * Saves an invoice to a JSON file and returns its DTO representation.
     *
     * @param orderId the identifier of the order whose invoice should be exported
     * @return the InvoiceDto representation of the saved invoice
     * @throws IOException              if an error occurs during file writing
     * @throws InvoiceNotFoundException if no invoice exists for the order
     */
    @Override
    public InvoiceDto saveInvoiceToFile(Long orderId) throws IOException {
        Invoice invoice = invoiceRepository.getInvoiceByOrderId(orderId).orElseThrow(InvoiceNotFoundException::new);
        invoiceJsonWriter.saveInvoiceToJson(invoice, "invoice.json");
        return InvoiceMapper.mapInvoiceToDto(invoice);
    }
}

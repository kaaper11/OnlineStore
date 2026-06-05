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
import java.util.List;

@AllArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private InvoiceRepository invoiceRepository;
    private InvoiceJsonWriter invoiceJsonWriter;

    @Override
    public InvoiceDto getInvoiceByOrderId(Long orderId) {
        return invoiceRepository.getInvoiceByOrderId(orderId)
                .map(InvoiceMapper::mapInvoiceToDto)
                .orElseThrow(InvoiceNotFoundException::new);
    }

    @Override
    public List<InvoiceDto> getInvoicesByClientId(Long clientId) {
        return invoiceRepository.getInvoicesByClientId(clientId).stream()
                .map(InvoiceMapper::mapInvoiceToDto)
                .toList();
    }

    @Override
    public InvoiceDto saveInvoiceToFile(Long orderId) throws IOException {
        Invoice invoice = invoiceRepository.getInvoiceByOrderId(orderId).orElseThrow(InvoiceNotFoundException::new);
        invoiceJsonWriter.saveInvoiceToJson(invoice, "invoice.json");
        return InvoiceMapper.mapInvoiceToDto(invoice);
    }
}

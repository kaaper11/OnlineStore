package service.impl;

import dto.invoice.InvoiceDto;
import exception.InvoiceNotFoundException;
import lombok.AllArgsConstructor;
import mapper.InvoiceMapper;
import repository.InvoiceRepository;
import service.InvoiceService;

import java.util.List;

@AllArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private InvoiceRepository invoiceRepository;

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
}

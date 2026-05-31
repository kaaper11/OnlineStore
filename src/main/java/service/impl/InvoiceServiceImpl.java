package service.impl;

import dto.invoice.InvoiceDto;
import exception.InvoiceNotFoundException;
import lombok.AllArgsConstructor;
import mapper.InvoiceMapper;
import repository.InvoiceRepository;
import service.InvoiceService;

@AllArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    private InvoiceRepository invoiceRepository;

    @Override
    public InvoiceDto getInvoiceByOrderId(Long orderId) {
        return invoiceRepository.getInvoiceByOrderId(orderId)
                .map(InvoiceMapper::mapInvoiceToDto)
                .orElseThrow(InvoiceNotFoundException::new);
    }
}

package service;

import dto.invoice.InvoiceDto;

import java.util.List;

public interface InvoiceService {

    InvoiceDto getInvoiceByOrderId(Long orderId);

    List<InvoiceDto> getInvoicesByClientId(Long clientId);

}

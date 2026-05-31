package service;

import dto.invoice.InvoiceDto;

public interface InvoiceService {

    InvoiceDto getInvoiceByOrderId(Long orderId);

}

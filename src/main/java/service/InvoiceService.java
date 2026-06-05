package service;

import dto.invoice.InvoiceDto;

import java.io.IOException;
import java.util.List;

public interface InvoiceService {

    InvoiceDto getInvoiceByOrderId(Long orderId);

    List<InvoiceDto> getInvoicesByClientId(Long clientId);

    InvoiceDto saveInvoiceToFile(Long id) throws IOException;

}

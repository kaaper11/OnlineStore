package service.invoice;

import dto.invoice.InvoiceDto;

import java.io.IOException;
import java.time.ZoneId;
import java.util.List;

public interface InvoiceService {

    InvoiceDto getInvoiceByOrderId(Long orderId);

    List<InvoiceDto> getInvoicesByClientId(Long clientId, ZoneId zone);

    InvoiceDto saveInvoiceToFile(Long id) throws IOException;

}

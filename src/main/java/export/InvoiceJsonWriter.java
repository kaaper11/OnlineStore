package export;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import entity.invoice.Invoice;

import java.io.File;
import java.io.IOException;

public class InvoiceJsonWriter {

    public void saveInvoiceToJson(Invoice invoice, String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .findAndRegisterModules();

        mapper.writeValue(new File(path), invoice);
    }
}

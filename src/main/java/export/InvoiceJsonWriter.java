package export;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import entity.invoice.Invoice;

import java.io.File;
import java.io.IOException;

/**
 * Utility class responsible for exporting invoices to JSON format.
 * It uses Jackson ObjectMapper with JavaTimeModule support to properly
 * serialize date/time fields.
 */
public class InvoiceJsonWriter {

    /**
     * Saves the given invoice as a JSON file at the specified file path.
     * The method uses Jackson ObjectMapper configured with JavaTimeModule
     * to ensure proper serialization of Java time objects.
     *
     * @param invoice the invoice object to be serialized and saved
     * @param path    the file system path where the JSON file will be written
     * @throws IOException if an I/O error occurs during file writing
     */
    public void saveInvoiceToJson(Invoice invoice, String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .findAndRegisterModules();

        mapper.writeValue(new File(path), invoice);
    }
}

package service.invoice;

import dto.invoice.InvoiceDto;
import entity.client.Address;
import entity.client.Client;
import entity.client.Role;
import entity.invoice.Invoice;
import exception.InvoiceNotFoundException;
import export.InvoiceJsonWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.InvoiceRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class InvoiceServiceImplTestIT {

    private InvoiceService invoiceService;
    private InvoiceRepository invoiceRepository;

    private Invoice invoice;

    @BeforeEach
    public void setUp() {
        invoiceRepository = new InvoiceRepository();
        InvoiceJsonWriter invoiceJsonWriter = new InvoiceJsonWriter();

        invoiceService = new InvoiceServiceImpl(invoiceRepository, invoiceJsonWriter);

        Address address = new Address("Polska", "Wwa", "Zlota", "17-873", 10);
        Client client = new Client(1L, "Kacper", "kacper40@wp.pl", "Cos123%dd", "123456789", address, Role.USER);

        invoice = new Invoice(1L, 1L, client, List.of(), BigDecimal.TEN, ZonedDateTime.now());
    }

    @Test
    void shouldGetInvoiceByOrderId() {
        invoiceRepository.save(invoice);

        InvoiceDto response = invoiceService.getInvoiceByOrderId(1L);

        assertThat(response).isNotNull();
        assertThat(response.orderId()).isEqualTo(1L);
    }

    @Test
    void shouldGetInvoicesByClientId() {
        invoiceRepository.save(invoice);

        List<InvoiceDto> invoices = invoiceService.getInvoicesByClientId(1L, ZoneId.systemDefault());

        assertThat(invoices).isNotNull();
        assertThat(invoices.size()).isEqualTo(1);
    }

    @Test
    void shouldSaveInvoiceToFile() throws IOException {
        invoiceRepository.save(invoice);

        InvoiceDto response = invoiceService.saveInvoiceToFile(1L);

        assertThat(response).isNotNull();
        assertThat(response.orderId()).isEqualTo(1L);
    }

    @Test
    void shouldThrowWhenInvoiceNotFoundByOrderId() {
        assertThatExceptionOfType(InvoiceNotFoundException.class)
                .isThrownBy(() -> invoiceService.getInvoiceByOrderId(100L));
    }

    @Test
    void shouldThrowWhenInvoiceNotFoundWhenSavingToFile() {
        assertThatExceptionOfType(InvoiceNotFoundException.class)
                .isThrownBy(() -> invoiceService.saveInvoiceToFile(100L));
    }
}
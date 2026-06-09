import cli.CommandLineMain;
import export.InvoiceJsonWriter;
import repository.*;
import repository.productrepositories.ComputerRepository;
import repository.productrepositories.ElectronicsRepository;
import repository.productrepositories.SmartphoneRepository;
import service.impl.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        CartRepository cartRepository = new CartRepository();
        ClientRepository clientRepository = new ClientRepository();
        ComputerRepository computerRepository = new ComputerRepository();
        ElectronicsRepository electronicsRepository = new ElectronicsRepository();
        InvoiceRepository invoiceRepository = new InvoiceRepository();
        OrderRepository orderRepository = new OrderRepository();
        SmartphoneRepository smartphoneRepository = new SmartphoneRepository();
        DiscountRepository discountRepository = new DiscountRepository();

        InvoiceJsonWriter invoiceJsonWriter = new InvoiceJsonWriter();

        DiscountServiceImpl discountService = new DiscountServiceImpl(discountRepository, computerRepository,
                smartphoneRepository, electronicsRepository, clientRepository);
        CartServiceImpl cartService = new CartServiceImpl(cartRepository, computerRepository, smartphoneRepository,
                electronicsRepository);
        ClientServiceImpl clientService = new ClientServiceImpl(clientRepository, cartRepository);
        ComputerServiceImpl computerService = new ComputerServiceImpl(computerRepository, clientRepository);
        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl(invoiceRepository, invoiceJsonWriter);
        OrderServiceImpl orderService = new OrderServiceImpl(orderRepository, cartRepository, clientRepository,
                invoiceRepository, discountService);
        ElectronicsServiceImpl electronicsService = new ElectronicsServiceImpl(electronicsRepository, clientRepository);
        SmartphoneServiceImpl smartphoneService = new SmartphoneServiceImpl(smartphoneRepository, clientRepository);
        ProductFacadeServiceImpl productFacadeServiceImpl = new ProductFacadeServiceImpl(List.of(computerService, electronicsService,
                smartphoneService));


        CommandLineMain cli = new CommandLineMain(cartService, clientService, invoiceService, orderService,
                productFacadeServiceImpl, discountService);

        cli.start();
    }
}

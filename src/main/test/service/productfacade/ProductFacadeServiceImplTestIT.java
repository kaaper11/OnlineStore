package service.productfacade;

import dto.product.response.ComputerResponseDto;
import dto.product.response.ElectronicsResponseDto;
import dto.product.response.ProductResponseDto;
import dto.product.response.SmartphoneResponseDto;
import entity.client.Address;
import entity.client.Client;
import entity.client.Role;
import entity.product.type.Computer;
import entity.product.type.Smartphone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ClientRepository;
import repository.productrepositories.ComputerRepository;
import repository.productrepositories.ElectronicsRepository;
import repository.productrepositories.SmartphoneRepository;
import service.computer.ComputerServiceImpl;
import service.electronics.ElectronicsServiceImpl;
import service.product.ProductService;


import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductFacadeServiceImplTestIT{

    private ProductFacadeService productFacadeService;

    @BeforeEach
    public void setUp() {
        ComputerRepository computerRepository = new ComputerRepository();
        SmartphoneRepository smartphoneRepository = new SmartphoneRepository();
        ElectronicsRepository electronicsRepository = new ElectronicsRepository();
        ClientRepository clientRepository = new ClientRepository();

        ProductService<ComputerResponseDto> computerService = new ComputerServiceImpl(computerRepository, clientRepository);
        ProductService<SmartphoneResponseDto> smartphoneService = new service.smartphone.SmartphoneServiceImpl(smartphoneRepository, clientRepository);
        ProductService<ElectronicsResponseDto> electronicsService = new ElectronicsServiceImpl(electronicsRepository, clientRepository);

        productFacadeService = new ProductFacadeServiceImpl(List.of(computerService, smartphoneService,
                electronicsService));

        Address address = new Address("Polska", "Wwa", "Zlota", "17-873", 10);
        Client client = new Client(1L, "Kacper", "kacper40@wp.pl", "Cos123%dd", "123456789",
                address, Role.ADMIN);
        clientRepository.save(client);

        computerRepository.save(new Computer(1L, "name", new BigDecimal("100"), 10));
        smartphoneRepository.save(new Smartphone(10L, "name", new BigDecimal("100"), 10));
    }

    @Test
    void shouldGetBaseProducts() {
        List<ProductResponseDto> all = productFacadeService.getBaseProducts();

        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(2);
    }
}
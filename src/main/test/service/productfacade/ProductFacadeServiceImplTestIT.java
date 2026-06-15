package service.productfacade;

import dto.product.request.ComputerRequestDto;
import dto.product.request.ElectronicsRequestDto;
import dto.product.request.ProductRequestDto;
import dto.product.request.SmartphoneRequestDto;
import dto.product.response.ComputerResponseDto;
import dto.product.response.ElectronicsResponseDto;
import dto.product.response.ProductResponseDto;
import dto.product.response.SmartphoneResponseDto;
import entity.client.Address;
import entity.client.Client;
import entity.client.Role;
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
    }

    @Test
    void shouldCreateComputerProduct() {
        ProductRequestDto dto = new ComputerRequestDto("PC", BigDecimal.TEN, 10);

        ProductResponseDto result = productFacadeService.createProduct(dto, 1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldCreateSmartphoneProduct() {
        ProductRequestDto dto = new SmartphoneRequestDto("Phone", BigDecimal.TEN, 10);

        ProductResponseDto result = productFacadeService.createProduct(dto, 1L);

        assertThat(result).isNotNull();
    }

    @Test
    void shouldCreateElectronicsProduct() {
        ProductRequestDto dto = new ElectronicsRequestDto("TV", BigDecimal.TEN, 10);

        ProductResponseDto result = productFacadeService.createProduct(dto, 1L);

        assertThat(result).isNotNull();
    }

    @Test
    void shouldGetAllProducts() {
        productFacadeService.createProduct(new ComputerRequestDto("PC", BigDecimal.TEN, 10), 1L);
        productFacadeService.createProduct(new SmartphoneRequestDto("Phone", BigDecimal.TEN, 10), 1L);

        List<ProductResponseDto> all = productFacadeService.getAllProducts();

        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(2);
    }
}
package service.discount;

import dto.discount.DiscountDto;
import dto.discount.DiscountRequest;
import entity.client.Address;
import entity.client.Client;
import entity.client.Role;
import entity.discount.Discount;
import entity.discount.DiscountType;
import entity.product.config.computer.GraphicCard;
import entity.product.config.computer.Processor;
import entity.product.config.computer.Ram;
import entity.product.config.computer.Rom;
import entity.product.type.Computer;
import exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ClientRepository;
import repository.DiscountRepository;
import repository.productrepositories.ComputerRepository;
import repository.productrepositories.ElectronicsRepository;
import repository.productrepositories.SmartphoneRepository;
import service.computer.ComputerServiceImpl;
import service.electronics.ElectronicsServiceImpl;
import service.productfacade.ProductFacadeServiceImpl;
import service.smartphone.SmartphoneServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class DiscountServiceImplTestIT {

    private DiscountService discountService;

    private DiscountRepository discountRepository;

    private Computer computer;

    @BeforeEach
    void setUp() {
        discountRepository = new DiscountRepository();
        ComputerRepository computerRepository = new ComputerRepository();
        SmartphoneRepository smartphoneRepository = new SmartphoneRepository();
        ElectronicsRepository electronicsRepository = new ElectronicsRepository();
        ClientRepository clientRepository = new ClientRepository();

        ComputerServiceImpl computerService = new ComputerServiceImpl(computerRepository, clientRepository);
        SmartphoneServiceImpl smartphoneService = new SmartphoneServiceImpl(smartphoneRepository, clientRepository);
        ElectronicsServiceImpl electronicsService = new ElectronicsServiceImpl(electronicsRepository, clientRepository);
        ProductFacadeServiceImpl productFacadeService = new ProductFacadeServiceImpl(List.of(
                smartphoneService, computerService, electronicsService
        ));

        discountService = new DiscountServiceImpl(discountRepository, clientRepository, productFacadeService);

        Address address = new Address("Polska", "Warszawa", "Zlota", "17-873", 10);

        Client admin = new Client(100L, "Admin", "admin@test.pl", "Admin123%",
                "123456789", address, Role.ADMIN);

        Client user = new Client(1L, "User", "user@test.pl", "User123%",
                "987654321", address, Role.USER);

        clientRepository.save(admin);
        clientRepository.save(user);

        computer = new Computer(1L, "Komputer", new BigDecimal("1000"), 1,
                Processor.INTEL_CORE_I5, Ram.GB8, Rom.GB500, GraphicCard.RTX5050);

        computerRepository.save(computer);
    }

    @Test
    void shouldAddPercentDiscount() {
        DiscountRequest request = new DiscountRequest(1L, DiscountType.PERCENT, new BigDecimal("20"));

        DiscountDto dto = discountService.addDiscount("computer", request, 100L);

        assertThat(dto).isNotNull();
        assertThat(dto.productId()).isEqualTo(1L);
    }

    @Test
    void shouldCalculatePercentDiscount() {
        Discount discount = new Discount(1L, 1L, DiscountType.PERCENT, new BigDecimal("20"));

        discountRepository.save(discount);

        BigDecimal result = discountService.calculateDiscount(computer);

        assertThat(result).isEqualByComparingTo("1200");
    }

    @Test
    void shouldCalculateConstantDiscount() {
        Discount discount = new Discount(1L, 1L, DiscountType.CONSTANT, new BigDecimal("100"));

        discountRepository.save(discount);

        BigDecimal result = discountService.calculateDiscount(computer);

        assertThat(result).isEqualByComparingTo("1400");
    }

    @Test
    void shouldReturnOriginalPriceWhenDiscountDoesNotExist() {
        BigDecimal result = discountService.calculateDiscount(computer);

        assertThat(result).isEqualByComparingTo("1500");
    }

    @Test
    void shouldGetDiscountForProduct() {
        Discount discount = new Discount(1L, 1L, DiscountType.PERCENT, new BigDecimal("20"));

        discountRepository.save(discount);

        Optional<DiscountDto> dto = discountService.getDiscountForProduct(1L);

        assertThat(dto).isNotNull();
        assertThat(dto.get().productId()).isEqualTo(1L);
    }

    @Test
    void shouldCalculateTotalCart() {
        Discount discount = new Discount(1L, 1L, DiscountType.PERCENT, new BigDecimal("20"));

        discountRepository.save(discount);

        BigDecimal total = discountService.calculateTotalCart(List.of(computer));

        assertThat(total).isEqualByComparingTo("1200");
    }

    @Test
    void shouldThrowWhenClientNotFound() {
        DiscountRequest request = new DiscountRequest(1L, DiscountType.PERCENT, new BigDecimal("20"));

        assertThatExceptionOfType(ClientNotFoundException.class)
                .isThrownBy(() -> discountService.addDiscount("computer", request, 999L))
                .withMessage("Brak klienta w repozytorium.");
    }

    @Test
    void shouldThrowWhenUserHasNoPermission() {
        DiscountRequest request = new DiscountRequest(1L, DiscountType.PERCENT, new BigDecimal("20"));

        assertThatExceptionOfType(NoPermissionsException.class)
                .isThrownBy(() -> discountService.addDiscount("computer", request, 1L));
    }

    @Test
    void shouldThrowWhenDiscountAlreadyExists() {
        Discount discount = new Discount(1L, 1L, DiscountType.PERCENT, new BigDecimal("20"));

        discountRepository.save(discount);

        DiscountRequest request = new DiscountRequest(1L, DiscountType.PERCENT, new BigDecimal("10"));

        assertThatExceptionOfType(DiscountForProductAlreadyExists.class)
                .isThrownBy(() -> discountService.addDiscount("computer", request, 100L));
    }

    @Test
    void shouldThrowWhenProductNotFound() {
        DiscountRequest request = new DiscountRequest(999L, DiscountType.PERCENT, new BigDecimal("20"));

        assertThatExceptionOfType(ProductNotFoundException.class)
                .isThrownBy(() -> discountService.addDiscount("computer", request, 100L));
    }
}
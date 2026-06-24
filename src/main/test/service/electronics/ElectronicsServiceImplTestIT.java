package service.electronics;

import dto.product.request.ElectronicsRequestDto;
import dto.product.request.ProductRequestDto;
import dto.product.response.ElectronicsResponseDto;
import entity.client.Address;
import entity.client.Client;
import entity.client.Role;
import entity.product.type.Electronics;
import exception.ClientNotFoundException;
import exception.ElectronicsNotFoundException;
import exception.NoPermissionsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ClientRepository;
import repository.productrepositories.ElectronicsRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class ElectronicsServiceImplTestIT {

    private ElectronicsService electronicsService;
    private ElectronicsRepository electronicsRepository;

    private Electronics electronics;
    private ProductRequestDto productRequestDto;

    @BeforeEach
    public void setUp() {
        electronicsRepository = new ElectronicsRepository();
        ClientRepository clientRepository = new ClientRepository();

        electronicsService = new ElectronicsServiceImpl(electronicsRepository, clientRepository);

        electronics = new Electronics(1L, "name", new BigDecimal("100"), 20);

        Address address = new Address("Polska", "Wwa", "Zlota", "17-873", 10);
        Client client = new Client(1L, "Kacper", "kacper40@wp.pl", "Cos123%dd", "123456789",
                address, Role.USER);
        clientRepository.save(client);

        productRequestDto = new ElectronicsRequestDto("name", BigDecimal.TEN, 20);
    }

    @Test
    void shouldCreateElectronics() {
        ElectronicsResponseDto electronicsResponseDto = electronicsService.create(productRequestDto, 100L);

        assertThat(electronicsResponseDto).isNotNull();
        assertThat(electronicsResponseDto.getId()).isEqualTo(1L);
    }

    @Test
    void shouldRemoveElectronics() {
        electronicsRepository.save(electronics);

        electronicsService.remove(1L, 100L);

        assertThat(electronicsRepository.getAllElectronics().size()).isEqualTo(0);
    }

    @Test
    void shouldUpdateElectronicsPrice() {
        electronicsRepository.save(electronics);

        ElectronicsResponseDto updatePrice = electronicsService.updatePrice(1L, 100L,
                new BigDecimal("100"));

        assertThat(updatePrice).isNotNull();
        assertThat(updatePrice.getPrice()).isEqualTo(new BigDecimal("100"));
    }

    @Test
    void shouldUpdateElectronicsQuantity() {
        electronicsRepository.save(electronics);

        ElectronicsResponseDto updateQuantity = electronicsService.updateQuantity(1L, 100L, 1000);

        assertThat(updateQuantity).isNotNull();
        assertThat(updateQuantity.getQuantity()).isEqualTo(1000);
    }

    @Test
    void shouldGetElectronicsById() {
        electronicsRepository.save(electronics);

        ElectronicsResponseDto response = electronicsService.getById(1L);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
    }

    @Test
    void shouldElectronicsExists() {
        electronicsRepository.save(electronics);

        boolean result = electronicsService.exist(1L);

        assertThat(result).isTrue();
    }

    @Test
    void shouldElectronicsNotExists() {
        boolean result = electronicsService.exist(1L);

        assertThat(result).isFalse();
    }

    @Test
    void shouldGetAllElectronics() {
        electronicsRepository.save(electronics);

        List<ElectronicsResponseDto> all = electronicsService.getAll();

        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    void shouldThrowWhenClientDoesNotExist() {
        assertThatExceptionOfType(ClientNotFoundException.class)
                .isThrownBy(() -> electronicsService.create(productRequestDto, 7L));
    }

    @Test
    void shouldThrowWhenClientDoesNotHavePermission() {
        assertThatExceptionOfType(NoPermissionsException.class)
                .isThrownBy(() -> electronicsService.create(productRequestDto, 1L));
    }

    @Test
    void shouldThrowWhenElectronicsNotFound() {
        assertThatExceptionOfType(ElectronicsNotFoundException.class)
                .isThrownBy(() -> electronicsService.remove(100L, 100L));
    }
}
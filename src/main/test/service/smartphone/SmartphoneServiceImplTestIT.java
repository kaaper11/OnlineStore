package service.smartphone;

import dto.product.request.ProductRequestDto;
import dto.product.request.SmartphoneRequestDto;
import dto.product.response.SmartphoneResponseDto;
import entity.client.Address;
import entity.client.Client;
import entity.client.Role;
import entity.product.type.Smartphone;
import exception.ClientNotFoundException;
import exception.NoPermissionsException;
import exception.SmartphoneNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ClientRepository;
import repository.productrepositories.SmartphoneRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class SmartphoneServiceImplTestIT {

    private SmartphoneService smartphoneService;
    private SmartphoneRepository smartphoneRepository;

    private Smartphone smartphone;
    private ProductRequestDto productRequestDto;

    @BeforeEach
    public void setUp() {
        smartphoneRepository = new SmartphoneRepository();
        ClientRepository clientRepository = new ClientRepository();

        smartphoneService = new SmartphoneServiceImpl(smartphoneRepository, clientRepository);

        smartphone = new Smartphone(1L, "iPhone", new BigDecimal("1000"), 10);

        Address address = new Address("Polska", "Wwa", "Zlota", "17-873", 10);

        Client admin = new Client(1L, "Admin", "admin@test.pl", "Pass123%", "123456789",
                address, Role.ADMIN);
        Client user = new Client(2L, "User", "user@test.pl", "Pass123%", "987654321", address,
                Role.USER);

        clientRepository.save(admin);
        clientRepository.save(user);

        productRequestDto = new SmartphoneRequestDto("iPhone", BigDecimal.TEN, 10);
    }

    @Test
    void shouldCreateSmartphone() {
        SmartphoneResponseDto result = smartphoneService.create(productRequestDto, 1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldRemoveSmartphone() {
        smartphoneRepository.save(smartphone);

        smartphoneService.remove(1L, 1L);

        assertThat(smartphoneRepository.getAllSmartphones().size()).isEqualTo(0);
    }

    @Test
    void shouldUpdateSmartphonePrice() {
        smartphoneRepository.save(smartphone);

        SmartphoneResponseDto result = smartphoneService.updatePrice(1L, 1L, new BigDecimal("2000"));

        assertThat(result).isNotNull();
        assertThat(result.getPrice()).isEqualTo(new BigDecimal("2000"));
    }

    @Test
    void shouldUpdateSmartphoneQuantity() {
        smartphoneRepository.save(smartphone);

        SmartphoneResponseDto result = smartphoneService.updateQuantity(1L, 1L, 50);

        assertThat(result).isNotNull();
        assertThat(result.getQuantity()).isEqualTo(50);
    }

    @Test
    void shouldGetSmartphoneById() {
        smartphoneRepository.save(smartphone);

        SmartphoneResponseDto result = smartphoneService.getById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldGetAllSmartphones() {
        smartphoneRepository.save(smartphone);

        List<SmartphoneResponseDto> all = smartphoneService.getAll();

        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    void shouldCheckExist() {
        smartphoneRepository.save(smartphone);

        boolean result = smartphoneService.exist(1L);

        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseWhenNotExist() {
        boolean result = smartphoneService.exist(1L);

        assertThat(result).isFalse();
    }

    @Test
    void shouldThrowWhenClientNotFound() {
        assertThatExceptionOfType(ClientNotFoundException.class)
                .isThrownBy(() -> smartphoneService.create(productRequestDto, 999L));
    }

    @Test
    void shouldThrowWhenNoPermissions() {
        assertThatExceptionOfType(NoPermissionsException.class)
                .isThrownBy(() -> smartphoneService.create(productRequestDto, 2L));
    }

    @Test
    void shouldThrowWhenSmartphoneNotFound() {
        assertThatExceptionOfType(SmartphoneNotFoundException.class)
                .isThrownBy(() -> smartphoneService.remove(999L, 1L));
    }
}
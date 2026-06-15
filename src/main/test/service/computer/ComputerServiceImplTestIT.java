package service.computer;

import dto.product.request.ComputerRequestDto;
import dto.product.request.ProductRequestDto;
import dto.product.response.ComputerResponseDto;
import entity.client.Address;
import entity.client.Client;
import entity.client.Role;
import entity.product.type.Computer;
import exception.ClientNotFoundException;
import exception.ComputerNotFoundException;
import exception.NoPermissionsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ClientRepository;
import repository.productrepositories.ComputerRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class ComputerServiceImplTestIT {

    private ComputerService computerService;
    private ComputerRepository computerRepository;

    private Computer computer;
    private ProductRequestDto productRequestDto;


    @BeforeEach
    public void setUp() {
        computerRepository = new ComputerRepository();
        ClientRepository clientRepository = new ClientRepository();

        computerService = new ComputerServiceImpl(computerRepository, clientRepository);

        computer = new Computer(1L, "name", new BigDecimal("100"), 20);

        Address address = new Address("Polska", "Wwa", "Zlota", "17-873", 10);
        Client client = new Client(1L, "Kacper", "kacper40@wp.pl",
                "Cos123%dd", "123456789", address, Role.USER);
        clientRepository.save(client);

        productRequestDto = new ComputerRequestDto("name", BigDecimal.TEN, 20);
    }

    @Test
    void shouldCreateComputer() {
        ComputerResponseDto computerResponseDto = computerService.create(productRequestDto, 100L);

        assertThat(computerResponseDto).isNotNull();
        assertThat(computerResponseDto.getId()).isEqualTo(1L);
    }

    @Test
    void shouldRemoveComputer() {
        computerRepository.save(computer);

        ComputerResponseDto remove = computerService.remove(1L, 100L);

        assertThat(remove).isNotNull();
        assertThat(remove.getName()).isEqualTo(computer.getName());
    }

    @Test
    void shouldUpdateComputerPrice() {
        computerRepository.save(computer);

        ComputerResponseDto updatePrice = computerService.updatePrice(1L, 100L, new BigDecimal("100"));
        assertThat(updatePrice).isNotNull();
        assertThat(updatePrice.getPrice()).isEqualTo(new BigDecimal("100"));
    }

    @Test
    void shouldUpdateComputerQuantity() {
        computerRepository.save(computer);

        ComputerResponseDto updateQuantity = computerService.updateQuantity(1L, 100L, 1000);
        assertThat(updateQuantity).isNotNull();
        assertThat(updateQuantity.getQuantity()).isEqualTo(1000);
    }

    @Test
    void shouldGetComputerById() {
        computerRepository.save(computer);

        ComputerResponseDto response = computerService.getById(1L);
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
    }

    @Test
    void shouldComputerExists() {
        computerRepository.save(computer);

        boolean result = computerService.exist(1L);
        assertThat(result).isTrue();
    }

    @Test
    void shouldComputerNotExists() {
        boolean result = computerService.exist(1L);
        assertThat(result).isFalse();
    }

    @Test
    void shouldGetAllComputers() {
        computerRepository.save(computer);

        List<ComputerResponseDto> all = computerService.getAll();

        assertThat(all).isNotNull();
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    void shouldThrowWhenClientDoesNotExist() {
        assertThatExceptionOfType(ClientNotFoundException.class)
                .isThrownBy(() -> computerService.create(productRequestDto, 7L));
    }

    @Test
    void shouldThrowWhenClientDoesNotHavePermission() {
        assertThatExceptionOfType(NoPermissionsException.class)
                .isThrownBy(() -> computerService.create(productRequestDto, 1L));
    }

    @Test
    void shouldThrowWhenComputerNotFound() {
        assertThatExceptionOfType(ComputerNotFoundException.class)
                .isThrownBy(() -> computerService.remove(100L, 100L));
    }
}

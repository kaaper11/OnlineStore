package service.computer;

import dto.product.request.ComputerRequestDto;
import dto.product.response.ComputerResponseDto;
import entity.client.Address;
import entity.client.Client;
import entity.client.Role;
import entity.product.config.computer.GraphicCard;
import entity.product.config.computer.Processor;
import entity.product.config.computer.Ram;
import entity.product.config.computer.Rom;
import entity.product.type.Computer;
import exception.ComputerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.ClientRepository;
import repository.productrepositories.ComputerRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ComputerServiceImplTest {

    @Mock
    private ComputerRepository computerRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ComputerServiceImpl computerService;

    private static final ComputerRequestDto COMPUTER_REQUEST_DTO = new ComputerRequestDto("name", new BigDecimal("100"),
            20);

    private static final Computer COMPUTER = new Computer(1L, "name", new BigDecimal("100"), 20,
            Processor.INTEL_CORE_I3, Ram.GB8, Rom.GB500, GraphicCard.RTX5050);

    private static final Address ADDRESS = new Address("Poland", "Warsaw", "Zlota", "15-820",
            2);

    private static final Client CLIENT = new Client(1L, "name", "name@test.com", "pasS12%dd",
            "123456789", ADDRESS, Role.ADMIN);


    @Test
    void shouldCreateComputer() {
        //given
        when(computerRepository.save(any(Computer.class))).thenReturn(COMPUTER);
        when(clientRepository.getClientById(anyLong())).thenReturn(Optional.of(CLIENT));

        //when
        ComputerResponseDto dto = computerService.create(COMPUTER_REQUEST_DTO, 1L);

        assertNotNull(dto);
        verify(computerRepository).save(any(Computer.class));
        assertThat(dto.getName()).isEqualTo(COMPUTER_REQUEST_DTO.getName());
    }

    @Test
    void shouldRemoveComputer() {
        //given
        when(computerRepository.delete(anyLong())).thenReturn(Optional.of(COMPUTER));
        when(clientRepository.getClientById(anyLong())).thenReturn(Optional.of(CLIENT));

        //when
        computerService.remove(1L, 1L);

        //then
        verify(computerRepository).delete(1L);
    }

    @Test
    void shouldThrowWhenRemoveAndComputerNotFound() {
        //given
        when(computerRepository.delete(anyLong())).thenReturn(Optional.empty());
        when(clientRepository.getClientById(anyLong())).thenReturn(Optional.of(CLIENT));

        //then
        assertThatExceptionOfType(ComputerNotFoundException.class)
                .isThrownBy(() -> computerService.remove(1L, 1L));
    }

    @Test
    void shouldGetCorrectComputerById() {
        //given
        when(computerRepository.getComputerById(anyLong())).thenReturn(Optional.of(COMPUTER));

        //when
        ComputerResponseDto dto = computerService.getById(1L);

        //then
        assertNotNull(dto);
        assertThat(dto).usingRecursiveComparison().isEqualTo(COMPUTER);
    }

    @Test
    void shouldThrowWhenGetComputerByIdNotFound() {
        //given
        when(computerRepository.getComputerById(anyLong())).thenReturn(Optional.empty());

        //then
        assertThatExceptionOfType(ComputerNotFoundException.class)
            .isThrownBy(() -> computerService.getById(1L));
    }

    @Test
    void shouldGetAllComputers() {
        //given
        when(computerRepository.getAllComputers()).thenReturn(List.of(COMPUTER));

        //when
        List<ComputerResponseDto> dtos = computerService.getAll();

        //then
        assertThat(dtos).hasSize(1);
        assertThat(dtos.getFirst().getName()).isEqualTo(COMPUTER.getName());
    }

    @Test
    void shouldComputerExists() {
        when(computerRepository.getComputerById(anyLong())).thenReturn(Optional.of(COMPUTER));

        boolean result = computerService.exist(COMPUTER.getId());
        assertThat(result).isTrue();
    }

    @Test
    void shouldComputerNotExists() {
        when(computerRepository.getComputerById(anyLong())).thenReturn(Optional.of(COMPUTER));

        boolean result = computerService.exist(10L);
        assertThat(result).isTrue();
    }
}

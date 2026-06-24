package service.electronics;

import dto.product.request.ElectronicsRequestDto;
import dto.product.response.ElectronicsResponseDto;
import entity.client.Address;
import entity.client.Client;
import entity.client.Role;
import entity.product.type.Electronics;
import exception.ElectronicsNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.ClientRepository;
import repository.productrepositories.ElectronicsRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ElectronicsServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ElectronicsRepository electronicsRepository;

    @InjectMocks
    private ElectronicsServiceImpl electronicsService;

    private static final ElectronicsRequestDto ELECTRONICS_REQUEST_DTO = new ElectronicsRequestDto("name", new BigDecimal("100"), 20);

    private static final Electronics ELECTRONICS = new Electronics(1L, "name", new BigDecimal("100"), 20);

    private static final Address ADDRESS = new Address("Poland", "Warsaw", "Zlota", "15-820",
            2);

    private static final Client CLIENT = new Client(1L, "name", "name@test.com", "pasS12%dd",
            "123456789", ADDRESS, Role.ADMIN);

    @Test
    void shouldCreateElectronics() {
        // given
        when(electronicsRepository.save(any(Electronics.class))).thenReturn(ELECTRONICS);
        when(clientRepository.getClientById(anyLong())).thenReturn(Optional.of(CLIENT));

        // when
        ElectronicsResponseDto dto = electronicsService.create(ELECTRONICS_REQUEST_DTO, 1L);

        // then
        assertNotNull(dto);
        verify(electronicsRepository).save(any(Electronics.class));
        assertThat(dto).usingRecursiveComparison().ignoringFields("id")
                .isEqualTo(ELECTRONICS_REQUEST_DTO);
    }

    @Test
    void shouldRemoveElectronics() {
        // given
        when(electronicsRepository.delete(anyLong())).thenReturn(Optional.of(ELECTRONICS));
        when(clientRepository.getClientById(anyLong())).thenReturn(Optional.of(CLIENT));

        // when
        electronicsService.remove(1L, 1L);

        // then
        verify(electronicsRepository).delete(1L);
    }

    @Test
    void shouldThrowWhenRemoveAndElectronicsNotFound() {
        // given
        when(electronicsRepository.delete(anyLong())).thenReturn(Optional.empty());
        when(clientRepository.getClientById(anyLong())).thenReturn(Optional.of(CLIENT));


        // then
        assertThatExceptionOfType(ElectronicsNotFoundException.class)
                .isThrownBy(() -> electronicsService.remove(1L, 1L));
    }

    @Test
    void shouldGetCorrectElectronicsById() {
        // given
        when(electronicsRepository.getElectronicsById(anyLong()))
                .thenReturn(Optional.of(ELECTRONICS));

        // when
        ElectronicsResponseDto dto = electronicsService.getById(1L);

        // then
        assertNotNull(dto);
        assertThat(dto).usingRecursiveComparison().ignoringFields("id").isEqualTo(ELECTRONICS_REQUEST_DTO);
    }

    @Test
    void shouldThrowWhenGetElectronicsByIdNotFound() {
        // given
        when(electronicsRepository.getElectronicsById(anyLong()))
                .thenReturn(Optional.empty());

        // then
        assertThatExceptionOfType(ElectronicsNotFoundException.class)
                .isThrownBy(() -> electronicsService.getById(1L));
    }

    @Test
    void shouldGetAllElectronics() {
        // given
        when(electronicsRepository.getAllElectronics())
                .thenReturn(List.of(ELECTRONICS));

        // when
        List<ElectronicsResponseDto> dtos = electronicsService.getAll();

        // then
        assertThat(dtos).hasSize(1);
        assertThat(dtos.getFirst())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(ELECTRONICS_REQUEST_DTO);
    }

    @Test
    void shouldElectronicsExists() {
        when(electronicsRepository.getElectronicsById(anyLong())).thenReturn(Optional.of(ELECTRONICS));

        boolean result = electronicsService.exist(ELECTRONICS.getId());
        assertThat(result).isTrue();
    }

    @Test
    void shouldElectronicsNotExists() {
        when(electronicsRepository.getElectronicsById(anyLong())).thenReturn(Optional.of(ELECTRONICS));

        boolean result = electronicsService.exist(10L);
        assertThat(result).isTrue();
    }
}
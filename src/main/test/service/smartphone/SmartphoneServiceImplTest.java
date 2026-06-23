package service.smartphone;

import dto.product.request.SmartphoneRequestDto;
import dto.product.response.SmartphoneResponseDto;
import entity.client.Address;
import entity.client.Client;
import entity.client.Role;
import entity.product.config.smartphone.Battery;
import entity.product.config.smartphone.SmartphoneColorType;
import entity.product.type.Smartphone;
import exception.SmartphoneNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.ClientRepository;
import repository.productrepositories.SmartphoneRepository;

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
public class SmartphoneServiceImplTest {

    @Mock
    private SmartphoneRepository smartphoneRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private SmartphoneServiceImpl smartphoneService;

    private final SmartphoneRequestDto smartphoneDto = new SmartphoneRequestDto("name", new BigDecimal("100"),
            20);

    private final Smartphone smartphone = new Smartphone(1L, "name", new BigDecimal("100"), 20,
            SmartphoneColorType.BLACK, Battery.MAH5500);

    private final Address address = new Address("Poland", "Warsaw", "Zlota", "15-820",
            2);

    private final Client client = new Client(1L, "name", "name@test.com", "pasS12%dd",
            "123456789", address, Role.ADMIN);


    @Test
    void shouldCreateSmartphone() {
        // given
        when(smartphoneRepository.save(any(Smartphone.class))).thenReturn(smartphone);
        when(clientRepository.getClientById(anyLong())).thenReturn(Optional.of(client));

        // when
        SmartphoneResponseDto dto = smartphoneService.create(smartphoneDto, 1L);

        // then
        assertNotNull(dto);
        verify(smartphoneRepository).save(any(Smartphone.class));
        assertThat(dto.getName()).isEqualTo(smartphone.getName());
    }

    @Test
    void shouldRemoveSmartphone() {
        // given
        when(smartphoneRepository.delete(anyLong())).thenReturn(Optional.of(smartphone));
        when(clientRepository.getClientById(anyLong())).thenReturn(Optional.of(client));

        // when
        smartphoneService.remove(1L, 1L);

        // then
        verify(smartphoneRepository).delete(1L);
    }

    @Test
    void shouldThrowWhenRemoveAndSmartphoneNotFound() {
        // given
        when(smartphoneRepository.delete(anyLong())).thenReturn(Optional.empty());
        when(clientRepository.getClientById(anyLong())).thenReturn(Optional.of(client));

        // then
        assertThatExceptionOfType(SmartphoneNotFoundException.class)
                .isThrownBy(() -> smartphoneService.remove(1L, 1L));
    }

    @Test
    void shouldGetCorrectSmartphoneById() {
        // given
        when(smartphoneRepository.getSmartphoneById(anyLong()))
                .thenReturn(Optional.of(smartphone));

        // when
        SmartphoneResponseDto dto = smartphoneService.getById(1L);

        // then
        assertNotNull(dto);
        assertThat(dto.getName()).isEqualTo(smartphone.getName());
    }

    @Test
    void shouldThrowWhenGetSmartphoneByIdNotFound() {
        // given
        when(smartphoneRepository.getSmartphoneById(anyLong()))
                .thenReturn(Optional.empty());

        // then
        assertThatExceptionOfType(SmartphoneNotFoundException.class)
                .isThrownBy(() -> smartphoneService.getById(1L));
    }

    @Test
    void shouldGetAllSmartphones() {
        // given
        when(smartphoneRepository.getAllSmartphones())
                .thenReturn(List.of(smartphone));

        // when
        List<SmartphoneResponseDto> dtos = smartphoneService.getAll();

        // then
        assertThat(dtos).hasSize(1);
        assertThat(dtos.getFirst().getName()).isEqualTo(smartphoneDto.getName());
    }

    @Test
    void shouldSmartphoneExists() {
        when(smartphoneRepository.getSmartphoneById(anyLong())).thenReturn(Optional.of(smartphone));

        boolean result = smartphoneService.exist(smartphone.getId());
        assertThat(result).isTrue();
    }

    @Test
    void shouldSmartphoneNotExists() {
        when(smartphoneRepository.getSmartphoneById(anyLong())).thenReturn(Optional.of(smartphone));

        boolean result = smartphoneService.exist(10L);
        assertThat(result).isTrue();
    }
}

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

    private static final SmartphoneRequestDto SMARTPHONE_DTO = new SmartphoneRequestDto("name", new BigDecimal("100"),
            20);

    private static final Smartphone SMARTPHONE = new Smartphone(1L, "name", new BigDecimal("100"), 20,
            SmartphoneColorType.BLACK, Battery.MAH5500);

    private static final Address ADDRESS = new Address("Poland", "Warsaw", "Zlota", "15-820",
            2);

    private static final Client CLIENT = new Client(1L, "name", "name@test.com", "pasS12%dd",
            "123456789", ADDRESS, Role.ADMIN);


    @Test
    void shouldCreateSmartphone() {
        // given
        when(smartphoneRepository.save(any(Smartphone.class))).thenReturn(SMARTPHONE);
        when(clientRepository.getClientById(anyLong())).thenReturn(Optional.of(CLIENT));

        // when
        SmartphoneResponseDto dto = smartphoneService.create(SMARTPHONE_DTO, 1L);

        // then
        assertNotNull(dto);
        verify(smartphoneRepository).save(any(Smartphone.class));
        assertThat(dto.getName()).isEqualTo(SMARTPHONE.getName());
    }

    @Test
    void shouldRemoveSmartphone() {
        // given
        when(smartphoneRepository.delete(anyLong())).thenReturn(Optional.of(SMARTPHONE));
        when(clientRepository.getClientById(anyLong())).thenReturn(Optional.of(CLIENT));

        // when
        smartphoneService.remove(1L, 1L);

        // then
        verify(smartphoneRepository).delete(1L);
    }

    @Test
    void shouldThrowWhenRemoveAndSmartphoneNotFound() {
        // given
        when(smartphoneRepository.delete(anyLong())).thenReturn(Optional.empty());
        when(clientRepository.getClientById(anyLong())).thenReturn(Optional.of(CLIENT));

        // then
        assertThatExceptionOfType(SmartphoneNotFoundException.class)
                .isThrownBy(() -> smartphoneService.remove(1L, 1L));
    }

    @Test
    void shouldGetCorrectSmartphoneById() {
        // given
        when(smartphoneRepository.getSmartphoneById(anyLong()))
                .thenReturn(Optional.of(SMARTPHONE));

        // when
        SmartphoneResponseDto dto = smartphoneService.getById(1L);

        // then
        assertNotNull(dto);
        assertThat(dto.getName()).isEqualTo(SMARTPHONE.getName());
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
                .thenReturn(List.of(SMARTPHONE));

        // when
        List<SmartphoneResponseDto> dtos = smartphoneService.getAll();

        // then
        assertThat(dtos).hasSize(1);
        assertThat(dtos.getFirst().getName()).isEqualTo(SMARTPHONE_DTO.getName());
    }

    @Test
    void shouldSmartphoneExists() {
        when(smartphoneRepository.getSmartphoneById(anyLong())).thenReturn(Optional.of(SMARTPHONE));

        boolean result = smartphoneService.exist(SMARTPHONE.getId());
        assertThat(result).isTrue();
    }

    @Test
    void shouldSmartphoneNotExists() {
        when(smartphoneRepository.getSmartphoneById(anyLong())).thenReturn(Optional.of(SMARTPHONE));

        boolean result = smartphoneService.exist(10L);
        assertThat(result).isTrue();
    }
}

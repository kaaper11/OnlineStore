package service;

import dto.product.SmartphoneDto;
import entity.product.config.smartphone.Battery;
import entity.product.config.smartphone.SmartphoneColorType;
import entity.product.type.Smartphone;
import exception.SmartphoneNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.SmartphoneRepository;
import service.impl.SmartphoneServiceImpl;

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

    @InjectMocks
    private SmartphoneServiceImpl smartphoneService;

    private final SmartphoneDto smartphoneDto = new SmartphoneDto("name", new BigDecimal("100"), 20,
            SmartphoneColorType.BLACK, Battery.MAH5500);

    private final Smartphone smartphone = new Smartphone(1L, "name", new BigDecimal("100"), 20,
            SmartphoneColorType.BLACK, Battery.MAH5500);

    @Test
    void shouldCreateSmartphone() {
        // given
        when(smartphoneRepository.getNextId()).thenReturn(1L);
        when(smartphoneRepository.save(any(Smartphone.class))).thenReturn(smartphone);

        // when
        SmartphoneDto dto = smartphoneService.create(smartphoneDto);

        // then
        assertNotNull(dto);
        verify(smartphoneRepository).save(any(Smartphone.class));
        assertThat(dto).usingRecursiveComparison().isEqualTo(smartphoneDto);
    }

    @Test
    void shouldRemoveSmartphone() {
        // given
        when(smartphoneRepository.delete(anyLong())).thenReturn(Optional.of(smartphone));

        // when
        SmartphoneDto dto = smartphoneService.remove(1L);

        // then
        assertNotNull(dto);
        verify(smartphoneRepository).delete(1L);
    }

    @Test
    void shouldThrowWhenRemoveAndSmartphoneNotFound() {
        // given
        when(smartphoneRepository.delete(anyLong())).thenReturn(Optional.empty());

        // then
        assertThatExceptionOfType(SmartphoneNotFoundException.class)
                .isThrownBy(() -> smartphoneService.remove(1L));
    }

    @Test
    void shouldUpdateSmartphone() {
        // given
        when(smartphoneRepository.update(anyLong(), any(Smartphone.class)))
                .thenReturn(Optional.of(smartphone));

        // when
        SmartphoneDto dto = smartphoneService.update(1L, smartphoneDto);

        // then
        assertNotNull(dto);
        verify(smartphoneRepository).update(anyLong(), any(Smartphone.class));
        assertThat(dto).usingRecursiveComparison().isEqualTo(smartphoneDto);
    }

    @Test
    void shouldThrowWhenUpdateAndSmartphoneNotFound() {
        // given
        when(smartphoneRepository.update(anyLong(), any(Smartphone.class)))
                .thenReturn(Optional.empty());

        // then
        assertThatExceptionOfType(SmartphoneNotFoundException.class)
                .isThrownBy(() -> smartphoneService.update(1L, smartphoneDto));
    }

    @Test
    void shouldGetCorrectSmartphoneById() {
        // given
        when(smartphoneRepository.getSmartphoneById(anyLong()))
                .thenReturn(Optional.of(smartphone));

        // when
        SmartphoneDto dto = smartphoneService.getById(1L);

        // then
        assertNotNull(dto);
        assertThat(dto).usingRecursiveComparison().isEqualTo(smartphoneDto);
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
        List<SmartphoneDto> dtos = smartphoneService.getAll();

        // then
        assertThat(dtos).hasSize(1);
        assertThat(dtos.getFirst())
                .usingRecursiveComparison()
                .isEqualTo(smartphoneDto);
    }
}

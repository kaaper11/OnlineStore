package service;

import dto.product.request.ElectronicsRequestDto;
import dto.product.response.ElectronicsResponseDto;
import entity.product.type.Electronics;
import exception.ElectronicsNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.ElectronicsRepository;
import service.impl.ElectronicsServiceImpl;
import utils.ProductIdGenerator;

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
    private ElectronicsRepository electronicsRepository;

    @Mock
    private ProductIdGenerator productIdGenerator;

    @InjectMocks
    private ElectronicsServiceImpl electronicsService;

    private final ElectronicsRequestDto electronicsRequestDto = new ElectronicsRequestDto("name", new BigDecimal("100"), 20);

    private final Electronics electronics = new Electronics(1L, "name", new BigDecimal("100"), 20);

    @Test
    void shouldCreateElectronics() {
        // given
        when(productIdGenerator.getNextProductId()).thenReturn(1L);
        when(electronicsRepository.save(any(Electronics.class))).thenReturn(electronics);

        // when
        ElectronicsResponseDto dto = electronicsService.create(electronicsRequestDto);

        // then
        assertNotNull(dto);
        verify(electronicsRepository).save(any(Electronics.class));
        assertThat(dto).usingRecursiveComparison().ignoringFields("id")
                .isEqualTo(electronicsRequestDto);
    }

    @Test
    void shouldRemoveElectronics() {
        // given
        when(electronicsRepository.delete(anyLong())).thenReturn(Optional.of(electronics));

        // when
        ElectronicsResponseDto dto = electronicsService.remove(1L);

        // then
        assertNotNull(dto);
        verify(electronicsRepository).delete(1L);
    }

    @Test
    void shouldThrowWhenRemoveAndElectronicsNotFound() {
        // given
        when(electronicsRepository.delete(anyLong())).thenReturn(Optional.empty());

        // then
        assertThatExceptionOfType(ElectronicsNotFoundException.class)
                .isThrownBy(() -> electronicsService.remove(1L));
    }

    @Test
    void shouldUpdateElectronics() {
        // given
        when(electronicsRepository.update(anyLong(), any(Electronics.class)))
                .thenReturn(Optional.of(electronics));

        // when
        ElectronicsResponseDto dto = electronicsService.update(1L, electronicsRequestDto);

        // then
        assertNotNull(dto);
        verify(electronicsRepository).update(anyLong(), any(Electronics.class));
        assertThat(dto).usingRecursiveComparison().ignoringFields("id")
                .isEqualTo(electronicsRequestDto);
    }

    @Test
    void shouldThrowWhenUpdateAndElectronicsNotFound() {
        // given
        when(electronicsRepository.update(anyLong(), any(Electronics.class)))
                .thenReturn(Optional.empty());

        // then
        assertThatExceptionOfType(ElectronicsNotFoundException.class)
                .isThrownBy(() -> electronicsService.update(1L, electronicsRequestDto));
    }

    @Test
    void shouldGetCorrectElectronicsById() {
        // given
        when(electronicsRepository.getElectronicsById(anyLong()))
                .thenReturn(Optional.of(electronics));

        // when
        ElectronicsResponseDto dto = electronicsService.getById(1L);

        // then
        assertNotNull(dto);
        assertThat(dto).usingRecursiveComparison().ignoringFields("id").isEqualTo(electronicsRequestDto);
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
                .thenReturn(List.of(electronics));

        // when
        List<ElectronicsResponseDto> dtos = electronicsService.getAll();

        // then
        assertThat(dtos).hasSize(1);
        assertThat(dtos.getFirst())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(electronicsRequestDto);
    }
}
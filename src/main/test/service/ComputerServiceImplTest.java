package service;

import dto.product.request.ComputerRequestDto;
import dto.product.response.ComputerResponseDto;
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
import repository.ComputerRepository;
import service.impl.ComputerServiceImpl;
import utils.ProductIdGenerator;

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
    private ProductIdGenerator productIdGenerator;

    @InjectMocks
    private ComputerServiceImpl computerService;

    private final ComputerRequestDto computerRequestDto = new ComputerRequestDto("name", new BigDecimal("100"),
            20, Processor.INTEL_CORE_I3, Ram.GB8, Rom.GB500, GraphicCard.RTX5050);

    private final Computer computer = new Computer(1L, "name", new BigDecimal("100"), 20,
            Processor.INTEL_CORE_I3, Ram.GB8, Rom.GB500, GraphicCard.RTX5050);


    @Test
    void shouldCreateComputer() {
        //given
        when(productIdGenerator.getNextProductId()).thenReturn(1L);
        when(computerRepository.save(any(Computer.class))).thenReturn(computer);

        //when
        ComputerResponseDto dto = computerService.create(computerRequestDto);

        assertNotNull(dto);
        verify(computerRepository).save(any(Computer.class));
        assertThat(dto.getName()).isEqualTo(computerRequestDto.getName());
    }

    @Test
    void shouldRemoveComputer() {
        //given
        when(computerRepository.delete(anyLong())).thenReturn(Optional.of(computer));

        //when
        ComputerResponseDto dto = computerService.remove(1L);

        //then
        assertNotNull(dto);
        verify(computerRepository).delete(1L);
    }

    @Test
    void shouldThrowWhenRemoveAndComputerNotFound() {
        //given
        when(computerRepository.delete(anyLong())).thenReturn(Optional.empty());

        //then
        assertThatExceptionOfType(ComputerNotFoundException.class)
                .isThrownBy(() -> computerService.remove(1L));
    }

    @Test
    void shouldUpdateComputer() {
        when(computerRepository.update(anyLong(), any(Computer.class))).thenReturn(Optional.of(computer));

        ComputerResponseDto dto = computerService.update(1L, computerRequestDto);

        assertNotNull(dto);
        verify(computerRepository).update(anyLong(), any(Computer.class));
        assertThat(dto).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(computerRequestDto);
    }

    @Test
    void shouldThrowWhenUpdateAndComputerNotFound() {
        //given
        when(computerRepository.update(anyLong(), any(Computer.class))).thenReturn(Optional.empty());

        //then
        assertThatExceptionOfType(ComputerNotFoundException.class)
                .isThrownBy(() -> computerService.update(1L, computerRequestDto));
    }

    @Test
    void shouldGetCorrectComputerById() {
        //given
        when(computerRepository.getComputerById(anyLong())).thenReturn(Optional.of(computer));

        //when
        ComputerResponseDto dto = computerService.getById(1L);

        //then
        assertNotNull(dto);
        assertThat(dto).usingRecursiveComparison().isEqualTo(computer);
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
        when(computerRepository.getAllComputers()).thenReturn(List.of(computer));

        //when
        List<ComputerResponseDto> dtos = computerService.getAll();

        //then
        assertThat(dtos).hasSize(1);
        assertThat(dtos.getFirst()).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(computerRequestDto);
    }
}

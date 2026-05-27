package repository;

import entity.product.config.computer.GraphicCard;
import entity.product.config.computer.Processor;
import entity.product.config.computer.Ram;
import entity.product.config.computer.Rom;
import entity.product.type.Computer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ComputerRepositoryTest {

    private final Computer computer = new Computer(1L, "name", new BigDecimal("100"), 20,
            Processor.INTEL_CORE_I3, Ram.GB8, Rom.GB500, GraphicCard.RTX5050);

    @InjectMocks
    private ComputerRepository computerRepository;

    @Test
    public void shouldSaveAndFindComputer() {
        Computer save = computerRepository.save(computer);

        assertThat(save).usingRecursiveComparison().isEqualTo(computer);
    }

    @Test
    public void shouldDeleteComputer() {
        computerRepository.save(computer);

        Optional<Computer> deleted = computerRepository.delete(1L);

        assertThat(deleted).isPresent();
        assertThat(computerRepository.getComputerById(1L)).isEmpty();
    }

    @Test
    public void shouldUpdateComputer() {
        computerRepository.save(computer);

        Computer update = new Computer(1L, "new", new BigDecimal("200"), 15,
                Processor.INTEL_CORE_I3, Ram.GB8, Rom.GB500, GraphicCard.RTX5050);

        Optional<Computer> result = computerRepository.update(1L, update);

        assertThat(result).isPresent();
        assertThat(computerRepository.getComputerById(1L)).isEqualTo(Optional.of(update));
    }


    @Test
    public void shouldFindComputerById() {
        computerRepository.save(computer);

        Optional<Computer> result = computerRepository.getComputerById(1L);

        assertThat(result).isPresent();
        assertThat(result).isEqualTo(Optional.of(computer));
    }

    @Test
    void shouldReturnEmptyWhenComputerNotFound() {
        ComputerRepository repository = new ComputerRepository();

        Optional<Computer> result = repository.getComputerById(999L);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnAllComputers() {
        ComputerRepository repository = new ComputerRepository();

        Computer c1 = new Computer(1L, "a", BigDecimal.ONE, 1,
                Processor.INTEL_CORE_I3, Ram.GB8, Rom.GB500, GraphicCard.RTX5050);

        Computer c2 = new Computer(2L, "b", BigDecimal.TEN, 2,
                Processor.INTEL_CORE_I5, Ram.GB16, Rom.GB500, GraphicCard.RTX5090);

        repository.save(c1);
        repository.save(c2);

        List<Computer> result = repository.getAllComputers();

        assertThat(result).hasSize(2);
    }
}

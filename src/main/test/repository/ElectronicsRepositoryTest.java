package repository;

import entity.product.type.Electronics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.productrepositories.ElectronicsRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ElectronicsRepositoryTest {

    private final Electronics electronics = new Electronics(1L, "name", new BigDecimal("100"), 20);

    @InjectMocks
    private ElectronicsRepository electronicsRepository;

    @Test
    public void shouldSaveAndFindElectronics() {
        Electronics save = electronicsRepository.save(electronics);

        assertThat(save).usingRecursiveComparison().isEqualTo(electronics);
    }

    @Test
    public void shouldDeleteElectronics() {
        electronicsRepository.save(electronics);

        Optional<Electronics> deleted = electronicsRepository.delete(1L);

        assertThat(deleted).isPresent();
        assertThat(electronicsRepository.getElectronicsById(1L)).isEmpty();
    }

    @Test
    public void shouldFindElectronicsById() {
        electronicsRepository.save(electronics);

        Optional<Electronics> result = electronicsRepository.getElectronicsById(1L);

        assertThat(result).isPresent();
        assertThat(result).isEqualTo(Optional.of(electronics));
    }

    @Test
    void shouldReturnEmptyWhenElectronicsNotFound() {
        ElectronicsRepository repository = new ElectronicsRepository();

        Optional<Electronics> result = repository.getElectronicsById(999L);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnAllElectronics() {
        ElectronicsRepository repository = new ElectronicsRepository();

        Electronics e1 = new Electronics(1L, "a", BigDecimal.ONE, 1);
        Electronics e2 = new Electronics(2L, "b", BigDecimal.TEN, 2);

        repository.save(e1);
        repository.save(e2);

        List<Electronics> result = repository.getAllElectronics();

        assertThat(result).hasSize(2);
        assertThat(result.getFirst()).isEqualTo(e1);
    }

    @Test
    void shouldUpdateComputerPrice() {
        electronicsRepository.save(electronics);

        Optional<Electronics> result = electronicsRepository.updatePrice(1L, BigDecimal.TEN);
        assertThat(result).isPresent();
        assertThat(electronicsRepository.getElectronicsById(1L).get().getPrice()).isEqualTo(BigDecimal.TEN);
    }

    @Test
    void shouldUpdateComputerQuantity() {
        electronicsRepository.save(electronics);

        Optional<Electronics> result = electronicsRepository.updateQuantity(1L, 100);
        assertThat(result).isPresent();
        assertThat(electronicsRepository.getElectronicsById(1L).get().getQuantity()).isEqualTo(100);
    }
}
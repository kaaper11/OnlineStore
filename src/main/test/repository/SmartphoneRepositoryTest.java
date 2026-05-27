package repository;

import entity.product.config.smartphone.Battery;
import entity.product.config.smartphone.SmartphoneColorType;
import entity.product.type.Smartphone;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class SmartphoneRepositoryTest {

    private final Smartphone smartphone = new Smartphone(1L, "name", new BigDecimal("100"), 20,
            SmartphoneColorType.BLACK, Battery.MAH5500);

    @InjectMocks
    private SmartphoneRepository smartphoneRepository;

    @Test
    public void shouldSaveAndFindSmartphone() {
        Smartphone save = smartphoneRepository.save(smartphone);

        assertThat(save).usingRecursiveComparison().isEqualTo(smartphone);
    }

    @Test
    public void shouldDeleteSmartphone() {
        smartphoneRepository.save(smartphone);

        Optional<Smartphone> deleted = smartphoneRepository.delete(1L);

        assertThat(deleted).isPresent();
        assertThat(smartphoneRepository.getSmartphoneById(1L)).isEmpty();
    }

    @Test
    public void shouldUpdateSmartphone() {
        smartphoneRepository.save(smartphone);

        Smartphone update = new Smartphone(1L, "new", new BigDecimal("200"), 15,
                SmartphoneColorType.GOLD, Battery.MAH5500);

        Optional<Smartphone> result = smartphoneRepository.update(1L, update);

        assertThat(result).isPresent();
        assertThat(smartphoneRepository.getSmartphoneById(1L))
                .isEqualTo(Optional.of(update));
    }

    @Test
    public void shouldFindSmartphoneById() {
        smartphoneRepository.save(smartphone);

        Optional<Smartphone> result = smartphoneRepository.getSmartphoneById(1L);

        assertThat(result).isPresent();
        assertThat(result).isEqualTo(Optional.of(smartphone));
    }

    @Test
    void shouldReturnEmptyWhenSmartphoneNotFound() {
        SmartphoneRepository repository = new SmartphoneRepository();

        Optional<Smartphone> result = repository.getSmartphoneById(999L);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnAllSmartphones() {
        SmartphoneRepository repository = new SmartphoneRepository();

        Smartphone s1 = new Smartphone(1L, "new1", new BigDecimal("200"), 15,
                SmartphoneColorType.GOLD, Battery.MAH5500);
        Smartphone s2 = new Smartphone(2L, "new2", new BigDecimal("200"), 15,
                SmartphoneColorType.GOLD, Battery.MAH5500);

        repository.save(s1);
        repository.save(s2);

        List<Smartphone> result = repository.getAllSmartphones();

        assertThat(result).hasSize(2);
    }
}

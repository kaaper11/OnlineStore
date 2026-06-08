package repository;

import entity.discount.Discount;
import entity.discount.DiscountType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DiscountRepositoryTest {

    @Test
    void shouldSaveAndRetrieveDiscountByProductId() {
        DiscountRepository repository = new DiscountRepository();

        Discount discount = new Discount(repository.getNextId(), 100L, DiscountType.PERCENT,
                new BigDecimal("15.5")
        );

        repository.save(discount);

        Optional<Discount> found = repository.getByProductId(100L);

        assertTrue(found.isPresent());
        assertEquals(100L, found.get().getProductId());
        assertEquals(DiscountType.PERCENT, found.get().getDiscountType());
        assertEquals(new BigDecimal("15.5"), found.get().getValue());
    }

    @Test
    void shouldReturnEmptyWhenDiscountDoesNotExist() {
        DiscountRepository repository = new DiscountRepository();

        Optional<Discount> found = repository.getByProductId(999L);

        assertTrue(found.isEmpty());
    }

    @Test
    void shouldDeleteExistingDiscount() {
        DiscountRepository repository = new DiscountRepository();

        Discount discount = new Discount(repository.getNextId(), 200L, DiscountType.PERCENT,
                new BigDecimal("15.5"));

        repository.save(discount);

        Optional<Discount> deleted = repository.delete(200L);

        assertTrue(deleted.isPresent());
        assertEquals(200L, deleted.get().getProductId());

        assertFalse(repository.exists(200L));
    }

    @Test
    void shouldReturnFalseWhenDiscountDoesNotExist() {
        DiscountRepository repository = new DiscountRepository();

        boolean exists = repository.exists(123L);

        assertFalse(exists);
    }
}
package repository;

import entity.discount.Discount;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class DiscountRepository {
    private final Set<Discount> discounts = ConcurrentHashMap.newKeySet();
    private final AtomicLong idCounter = new AtomicLong(1L);

    public Discount save(Discount discount) {
        discounts.add(discount);
        return discount;
    }

    public Optional<Discount> delete(Long productId) {
        return getByProductId(productId)
                .map(discount -> {
                    discounts.remove(discount);
                    return discount;
                });
    }

    public Optional<Discount> getByProductId(Long productId) {
        return discounts.stream()
                .filter(discount -> discount.getProductId().equals(productId))
                .findFirst();
    }

    public boolean exists(Long productId) {
        return getByProductId(productId).isPresent();
    }

    public Long getNextId() {
        return idCounter.getAndIncrement();
    }
}

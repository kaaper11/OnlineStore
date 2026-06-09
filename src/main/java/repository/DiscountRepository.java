package repository;

import entity.discount.Discount;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repository responsible for managing Discount entities in memory.
 * It provides operations for saving, retrieving, checking existence,
 * and deleting discounts using a thread-safe set.
 */
public class DiscountRepository {
    private final Set<Discount> discounts = ConcurrentHashMap.newKeySet();
    private final AtomicLong idCounter = new AtomicLong(1L);

    /**
     * Saves a discount entity into the repository.
     *
     * @param discount the discount entity to be stored
     * @return the saved discount instance
     */
    public Discount save(Discount discount) {
        discounts.add(discount);
        return discount;
    }

    /**
     * Deletes a discount associated with the given product identifier.
     * If a matching discount exists, it is removed from the repository.
     *
     * @param productId the identifier of the product whose discount should be deleted
     * @return an Optional containing the removed discount if found,
     * otherwise an empty Optional
     */
    public Optional<Discount> delete(Long productId) {
        return getByProductId(productId)
                .map(discount -> {
                    discounts.remove(discount);
                    return discount;
                });
    }

    /**
     * Retrieves a discount by the associated product identifier.
     *
     * @param productId the identifier of the product
     * @return an Optional containing the found discount or empty if not found
     */
    public Optional<Discount> getByProductId(Long productId) {
        return discounts.stream()
                .filter(discount -> discount.getProductId().equals(productId))
                .findFirst();
    }

    /**
     * Checks whether a discount exists for the given product identifier.
     *
     * @param productId the identifier of the product
     * @return true if a discount exists for the product, false otherwise
     */
    public boolean exists(Long productId) {
        return getByProductId(productId).isPresent();
    }

    public Long getNextId() {
        return idCounter.getAndIncrement();
    }
}

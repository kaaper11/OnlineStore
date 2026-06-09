package repository.productrepositories.idconfig;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility class responsible for generating unique identifiers for products.
 * It uses an AtomicLong counter to ensure thread-safe incremental ID generation.
 * This class is non-instantiable and provides a global ID generator for products.
 */
public final class ProductIdGenerator {
    private static final AtomicLong productId = new AtomicLong(1L);

    /**
     * Returns the next unique product identifier.
     * The ID is generated in a thread-safe manner using an internal AtomicLong counter.
     *
     * @return the next available product ID
     */
    public static Long getNextProductId() {
        return productId.getAndIncrement();
    }
}

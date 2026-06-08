package repository.productrepositories.idconfig;

import java.util.concurrent.atomic.AtomicLong;

public final class ProductIdGenerator {
    private static final AtomicLong productId = new AtomicLong(1L);

    public static Long getNextProductId() {
        return productId.getAndIncrement();
    }
}

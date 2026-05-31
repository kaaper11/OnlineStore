package utils;

public final class ProductIdGenerator {
    private Long productId = 0L;

    public Long getNextProductId() {
        return productId++;
    }
}

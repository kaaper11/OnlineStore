package repository.productrepositories.idconfig;

public final class ProductIdGenerator {
    private static Long productId = 1L;

    public static Long getNextProductId() {
        return productId++;
    }
}

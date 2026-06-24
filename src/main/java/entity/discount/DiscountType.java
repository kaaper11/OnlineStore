package entity.discount;

/**
 * Represents the type of discount applied to a product.
 * <p>
 * PERCENT - discount calculated as a percentage of the product price
 * CONSTANT - fixed amount subtracted from the product price
 */
public enum DiscountType {
    PERCENT, CONSTANT
}

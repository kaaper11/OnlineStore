package discountformatter;

import dto.discount.DiscountDto;
import entity.discount.DiscountType;

public class DiscountFormatter {

    public static String formatDiscount(DiscountDto discount) {
        return discount.discountType() == DiscountType.PERCENT ? " -" + discount.value() + "%"
                : " -" + discount.value() + "zł";
    }
}

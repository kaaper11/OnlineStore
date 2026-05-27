package dto.cart;

import java.util.List;

public record CartRequestDto(Long clientId, List<Long> productIds) {
}

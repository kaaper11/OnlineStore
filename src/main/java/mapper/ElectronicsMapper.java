package mapper;

import dto.product.request.ElectronicsRequestDto;
import dto.product.response.ElectronicsResponseDto;
import entity.product.type.Electronics;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility mapper class responsible for converting between Electronics entities
 * and Electronics DTO objects.
 * This class is non-instantiable and provides static methods for transforming
 * electronics data between request/response layers and the domain model.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ElectronicsMapper {

    public static Electronics mapDtoToElectronics(ElectronicsRequestDto dto) {
        return new Electronics(null, dto.getName(), dto.getPrice(), dto.getQuantity());
    }

    public static ElectronicsResponseDto mapElectronicsToDto(Electronics electronics) {
        return new ElectronicsResponseDto(electronics.getId(), electronics.getName(), electronics.getPrice(),
                electronics.getQuantity());
    }
}

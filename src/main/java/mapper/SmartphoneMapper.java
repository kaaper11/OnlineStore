package mapper;

import dto.product.request.SmartphoneRequestDto;
import dto.product.response.SmartphoneResponseDto;
import entity.product.type.Smartphone;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility mapper class responsible for converting between Smartphone entities
 * and Smartphone DTO objects.
 * This class is non-instantiable and provides static methods for transforming
 * smartphone data between request/response layers and the domain model.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SmartphoneMapper {

    public static Smartphone mapDtoToSmartphone(SmartphoneRequestDto smartphoneDto, Long id) {
        return new Smartphone(id, smartphoneDto.getName(), smartphoneDto.getPrice(), smartphoneDto.getQuantity(),
                smartphoneDto.getColor(), smartphoneDto.getBattery());
    }

    public static SmartphoneResponseDto mapSmartphoneToDto(Smartphone smartphone) {
        return new SmartphoneResponseDto(smartphone.getId(), smartphone.getName(), smartphone.getPrice(),
                smartphone.getQuantity(), smartphone.getColor(), smartphone.getBattery(), smartphone.getAccessoryList());
    }
}

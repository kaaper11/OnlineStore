package mapper;

import dto.product.request.SmartphoneRequestDto;
import dto.product.response.SmartphoneResponseDto;
import entity.product.type.Smartphone;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SmartphoneMapper {
//
//    public static SmartphoneRequestDto mapSmartphoneToDto(Smartphone smartphone) {
//        return new SmartphoneRequestDto(smartphone.getName(), smartphone.getPrice(), smartphone.getQuantity(),
//                smartphone.getColor(), smartphone.getBattery());
//    }

    public static Smartphone mapDtoToSmartphone(SmartphoneRequestDto smartphoneDto, Long id) {
        return new Smartphone(id, smartphoneDto.getName(), smartphoneDto.getPrice(), smartphoneDto.getQuantity(),
                smartphoneDto.getColor(), smartphoneDto.getBattery());
    }

    public static SmartphoneResponseDto mapSmartphoneToDto(Smartphone smartphone) {
        return new SmartphoneResponseDto(smartphone.getId(), smartphone.getName(), smartphone.getPrice(),
                smartphone.getQuantity(), smartphone.getColor(), smartphone.getBattery());
    }
}

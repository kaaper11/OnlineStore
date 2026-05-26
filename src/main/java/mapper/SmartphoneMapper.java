package mapper;

import dto.type.SmartphoneDto;
import entity.product.type.Smartphone;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SmartphoneMapper {

    public static SmartphoneDto mapSmartphoneToDto(Smartphone smartphone) {
        return new SmartphoneDto(smartphone.getName(), smartphone.getPrice(), smartphone.getQuantity(),
                smartphone.getColor(), smartphone.getBattery());
    }

    public static Smartphone mapDtoToSmartphone(SmartphoneDto smartphoneDto, Long id) {
        return new Smartphone(id, smartphoneDto.getName(), smartphoneDto.getPrice(), smartphoneDto.getQuantity(),
                smartphoneDto.getColor(), smartphoneDto.getBattery());
    }
}

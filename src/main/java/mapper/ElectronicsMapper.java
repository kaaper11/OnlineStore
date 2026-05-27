package mapper;

import dto.product.ElectronicsDto;
import entity.product.type.Electronics;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ElectronicsMapper {

    public static ElectronicsDto mapElectronicsToDto(Electronics electronics) {
        return new ElectronicsDto(electronics.getName(), electronics.getPrice(), electronics.getQuantity());
    }

    public static Electronics mapDtoToElectronics(ElectronicsDto dto, Long id) {
        return new Electronics(id, dto.getName(), dto.getPrice(), dto.getQuantity());
    }
}

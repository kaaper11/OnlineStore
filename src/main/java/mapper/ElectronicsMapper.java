package mapper;

import dto.product.request.ElectronicsRequestDto;
import dto.product.response.ElectronicsResponseDto;
import entity.product.type.Electronics;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ElectronicsMapper {

    public static Electronics mapDtoToElectronics(ElectronicsRequestDto dto, Long id) {
        return new Electronics(id, dto.getName(), dto.getPrice(), dto.getQuantity());
    }

    public static ElectronicsResponseDto mapElectronicsToDto(Electronics electronics) {
        return new ElectronicsResponseDto(electronics.getId(), electronics.getName(), electronics.getPrice(),
                electronics.getQuantity());
    }
}

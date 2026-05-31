package mapper;

import dto.product.request.ComputerRequestDto;
import dto.product.response.ComputerResponseDto;
import entity.product.type.Computer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ComputerMapper {

    public static Computer mapDtoToComputer(ComputerRequestDto dto, Long id) {
        return new Computer(id, dto.getName(), dto.getPrice(), dto.getQuantity(), dto.getProcessor(), dto.getRam(),
                dto.getRom(), dto.getGraphicCard());
    }

    public static ComputerResponseDto mapComputerToDto(Computer computer) {
        return new ComputerResponseDto(computer.getId(), computer.getName(), computer.getPrice(),
                computer.getQuantity(), computer.getProcessor(), computer.getRam(), computer.getRom(), computer.getGraphicCard());
    }
}

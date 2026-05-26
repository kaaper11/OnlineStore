package mapper;

import dto.type.ComputerDto;
import entity.product.type.Computer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ComputerMapper {

    public static ComputerDto mapComputerToDto(Computer computer) {
        return new ComputerDto(computer.getName(), computer.getPrice(), computer.getQuantity(), computer.getProcessor(),
                computer.getRam(), computer.getRom(), computer.getGraphicCard());
    }

    public static Computer mapDtoToComputer(ComputerDto dto, Long id) {
        return new Computer(id, dto.getName(), dto.getPrice(), dto.getQuantity(), dto.getProcessor(), dto.getRam(),
                dto.getRom(), dto.getGraphicCard());
    }
}

package mapper;

import dto.product.request.ComputerRequestDto;
import dto.product.response.ComputerResponseDto;
import entity.product.config.computer.GraphicCard;
import entity.product.config.computer.Processor;
import entity.product.config.computer.Ram;
import entity.product.config.computer.Rom;
import entity.product.type.Computer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility mapper class responsible for converting between Computer entities
 * and Computer DTO objects.
 * This class is non-instantiable and provides static methods for transforming
 * computer data between request/response layers and the domain model.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ComputerMapper {

    public static Computer mapDtoToComputer(ComputerRequestDto dto, Long id) {
        return new Computer(id, dto.getName(), dto.getPrice(), dto.getQuantity(), Processor.INTEL_CORE_I3, Ram.GB8,
                Rom.GB500 , GraphicCard.RTX5050);
    }

    public static ComputerResponseDto mapComputerToDto(Computer computer) {
        return new ComputerResponseDto(computer.getId(), computer.getName(), computer.getPrice(),
                computer.getQuantity(), computer.getProcessor(), computer.getRam(), computer.getRom(),
                computer.getGraphicCard());
    }
}

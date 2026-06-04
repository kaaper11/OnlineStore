package dto.product.request;

import entity.product.config.computer.GraphicCard;
import entity.product.config.computer.Processor;
import entity.product.config.computer.Ram;
import entity.product.config.computer.Rom;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Getter
public class ComputerRequestDto extends ProductRequestDto {
    private final Processor processor = Processor.INTEL_CORE_I3;

    private final Ram ram = Ram.GB8;

    private final Rom rom = Rom.GB500;

    private final GraphicCard graphicCard = GraphicCard.RTX5050;

    public ComputerRequestDto(String name, BigDecimal price, int quantity) {
        super(name, price, quantity);
    }
}

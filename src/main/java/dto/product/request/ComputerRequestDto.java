package dto.product.request;

import entity.product.config.computer.GraphicCard;
import entity.product.config.computer.Processor;
import entity.product.config.computer.Ram;
import entity.product.config.computer.Rom;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ComputerRequestDto extends ProductRequestDto {
    private final Processor processor;
    private final Ram ram;
    private final Rom rom;
    private final GraphicCard graphicCard;

    public ComputerRequestDto(String name, BigDecimal price, int quantity, Processor processor, Ram ram, Rom rom,
                              GraphicCard graphicCard) {
        super(name, price, quantity);
        this.processor = processor;
        this.ram = ram;
        this.rom = rom;
        this.graphicCard = graphicCard;
    }
}

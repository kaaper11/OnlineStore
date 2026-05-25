package dto.type;

import dto.base.ProductDto;
import entity.component.computerConfig.GraphicCard;
import entity.component.computerConfig.Processor;
import entity.component.computerConfig.Ram;
import entity.component.computerConfig.Rom;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ComputerDto extends ProductDto {
    private final Processor processor;
    private final Ram ram;
    private final Rom rom;
    private final GraphicCard graphicCard;

    public ComputerDto(String name, BigDecimal price, int quantity, Processor processor, Ram ram, Rom rom, GraphicCard graphicCard) {
        super(name, price, quantity);
        this.processor = processor;
        this.ram = ram;
        this.rom = rom;
        this.graphicCard = graphicCard;
    }
}

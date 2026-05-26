package entity.product.type;


import entity.component.computerConfig.GraphicCard;
import entity.component.computerConfig.Processor;
import entity.component.computerConfig.Ram;
import entity.component.computerConfig.Rom;
import entity.product.Configurable;
import entity.product.base.Product;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Computer extends Product implements Configurable {
    private Processor processor;
    private Ram ram;
    private Rom rom;
    private GraphicCard graphicCard;

    public Computer(Long id, String name, BigDecimal price, int quantity, Processor processor, Ram ram, Rom rom,
                    GraphicCard graphicCard) {
        super(id, name, price, quantity);
        this.processor = processor;
        this.ram = ram;
        this.rom = rom;
        this.graphicCard = graphicCard;
    }

    public Computer configureComputer(Processor processor, Ram ram, Rom rom, GraphicCard graphicCard) {
        this.processor = processor;
        this.ram = ram;
        this.rom = rom;
        this.graphicCard = graphicCard;

        return this;
    }

    @Override
    public boolean isConfigured() {
        return processor != null && ram != null && rom != null && graphicCard != null;
    }
}

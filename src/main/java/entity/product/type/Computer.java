package entity.product.type;


import entity.product.config.computer.GraphicCard;
import entity.product.config.computer.Processor;
import entity.product.config.computer.Ram;
import entity.product.config.computer.Rom;
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

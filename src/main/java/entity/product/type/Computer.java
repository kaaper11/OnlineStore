package entity.product.type;


import entity.product.Configurable;
import entity.product.base.Product;
import entity.component.computerConfig.GraphicCard;
import entity.component.computerConfig.Processor;
import entity.component.computerConfig.Ram;
import entity.component.computerConfig.Rom;

import java.math.BigDecimal;

public class Computer extends Product implements Configurable {
    private Processor processor;
    private Ram ram;
    private Rom rom;
    private GraphicCard graphicCard;

    public Computer(long id, String name, BigDecimal price, int quantity) {
        super(id, name, price, quantity);
    }

    public void configureComputer(Processor processor, Ram ram, Rom rom, GraphicCard graphicCard) {
        this.processor = processor;
        this.ram = ram;
        this.rom = rom;
        this.graphicCard = graphicCard;
    }

    @Override
    public boolean isConfigured() {
        return processor != null && ram != null && rom != null && graphicCard != null;
    }
}

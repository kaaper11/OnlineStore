package entity.product.type;


import entity.product.config.computer.GraphicCard;
import entity.product.config.computer.Processor;
import entity.product.config.computer.Ram;
import entity.product.config.computer.Rom;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Getter
public class Computer extends Product {
    private Processor processor;
    private Ram ram;
    private Rom rom;
    private GraphicCard graphicCard;

    public Computer(Long id, String name, BigDecimal price, int quantity) {
        super(id, name, price, quantity);
    }

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
    public BigDecimal getTotalPrice() {
        return getPrice().add(processor.getPrice()).add(ram.getPrice()).add(rom.getPrice()).add(graphicCard.getPrice());
    }

    @Override
    public Product getProductCopy() {
        return new Computer(getId(), getName(), getPrice(), getQuantity());
    }
}

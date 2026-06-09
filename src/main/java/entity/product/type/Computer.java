package entity.product.type;


import entity.product.config.computer.GraphicCard;
import entity.product.config.computer.Processor;
import entity.product.config.computer.Ram;
import entity.product.config.computer.Rom;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Represents a computer product composed of configurable hardware components
 * such as processor, RAM, ROM, and graphics card.
 * A computer extends the base Product class and calculates its total price
 * based on its selected configuration.
 */
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

    /**
     * Configures the computer by assigning hardware components.
     *
     * @param processor the processor to be assigned to the computer
     * @param ram the RAM module to be assigned to the computer
     * @param rom the storage unit to be assigned to the computer
     * @param graphicCard the graphics card to be assigned to the computer
     * @return the updated Computer instance with applied configuration
     */
    public Computer configureComputer(Processor processor, Ram ram, Rom rom, GraphicCard graphicCard) {
        this.processor = processor;
        this.ram = ram;
        this.rom = rom;
        this.graphicCard = graphicCard;

        return this;
    }

    /**
     * Calculates the total price of the computer, including all selected components.
     * The final price is computed as the sum of the base product price and
     * the prices of the processor, RAM, ROM, and graphics card.
     *
     * @return the total price of the configured computer
     */
    @Override
    public BigDecimal getTotalPrice() {
        return getPrice().add(processor.getPrice()).add(ram.getPrice()).add(rom.getPrice()).add(graphicCard.getPrice());
    }

    @Override
    public Product getProductCopy() {
        return new Computer(getId(), getName(), getPrice(), getQuantity());
    }
}

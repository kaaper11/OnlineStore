package dto.productconfig;

import entity.product.config.computer.GraphicCard;
import entity.product.config.computer.Processor;
import entity.product.config.computer.Ram;
import entity.product.config.computer.Rom;
import entity.product.type.Computer;
import entity.product.type.Product;

public record ComputerConfig(Processor processor, Ram ram, Rom rom, GraphicCard graphicCard) implements ProductConfig {

    @Override
    public void configure(Product product) {
        Computer computer = (Computer) product;
        computer.configureComputer(processor, ram, rom, graphicCard);
    }
}

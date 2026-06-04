package dto.product.response;

import entity.product.config.computer.GraphicCard;
import entity.product.config.computer.Processor;
import entity.product.config.computer.Ram;
import entity.product.config.computer.Rom;

import java.math.BigDecimal;

public class ComputerResponseDto extends ProductResponseDto {
    private final Processor processor;
    private final Ram ram;
    private final Rom rom;
    private final GraphicCard graphicCard;

    public ComputerResponseDto(Long id, String name, BigDecimal price, int quantity, Processor processor, Ram ram,
                               Rom rom, GraphicCard graphicCard) {
        super(id, name, price, quantity);
        this.processor = processor;
        this.ram = ram;
        this.rom = rom;
        this.graphicCard = graphicCard;
    }

    @Override
    public String toString() {
        return processor != null && ram != null && rom != null && graphicCard != null ? "Computer: (" + "nazwa: "
                + getName() + ", cena: " + getPrice() + ", dostępność: " + getQuantity() + ", procesor: " + processor +
                ", ilość ram: " + ram + ", ilość rom: " + rom + ", karta graficzna: " + graphicCard +")"
                : "Computer: (" + "nazwa:" + getName() + ", cena:" + getPrice() + ", dostępność"
                + getQuantity() + ")";
    }
}

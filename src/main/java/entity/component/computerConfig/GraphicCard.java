package entity.component.computerConfig;

import entity.product.base.NamedComponent;

import java.math.BigDecimal;

public class GraphicCard extends NamedComponent {
    private int vram;

    public GraphicCard(BigDecimal price, String name, int vram) {
        super(price, name);
        this.vram = vram;
    }
}

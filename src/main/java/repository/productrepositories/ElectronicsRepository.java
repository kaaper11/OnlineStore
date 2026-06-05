package repository.productrepositories;

import entity.product.type.Electronics;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ElectronicsRepository {
    private final Set<Electronics> electronicsSet = new HashSet<>();

    public Electronics save(Electronics electronics) {
        electronicsSet.add(electronics);
        return electronics;
    }

    public Optional<Electronics> delete(Long id) {
        return getElectronicsById(id)
                .map(electronics -> {
                    electronicsSet.remove(electronics);
                    return electronics;
                });
    }

    public Optional<Electronics> getElectronicsById(Long id) {
        return electronicsSet.stream()
                .filter(electronics -> electronics.getId().equals(id))
                .findFirst();
    }

    public List<Electronics> getAllElectronics() {
        return electronicsSet.stream()
                .toList();
    }

    public Optional<Electronics> updatePrice(Long id, BigDecimal price) {
        return getElectronicsById(id)
                .map(electronics -> {
                    electronics.setPrice(price);
                    return electronics;
                });
    }

    public Optional<Electronics> updateQuantity(Long id, Integer quantity) {
        return getElectronicsById(id)
                .map(electronics -> {
                    electronics.setQuantity(quantity);
                    return electronics;
                });
    }
}

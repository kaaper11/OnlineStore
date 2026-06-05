package repository.productrepositories;

import entity.product.type.Smartphone;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SmartphoneRepository {
    private final Set<Smartphone> smartphones = new HashSet<>();

    public Smartphone save(Smartphone smartphone) {
        smartphones.add(smartphone);
        return smartphone;
    }

    public Optional<Smartphone> delete(Long id) {
        return getSmartphoneById(id)
                .map(smartphone -> {
                    smartphones.remove(smartphone);
                    return smartphone;
                });
    }

    public Optional<Smartphone> getSmartphoneById(Long id) {
        return smartphones.stream()
                .filter(smartphone -> smartphone.getId().equals(id))
                .findFirst();
    }

    public List<Smartphone> getAllSmartphones() {
        return smartphones.stream()
                .toList();
    }

    public Optional<Smartphone> updateSmartphonePrice(Long id, BigDecimal price) {
        return getSmartphoneById(id)
                .map(smartphone -> {
                    smartphone.setPrice(price);
                    return smartphone;
                });
    }

    public Optional<Smartphone> updateSmartphoneQuantity(Long id, int quantity) {
        return getSmartphoneById(id)
                .map(smartphone -> {
                    smartphone.setQuantity(quantity);
                    return smartphone;
                });
    }
}

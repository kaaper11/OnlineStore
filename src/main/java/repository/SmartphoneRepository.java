package repository;

import entity.product.type.Smartphone;

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

    public Optional<Smartphone> update(Long id, Smartphone updatedSmartphone) {
        return getSmartphoneById(id)
                .map(smartphone -> {
                    smartphones.remove(smartphone);
                    smartphones.add(updatedSmartphone);
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
}

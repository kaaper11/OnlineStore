package repository;

import entity.product.type.Electronics;

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

    public Optional<Electronics> update(Long id, Electronics updatedElectronics) {
        return getElectronicsById(id)
                .map(electronics -> {
                    electronicsSet.remove(electronics);
                    electronicsSet.add(updatedElectronics);
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
}

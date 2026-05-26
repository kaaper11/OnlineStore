package service;

import dto.type.ElectronicsDto;

import java.util.List;

public interface ElectronicsService {
    ElectronicsDto createElectronics(ElectronicsDto electronicsDto);

    ElectronicsDto removeElectronics(Long id);

    ElectronicsDto updateElectronics(Long id, ElectronicsDto electronicsDto);

    ElectronicsDto getElectronicsById(Long id);

    List<ElectronicsDto> getAllElectronics();
}

package service.impl;

import dto.type.ElectronicsDto;
import entity.product.type.Electronics;
import exception.ElectronicsNotFoundException;
import lombok.RequiredArgsConstructor;
import mapper.ElectronicsMapper;
import repository.ElectronicsRepository;
import service.ElectronicsService;

import java.util.List;

@RequiredArgsConstructor
public class ElectronicsServiceImpl implements ElectronicsService {
    private final ElectronicsRepository electronicsRepository;

    @Override
    public ElectronicsDto createElectronics(ElectronicsDto electronicsDto) {
        Electronics electronics = electronicsRepository.save(ElectronicsMapper.mapDtoToElectronics(electronicsDto,
                electronicsRepository.getNextId()));
        return ElectronicsMapper.mapElectronicsToDto(electronics);
    }

    @Override
    public ElectronicsDto removeElectronics(Long id) {
        Electronics electronics = electronicsRepository.delete(id).orElseThrow(ElectronicsNotFoundException::new);
        return ElectronicsMapper.mapElectronicsToDto(electronics);
    }

    @Override
    public ElectronicsDto updateElectronics(Long id, ElectronicsDto electronicsDto) {
        Electronics electronics = electronicsRepository.update(id, ElectronicsMapper
                .mapDtoToElectronics(electronicsDto, id)).orElseThrow(ElectronicsNotFoundException::new);
        return ElectronicsMapper.mapElectronicsToDto(electronics);
    }

    @Override
    public ElectronicsDto getElectronicsById(Long id) {
        Electronics electronics = electronicsRepository.getElectronicsById(id)
                .orElseThrow(ElectronicsNotFoundException::new);
        return ElectronicsMapper.mapElectronicsToDto(electronics);
    }

    @Override
    public List<ElectronicsDto> getAllElectronics() {
        return electronicsRepository.getAllElectronics().stream()
                .map(ElectronicsMapper::mapElectronicsToDto)
                .toList();
    }
}

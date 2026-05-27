package service.impl;

import dto.product.ElectronicsDto;
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
    public ElectronicsDto create(ElectronicsDto electronicsDto) {
        Electronics electronics = electronicsRepository.save(ElectronicsMapper.mapDtoToElectronics(electronicsDto,
                electronicsRepository.getNextId()));
        return ElectronicsMapper.mapElectronicsToDto(electronics);
    }

    @Override
    public ElectronicsDto remove(Long id) {
        Electronics electronics = electronicsRepository.delete(id).orElseThrow(ElectronicsNotFoundException::new);
        return ElectronicsMapper.mapElectronicsToDto(electronics);
    }

    @Override
    public ElectronicsDto update(Long id, ElectronicsDto electronicsDto) {
        Electronics electronics = electronicsRepository.update(id, ElectronicsMapper
                .mapDtoToElectronics(electronicsDto, id)).orElseThrow(ElectronicsNotFoundException::new);
        return ElectronicsMapper.mapElectronicsToDto(electronics);
    }

    @Override
    public ElectronicsDto getById(Long id) {
        Electronics electronics = electronicsRepository.getElectronicsById(id)
                .orElseThrow(ElectronicsNotFoundException::new);
        return ElectronicsMapper.mapElectronicsToDto(electronics);
    }

    @Override
    public List<ElectronicsDto> getAll() {
        return electronicsRepository.getAllElectronics().stream()
                .map(ElectronicsMapper::mapElectronicsToDto)
                .toList();
    }
}

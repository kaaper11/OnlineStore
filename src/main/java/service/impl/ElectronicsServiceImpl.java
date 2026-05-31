package service.impl;

import dto.product.request.ElectronicsRequestDto;
import dto.product.response.ElectronicsResponseDto;
import entity.product.type.Electronics;
import exception.ElectronicsNotFoundException;
import lombok.RequiredArgsConstructor;
import mapper.ElectronicsMapper;
import repository.ElectronicsRepository;
import service.ElectronicsService;
import utils.ProductIdGenerator;

import java.util.List;

@RequiredArgsConstructor
public class ElectronicsServiceImpl implements ElectronicsService {
    private final ElectronicsRepository electronicsRepository;
    private final ProductIdGenerator productIdGenerator;


    @Override
    public ElectronicsResponseDto create(ElectronicsRequestDto electronicsRequestDto) {
        Electronics electronics = electronicsRepository.save(ElectronicsMapper.mapDtoToElectronics(electronicsRequestDto,
                productIdGenerator.getNextProductId()));
        return ElectronicsMapper.mapElectronicsToDto(electronics);
    }

    @Override
    public ElectronicsResponseDto remove(Long id) {
        Electronics electronics = electronicsRepository.delete(id).orElseThrow(ElectronicsNotFoundException::new);
        return ElectronicsMapper.mapElectronicsToDto(electronics);
    }

    @Override
    public ElectronicsResponseDto update(Long id, ElectronicsRequestDto electronicsRequestDto) {
        Electronics electronics = electronicsRepository.update(id, ElectronicsMapper
                .mapDtoToElectronics(electronicsRequestDto, id)).orElseThrow(ElectronicsNotFoundException::new);
        return ElectronicsMapper.mapElectronicsToDto(electronics);
    }

    @Override
    public ElectronicsResponseDto getById(Long id) {
        Electronics electronics = electronicsRepository.getElectronicsById(id)
                .orElseThrow(ElectronicsNotFoundException::new);
        return ElectronicsMapper.mapElectronicsToDto(electronics);
    }

    @Override
    public List<ElectronicsResponseDto> getAll() {
        return electronicsRepository.getAllElectronics().stream()
                .map(ElectronicsMapper::mapElectronicsToDto)
                .toList();
    }
}

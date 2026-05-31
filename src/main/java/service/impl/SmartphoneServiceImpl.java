package service.impl;

import dto.product.request.SmartphoneRequestDto;
import dto.product.response.SmartphoneResponseDto;
import entity.product.type.Smartphone;
import exception.SmartphoneNotFoundException;
import lombok.RequiredArgsConstructor;
import mapper.SmartphoneMapper;
import repository.SmartphoneRepository;
import service.SmartphoneService;
import utils.ProductIdGenerator;

import java.util.List;

@RequiredArgsConstructor
public class SmartphoneServiceImpl implements SmartphoneService {
    private final SmartphoneRepository smartphoneRepository;
    private final ProductIdGenerator productIdGenerator;

    @Override
    public SmartphoneResponseDto create(SmartphoneRequestDto smartphoneDto) {
        Smartphone smartphone = smartphoneRepository.save(SmartphoneMapper.mapDtoToSmartphone(smartphoneDto,
                productIdGenerator.getNextProductId()));
        return SmartphoneMapper.mapSmartphoneToDto(smartphone);
    }

    @Override
    public SmartphoneResponseDto remove(Long id) {
        Smartphone smartphone = smartphoneRepository.delete(id).orElseThrow(SmartphoneNotFoundException::new);
        return SmartphoneMapper.mapSmartphoneToDto(smartphone);
    }

    @Override
    public SmartphoneResponseDto update(Long id, SmartphoneRequestDto smartphoneDto) {
        Smartphone smartphone = smartphoneRepository.update(id, SmartphoneMapper.mapDtoToSmartphone(smartphoneDto, id))
                .orElseThrow(SmartphoneNotFoundException::new);
        return SmartphoneMapper.mapSmartphoneToDto(smartphone);
    }

    @Override
    public SmartphoneResponseDto getById(Long id) {
        Smartphone smartphone = smartphoneRepository.getSmartphoneById(id)
                .orElseThrow(SmartphoneNotFoundException::new);
        return SmartphoneMapper.mapSmartphoneToDto(smartphone);
    }

    @Override
    public List<SmartphoneResponseDto> getAll() {
        return smartphoneRepository.getAllSmartphones().stream()
                .map(SmartphoneMapper::mapSmartphoneToDto)
                .toList();
    }
}

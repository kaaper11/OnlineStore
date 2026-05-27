package service.impl;

import dto.product.SmartphoneDto;
import entity.product.type.Smartphone;
import exception.SmartphoneNotFoundException;
import lombok.RequiredArgsConstructor;
import mapper.SmartphoneMapper;
import repository.SmartphoneRepository;
import service.SmartphoneService;

import java.util.List;

@RequiredArgsConstructor
public class SmartphoneServiceImpl implements SmartphoneService {
    private final SmartphoneRepository smartphoneRepository;

    @Override
    public SmartphoneDto create(SmartphoneDto smartphoneDto) {
        Smartphone smartphone = smartphoneRepository.save(SmartphoneMapper.mapDtoToSmartphone(smartphoneDto,
                smartphoneRepository.getNextId()));
        return SmartphoneMapper.mapSmartphoneToDto(smartphone);
    }

    @Override
    public SmartphoneDto remove(Long id) {
        Smartphone smartphone = smartphoneRepository.delete(id).orElseThrow(SmartphoneNotFoundException::new);
        return SmartphoneMapper.mapSmartphoneToDto(smartphone);
    }

    @Override
    public SmartphoneDto update(Long id, SmartphoneDto smartphoneDto) {
        Smartphone smartphone = smartphoneRepository.update(id, SmartphoneMapper.mapDtoToSmartphone(smartphoneDto, id))
                .orElseThrow(SmartphoneNotFoundException::new);
        return SmartphoneMapper.mapSmartphoneToDto(smartphone);
    }

    @Override
    public SmartphoneDto getById(Long id) {
        Smartphone smartphone = smartphoneRepository.getSmartphoneById(id)
                .orElseThrow(SmartphoneNotFoundException::new);
        return SmartphoneMapper.mapSmartphoneToDto(smartphone);
    }

    @Override
    public List<SmartphoneDto> getAll() {
        return smartphoneRepository.getAllSmartphones().stream()
                .map(SmartphoneMapper::mapSmartphoneToDto)
                .toList();
    }
}

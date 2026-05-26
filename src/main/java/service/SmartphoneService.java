package service;

import dto.type.SmartphoneDto;

import java.util.List;

public interface SmartphoneService {
    SmartphoneDto createSmartphone(SmartphoneDto smartphoneDto);

    SmartphoneDto removeSmartphone(Long id);

    SmartphoneDto updateSmartphone(Long id, SmartphoneDto smartphoneDto);

    SmartphoneDto getSmartphoneById(Long id);

    List<SmartphoneDto> getAllSmartphones();
}

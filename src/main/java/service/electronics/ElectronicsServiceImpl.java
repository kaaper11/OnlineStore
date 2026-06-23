package service.electronics;

import dto.product.request.ElectronicsRequestDto;
import dto.product.request.ProductRequestDto;
import dto.product.response.ElectronicsResponseDto;
import entity.client.Client;
import entity.client.Role;
import entity.product.type.Electronics;
import exception.ClientNotFoundException;
import exception.ElectronicsNotFoundException;
import exception.NoPermissionsException;
import lombok.RequiredArgsConstructor;
import mapper.ElectronicsMapper;
import repository.ClientRepository;
import repository.productrepositories.ElectronicsRepository;
import validator.ProductValidator;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service implementation responsible for managing Electronics products.
 * It provides operations for creating, updating, deleting, retrieving,
 * and validating electronics entities, while enforcing ADMIN-only access control.
 */
@RequiredArgsConstructor
public class ElectronicsServiceImpl implements ElectronicsService {
    private final ElectronicsRepository electronicsRepository;
    private final ClientRepository clientRepository;

    @Override
    public ElectronicsResponseDto create(ProductRequestDto dto, Long clientId) {
        ProductValidator.validate(dto);

        ElectronicsRequestDto electronicsRequestDto = (ElectronicsRequestDto) dto;
        Client client = clientRepository.getClientById(clientId).orElseThrow(ClientNotFoundException::new);

        if (client.getRole() != Role.ADMIN) {
            throw new NoPermissionsException();
        }

        Electronics electronics = electronicsRepository.save(ElectronicsMapper.mapDtoToElectronics(electronicsRequestDto,
                electronicsRepository.getNextId()));
        return ElectronicsMapper.mapElectronicsToDto(electronics);
    }

    @Override
    public void remove(Long id, Long clientId) {
        Client client = clientRepository.getClientById(clientId).orElseThrow(ClientNotFoundException::new);

        if (client.getRole() != Role.ADMIN) {
            throw new NoPermissionsException();
        }

        electronicsRepository.delete(id).orElseThrow(ElectronicsNotFoundException::new);
    }

    @Override
    public ElectronicsResponseDto updatePrice(Long id, Long clientId, BigDecimal price) {
        ProductValidator.validatePrice(price);

        Client client = clientRepository.getClientById(clientId).orElseThrow(ClientNotFoundException::new);

        if (client.getRole() != Role.ADMIN) {
            throw new NoPermissionsException();
        }

        Electronics electronics = electronicsRepository.getElectronicsById(id)
                .orElseThrow(ElectronicsNotFoundException::new);

        electronics.setPrice(price);
        return ElectronicsMapper.mapElectronicsToDto(electronics);
    }

    @Override
    public ElectronicsResponseDto updateQuantity(Long id, Long clientId, int quantity) {
        ProductValidator.validateQuantity(quantity);

        Client client = clientRepository.getClientById(clientId).orElseThrow(ClientNotFoundException::new);

        if (client.getRole() != Role.ADMIN) {
            throw new NoPermissionsException();
        }

        Electronics electronics = electronicsRepository.getElectronicsById(id)
                .orElseThrow(ElectronicsNotFoundException::new);

        electronics.setQuantity(quantity);
        return ElectronicsMapper.mapElectronicsToDto(electronics);
    }

    @Override
    public ElectronicsResponseDto getById(Long id) {
        Electronics electronics = electronicsRepository.getElectronicsById(id)
                .orElseThrow(ElectronicsNotFoundException::new);
        return ElectronicsMapper.mapElectronicsToDto(electronics);
    }

    @Override
    public boolean exist(Long id) {
        return electronicsRepository.getElectronicsById(id).isPresent();
    }

    @Override
    public List<ElectronicsResponseDto> getAll() {
        return electronicsRepository.getAllElectronics().stream()
                .map(ElectronicsMapper::mapElectronicsToDto)
                .toList();
    }
}

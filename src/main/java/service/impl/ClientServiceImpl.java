package service.impl;

import dto.client.ClientRequestDto;
import dto.client.ClientResponseDto;
import entity.client.Client;
import exception.ClientNotFoundException;
import lombok.RequiredArgsConstructor;
import mapper.ClientMapper;
import repository.CartRepository;
import repository.ClientRepository;
import service.ClientService;

import java.util.List;

@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final CartRepository cartRepository;

    @Override
    public ClientResponseDto createClient(ClientRequestDto clientRequestDto) {
        Client client = clientRepository.save(ClientMapper.mapDtoToClient(clientRequestDto, clientRepository.getNextId()));
        cartRepository.save(client.getId());
        return ClientMapper.mapClientToDto(client);
    }

    @Override
    public ClientResponseDto updateClient(Long id, ClientRequestDto clientRequestDto) {
        Client client = clientRepository.update(id, ClientMapper.mapDtoToClient(clientRequestDto, id))
                .orElseThrow(ClientNotFoundException::new);
        return ClientMapper.mapClientToDto(client);
    }

    @Override
    public ClientResponseDto removeClient(Long id) {
        cartRepository.delete(id);
        Client client = clientRepository.delete(id).orElseThrow(ClientNotFoundException::new);
        clientRepository.delete(id);
        return ClientMapper.mapClientToDto(client);
    }

    @Override
    public ClientResponseDto getClientById(Long id) {
        Client client = clientRepository.getClientById(id).orElseThrow(ClientNotFoundException::new);
        return ClientMapper.mapClientToDto(client);
    }

    @Override
    public ClientResponseDto getClientByEmail(String email) {
        Client client = clientRepository.getClientByEmail(email).orElseThrow(ClientNotFoundException::new);
        return ClientMapper.mapClientToDto(client);
    }

    @Override
    public List<ClientResponseDto> getAllClients() {
        return clientRepository.getAllComputers().stream()
                .map(ClientMapper::mapClientToDto)
                .toList();
    }
}

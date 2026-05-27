package service.impl;

import dto.client.ClientDto;
import entity.client.Client;
import exception.ClientNotFoundException;
import lombok.RequiredArgsConstructor;
import mapper.ClientMapper;
import repository.ClientRepository;
import service.ClientService;

import java.util.List;

@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    @Override
    public ClientDto createClient(ClientDto clientDto) {
        Client client = clientRepository.save(ClientMapper.mapDtoToClient(clientDto, clientRepository.getNextId()));
        return ClientMapper.mapClientToDto(client);
    }

    @Override
    public ClientDto updateClient(Long id, ClientDto clientDto) {
        Client client = clientRepository.update(id, ClientMapper.mapDtoToClient(clientDto, id))
                .orElseThrow(ClientNotFoundException::new);
        return ClientMapper.mapClientToDto(client);
    }

    @Override
    public ClientDto removeClient(Long id) {
        Client client = clientRepository.delete(id).orElseThrow(ClientNotFoundException::new);
        return ClientMapper.mapClientToDto(client);
    }

    @Override
    public ClientDto getClientById(Long id) {
        Client client = clientRepository.getClientById(id).orElseThrow(ClientNotFoundException::new);
        return ClientMapper.mapClientToDto(client);
    }

    @Override
    public ClientDto getClientByEmail(String email) {
        Client client = clientRepository.getClientByEmail(email).orElseThrow(ClientNotFoundException::new);
        return ClientMapper.mapClientToDto(client);
    }

    @Override
    public List<ClientDto> getAllClients() {
        return clientRepository.getAllComputers().stream()
                .map(ClientMapper::mapClientToDto)
                .toList();
    }
}

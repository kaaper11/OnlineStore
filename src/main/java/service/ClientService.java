package service;

import dto.client.ClientDto;

import java.util.List;

public interface ClientService {
    ClientDto createClient(ClientDto clientDto);

    ClientDto updateClient(Long id, ClientDto clientDto);

    ClientDto removeClient(Long id);

    ClientDto getClientById(Long id);

    ClientDto getClientByEmail(String email);

    List<ClientDto> getAllClients();
}

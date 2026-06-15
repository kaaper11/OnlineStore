package service.client;

import dto.client.LoginRequest;
import dto.client.ClientRequestDto;
import dto.client.ClientResponseDto;

import java.util.List;

public interface ClientService {
    ClientResponseDto createClient(ClientRequestDto clientRequestDto);

    ClientResponseDto updateClient(Long id, ClientRequestDto clientResponseDto);

    ClientResponseDto removeClient(Long id);

    ClientResponseDto getClientById(Long id);

    ClientResponseDto getClientByEmail(String email);

    List<ClientResponseDto> getAllClients();

    ClientResponseDto loginClient(LoginRequest loginRequest);
}

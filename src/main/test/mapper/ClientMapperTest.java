package mapper;

import dto.client.AddressDto;
import dto.client.ClientRequestDto;
import dto.client.ClientResponseDto;
import entity.client.Address;
import entity.client.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ClientMapperTest {

    private final Address address = new Address("Poland", "Warsaw", "Zlota", "15-820",
            10);
    private final AddressDto addressDto = new AddressDto("Poland", "Warsaw", "Zlota", "15-820",
            10);
    private final Client client = new Client(1L, "name", "email", "phone", address);
    private final ClientRequestDto clientRequestDto = new ClientRequestDto("name", "email", "phone", addressDto);

    @Test
    void shouldMapClientToDto() {
        ClientResponseDto result = ClientMapper.mapClientToDto(client);

        assertThat(result).isNotNull();
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(clientRequestDto);
    }

    @Test
    void shouldMapClientDtoToClient() {
        Client result = ClientMapper.mapDtoToClient(clientRequestDto, 1L);

        assertThat(result).isNotNull();
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(client);
    }

}

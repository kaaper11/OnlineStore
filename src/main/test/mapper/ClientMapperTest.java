package mapper;

import dto.client.AddressDto;
import dto.client.ClientRequestDto;
import dto.client.ClientResponseDto;
import entity.client.Address;
import entity.client.Client;
import entity.client.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ClientMapperTest {

    private static final Address ADDRESS = new Address("Poland", "Warsaw", "Zlota", "15-820",
            10);
    private static final AddressDto ADDRESS_DTO = new AddressDto("Poland", "Warsaw", "Zlota", "15-820",
            10);
    private static final Client CLIENT = new Client(1L, "name", "email", "pass", "phone",
            ADDRESS, Role.USER);
    private static final ClientRequestDto CLIENT_REQUEST_DTO = new ClientRequestDto("name", "email", "pass",
            "phone", ADDRESS_DTO);

    @Test
    void shouldMapClientToDto() {
        ClientResponseDto result = ClientMapper.mapClientToDto(CLIENT);

        assertThat(result).isNotNull();
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("role", "id")
                .isEqualTo(CLIENT_REQUEST_DTO);
    }

    @Test
    void shouldMapClientDtoToClient() {
        Client result = ClientMapper.mapDtoToClient(CLIENT_REQUEST_DTO);

        assertThat(result).isNotNull();
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(CLIENT);
    }

}

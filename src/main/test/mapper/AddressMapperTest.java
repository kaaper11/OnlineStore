package mapper;

import dto.client.AddressDto;
import entity.client.Address;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AddressMapperTest {

    private final static Address ADDRESS = new Address("Poland", "Warsaw", "Zlota", "15-820",
            10);
    private final static AddressDto ADDRESS_DTO = new AddressDto("Poland", "Warsaw", "Zlota", "15-820",
            10);

    @Test
    public void shouldMapAddressToDto() {
        AddressDto result = AddressMapper.mapAddressToDto(ADDRESS);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(ADDRESS);
    }

    @Test
    public void shouldMapAddressDtoToAddress() {
        Address result = AddressMapper.mapDtoToAddress(ADDRESS_DTO);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(ADDRESS_DTO);
    }
}

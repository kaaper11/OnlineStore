package dto.client;

public record ClientRequestDto(
        String name,

        String email,
        String password,

        String phone,

        AddressDto address) {
}

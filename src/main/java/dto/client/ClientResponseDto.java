package dto.client;

public record ClientResponseDto(Long id, String name, String email, String password, String phone, AddressDto address) {
}

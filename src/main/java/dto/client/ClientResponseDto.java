package dto.client;

public record ClientResponseDto(Long id, String name, String email, String phone, AddressDto address) {
}

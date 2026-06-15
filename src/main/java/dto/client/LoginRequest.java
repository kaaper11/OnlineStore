package dto.client;

import jakarta.validation.constraints.Email;

public record LoginRequest(
        @Email(message = "To ma być mail!")
        String email,
        String password) {
}

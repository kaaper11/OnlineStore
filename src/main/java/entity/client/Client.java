package entity.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * Represents a client in the system.
 * A client contains personal information such as name, email, password,
 * phone number, and address, as well as an assigned role defining
 * their permissions within the system.
 * <p>
 * Equality of clients is based on the email address, meaning that
 * two clients are considered equal if they share the same email.
 */
@AllArgsConstructor
@Getter
public class Client {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private Address address;
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(email, client.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }
}

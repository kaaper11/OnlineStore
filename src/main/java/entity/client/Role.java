package entity.client;

/**
 * Represents the role of a client within the system.
 * Determines the level of access and permissions assigned to a client.
 * <p>
 * ADMIN - has full access to system functionality
 * USER - has limited access intended for standard users
 */
public enum Role {
    ADMIN, USER
}

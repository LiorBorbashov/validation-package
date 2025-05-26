package il.ac.hit.validation;

import il.ac.hit.validation.user.types.BasicUser;
import il.ac.hit.validation.user.types.PlatinumUser;
import il.ac.hit.validation.user.types.PremiumUser;

/**
 * The {@code UserFactory} class implements the Factory Method design pattern.
 * It provides a static method to create different types of {@code User} objects
 * based on a given type string.
 */
public class UserFactory {
    /**
     * Creates a new {@code User} object based on the given type.
     * <p>
     * Supported types are: "basic", "premium", "platinum".
     *
     * @param type     the type of user to create ("basic", "premium", "platinum")
     * @param username the username of the user
     * @param email    the email address of the user
     * @param password the password of the user
     * @param age      the age of the user
     * @return a new instance of a subclass of {@code User} based on the given type
     * @throws IllegalArgumentException if the user type is unknown
     */
    public static User createUser(String type, String username, String email, String password, int age) {
        // Convert type to lowercase for case-insensitive support
        switch (type.toLowerCase()) {
            case "basic":
                return new BasicUser(username, email, password, age);
            case "premium":
                return new PremiumUser(username, email, password, age);
            case "platinum":
                return new PlatinumUser(username, email, password, age);
            default:
                // Throw exception if the type doesn't match any known user type
                throw new IllegalArgumentException("Unknown user type: " + type);
        }
    }
}

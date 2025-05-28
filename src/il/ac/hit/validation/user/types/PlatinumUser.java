package il.ac.hit.validation.user.types;

import il.ac.hit.validation.User;

/**
 * The {@code PlatinumUser} class extends the {@code User} class and inherits all its fields and behavior.
 */
public class PlatinumUser extends User {
    /**
     * Creates a new {@code PlatinumUser} with the given details.
     *
     * @param username the user's username
     * @param email the user's email
     * @param password the user's password
     * @param age the user's age
     */
    public PlatinumUser(String username, String email, String password, int age) {
        // Call the constructor of the parent class (User)
        super(username, email, password, age);
    }
}

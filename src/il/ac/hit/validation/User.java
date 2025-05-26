package il.ac.hit.validation;

/**
 * The {@code User} class represents a user with details
 * such as username, email, password, and age.
 * <p>
 * Provides getters and setters for each field,
 * and overrides {@code toString} to print the user details.
 */
public class User {
    private String username;
    private String email;
    private String password;
    private int age;

    /**
     * Constructs a new {@code User} with the specified details.
     *
     * @param username the username of the user
     * @param email    the email address of the user
     * @param password the password of the user
     * @param age      the age of the user
     */
    public User(String username, String email, String password, int age) {
        setUsername(username);
        setEmail(email);
        setPassword(password);
        setAge(age);
    }

    /**
     * Returns the username of the user.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the email of the user.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email the new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the password of the user.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the user's age.
     *
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the user's age.
     *
     * @param age the new age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Returns a string representation of the user.
     *
     * @return a string with the user's username, password, email, and age
     */
    @Override
    public String toString() {
        return "username: " + username + "password: " +password + ", email: " + email + ", age: " + age;
    }
}

package il.ac.hit.validation;

import java.util.Objects;

/**
 * The {@code User} class represents a user with details
 * such as username, email, password, and age.
 * <p>
 * Provides getters and setters for each field,
 * and overrides {@code toString, equals, hashCode}.
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

    /**
     * Checks if this user is equal to another object.
     * Two users are considered equal if all fields are equal.
     *
     * @param obj the object to compare
     * @return true if equal; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        // same reference
        if (this == obj) return true;

        // null or different type
        if (obj == null || getClass() != obj.getClass()) return false;

        User other = (User) obj;
        return age == other.age &&
                Objects.equals(username, other.username) &&
                Objects.equals(email, other.email) &&
                Objects.equals(password, other.password);
    }

    /**
     * Computes the hash code for this user based on all fields.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(username, email, password, age);
    }
}

package il.ac.hit.validation;

import java.util.function.Function;

/**
 * The {@code UserValidation} interface defines a rule for checking if a {@code User} is valid.
 * It extends {@code Function<User, ValidationResult>} and includes methods to combine or create rules.
 */
public interface UserValidation extends Function<User, ValidationResult> {

    /**
     * Combines two validations, Both validations must pass for the result to be valid.
     *
     * @param other another validation to combine with
     * @return a combined {@code UserValidation} that passes only if both validations pass
     */
    default UserValidation and(UserValidation other) {
        if (other == null) {
            throw new IllegalArgumentException("Other validation must not be null");
        }

        return user -> {
            ValidationResult first = this.apply(user);
            if (!first.isValid()) {
                return first;
            }

            return other.apply(user);
        };
    }

    /**
     * Combines two validations, Only one validation needs to pass.
     *
     * @param other another validation to combine with
     * @return a combined {@code UserValidation} that passes if at least one validation passes
     */
    default UserValidation or(UserValidation other) {
        if (other == null) {
            throw new IllegalArgumentException("Other validation must not be null");
        }

        return user -> {
            ValidationResult first = this.apply(user);
            if (first.isValid()) {
                return first;
            }

            ValidationResult second = other.apply(user);
            if (second.isValid()) {
                return second;
            }

            // If both are invalid, return combined failure reason
            return new Invalid("Both validations failed: " + first.getReason() + " | " + second.getReason());
        };
    }

    /**
     * Combines two validations. Exactly one validation must pass.
     *
     * @param other another validation to combine with
     * @return a combined validation
     */
    default UserValidation xor(UserValidation other) {
        if (other == null) {
            throw new IllegalArgumentException("Other validation must not be null");
        }

        return user -> {
            boolean a = this.apply(user).isValid();
            boolean b = other.apply(user).isValid();
            return a ^ b ? new Valid() : new Invalid("XOR condition failed");
        };
    }

    /**
     * Returns a validation that passes only if all the given validations pass.
     *
     * @param validations array of validations
     * @return a combined {@code UserValidation} that passes if all given validations pass
     */
    static UserValidation all(UserValidation... validations) {
        if (validations == null) {
            throw new IllegalArgumentException("Validations must not be null");
        }

        return user -> {
            for (UserValidation validation : validations) {
                if (validation == null) {
                    return new Invalid("Validation cannot be null");
                }

                ValidationResult result = validation.apply(user);
                if (!result.isValid()) {
                    return result;
                }
            }
            return new Valid();
        };
    }

    /**
     * Returns a validation that passes only if none of the given validations pass.
     *
     * @param validations array of validations
     * @return a {@code UserValidation} that fails if any given validation passes
     */
    static UserValidation none(UserValidation... validations) {
        if (validations == null) {
            throw new IllegalArgumentException("Validations must not be null");
        }

        return user -> {
            for (UserValidation validation : validations) {
                if (validation == null) {
                    return new Invalid("Validation cannot be null");
                }

                ValidationResult result = validation.apply(user);
                if (result.isValid()) {
                    return new Invalid("At least one validation is valid");
                }
            }
            return new Valid();
        };
    }

    /**
     * Validation that checks whether the provided {@code User} is not null.
     *
     * @return a {@code UserValidation} that returns {@code Invalid} if the user is null,
     *         otherwise returns {@code Valid}
     */
    static UserValidation userNotNull() {
        return user -> user == null
                ? new Invalid("User cannot be null")
                : new Valid();
    }

    /**
     * Validates that the user's email ends with "il".
     *
     * @return a {@code UserValidation} that checks if the user's email ends with "il".
     */
    static UserValidation emailEndsWithIL() {
        return userNotNull().and(user -> {
            String email = user.getEmail();
            return email != null && email.endsWith("il")
                    ? new Valid()
                    : new Invalid("Email must end with 'il'");
        });
    }

    /**
     * Validates that the user's email is longer than 10 characters.
     *
     * @return a {@code UserValidation} that checks if the user's email is longer than 10 characters.
     */
    static UserValidation emailLengthBiggerThan10() {
        return userNotNull().and(user -> {
            String email = user.getEmail();
            return email != null && email.length() > 10
                    ? new Valid()
                    : new Invalid("Email must be longer than 10 characters");
        });
    }

    /**
     * Validates that the user's password is longer than 8 characters.
     *
     * @return a {@code UserValidation} that checks if the user's password is longer than 8 characters.
     */
    static UserValidation passwordLengthBiggerThan8() {
        return userNotNull().and(user -> {
            String password = user.getPassword();
            return password != null && password.length() > 8
                    ? new Valid()
                    : new Invalid("Password must be longer than 8 characters");
        });
    }

    /**
     * Validates that the password contains only letters and numbers.
     *
     * @return a {@code UserValidation} that checks if the password contains only letters and numbers.
     */
    static UserValidation passwordIncludesLettersNumbersOnly() {
        return userNotNull().and(user -> {
            String password = user.getPassword();
            return password != null && password.matches("[A-Za-z0-9]+")
                    ? new Valid()
                    : new Invalid("Password must include only letters and numbers");
        });
    }

    /**
     * Validates that the password contains the dollar sign ('$') character.
     *
     * @return a {@code UserValidation} that checks if the password contains the dollar sign ('$') character.
     */
    static UserValidation passwordIncludesDollarSign() {
        return userNotNull().and(user -> {
            String password = user.getPassword();
            return password != null && password.contains("$")
                    ? new Valid()
                    : new Invalid("Password must include the $ character");
        });
    }

    /**
     * Validates that the password is different from the username.
     *
     * @return a {@code UserValidation} that checks if the password is different from the username.
     */
    static UserValidation passwordIsDifferentFromUsername() {
        return userNotNull().and(user -> {
            String username = user.getUsername();
            String password = user.getPassword();
            return username != null && password != null && !username.equals(password)
                    ? new Valid()
                    : new Invalid("Password must be different from username");
        });
    }

    /**
     * Validates that the user's age is greater than 18.
     *
     * @return a {@code UserValidation} that checks if the user's age is greater than 18.
     */
    static UserValidation ageBiggerThan18() {
        return userNotNull().and(user -> user.getAge() > 18
                ? new Valid()
                : new Invalid("User must be older than 18"));
    }

    /**
     * Validates that the username is longer than 8 characters.
     *
     * @return a {@code UserValidation} that checks if the username is longer than 8 characters.
     */
    static UserValidation usernameLengthBiggerThan8() {
        return userNotNull().and(user -> {
            String username = user.getUsername();
            return username != null && username.length() > 8
                    ? new Valid()
                    : new Invalid("Username must be longer than 8 characters");
        });
    }

}
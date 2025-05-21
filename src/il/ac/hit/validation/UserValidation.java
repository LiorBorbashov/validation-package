package il.ac.hit.validation;

import java.util.function.Function;

public interface UserValidation extends Function<User, ValidationResult> {
    default UserValidation and(UserValidation other) {
        return user -> {
            ValidationResult first = this.apply(user);
            ValidationResult second = other.apply(user);

            // if both valid
            if (first.isValid() && second.isValid()) {
                return new Valid();
            }

            // if both invalid
            if (!first.isValid() && !second.isValid()) {
                return new Invalid("Both validations failed: " + first.getReason() + " | " + second.getReason());
            }

            // if only one valid
            return !first.isValid() ? first : second;
        };
    }

    default UserValidation or(UserValidation other) {
        return user -> {
            ValidationResult first = this.apply(user);
            if (first.isValid()) {
                return first;
            }

            ValidationResult second = other.apply(user);
            if (second.isValid()) {
                return second;
            }

            // If both failed, return custom message
            return new Invalid("Both validations failed: " + first.getReason() + " | " + second.getReason());
        };
    }

    default UserValidation xor(UserValidation other) {
        return user -> {
            boolean a = this.apply(user).isValid();
            boolean b = other.apply(user).isValid();
            return a ^ b ? new Valid() : new Invalid("XOR condition failed");
        };
    }

    static UserValidation all(UserValidation... validations) {
        return user -> {
            for (UserValidation validation : validations) {
                ValidationResult result = validation.apply(user);
                if (!result.isValid()) {
                    return result;
                }
            }
            return new Valid();
        };
    }

    static UserValidation none(UserValidation... validations) {
        return user -> {
            for (UserValidation validation : validations) {
                ValidationResult result = validation.apply(user);
                if (result.isValid()) {
                    return new Invalid("At least one condition is valid");
                }
            }
            return new Valid();
        };
    }

    static UserValidation emailEndsWithIl() {
        return user -> {
            String email = user.getEmail();
            return email != null && email.endsWith("il")
                    ? new Valid()
                    : new Invalid("Email must end with 'il'");
        };
    }

    static UserValidation emailLengthBiggerThan10() {
        return user -> {
            String email = user.getEmail();
            return email != null && email.length() > 10
                    ? new Valid()
                    : new Invalid("Email must be longer than 10 characters");
        };
    }

    static UserValidation passwordLengthBiggerThan8() {
        return user -> {
            String password = user.getPassword();
            return password != null && password.length() > 8
                    ? new Valid()
                    : new Invalid("Password must be longer than 8 characters");
        };
    }

    static UserValidation passwordIncludesLettersNumbersOnly() {
        return user -> {
            String password = user.getPassword();
            return password != null && password.matches("[A-Za-z0-9]+")
                    ? new Valid()
                    : new Invalid("Password must include only letters and numbers");
        };
    }

    static UserValidation passwordIncludesDollarSign() {
        return user -> {
            String password = user.getPassword();
            return password != null && password.contains("$")
                    ? new Valid()
                    : new Invalid("Password must include the $ character");
        };
    }

    static UserValidation passwordIsDifferentFromUsername() {
        return user -> {
            String username = user.getUsername();
            String password = user.getPassword();
            return username != null && password != null && !username.equals(password)
                    ? new Valid()
                    : new Invalid("Password must be different from username");
        };
    }

    static UserValidation ageBiggerThan18() {
        return user ->  user.getAge() > 18
                    ? new Valid()
                    : new Invalid("User must be older than 18");
    }

    static UserValidation usernameLengthBiggerThan8() {
        return user -> user.getUsername() != null && user.getUsername().length() > 8
                    ? new Valid()
                    : new Invalid("Username must be longer than 8 characters");
    }

}

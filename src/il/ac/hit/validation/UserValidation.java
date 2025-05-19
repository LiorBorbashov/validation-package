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
}

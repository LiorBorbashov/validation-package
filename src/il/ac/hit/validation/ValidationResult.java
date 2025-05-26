package il.ac.hit.validation;

import java.util.Optional;

/**
 * The {@code ValidationResult} interface represents the result of a validation check.
 * It has methods to check if it's valid and to get the reason if it's not.
 */
public interface ValidationResult {
    /**
     * Indicates whether the validation was successful.
     *
     * @return {@code true} if valid; {@code false} otherwise
     */
    boolean isValid();

    /**
     * Returns the reason for a validation failure.
     * <p>
     * If the result is valid, this will return an empty {@code Optional}.
     *
     * @return an {@code Optional} containing the failure reason, or empty if valid
     */
    Optional<String> getReason();
}

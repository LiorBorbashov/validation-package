package il.ac.hit.validation;

import java.util.Optional;

/**
 * The {@code Invalid} class represents a failed validation result.
 * It implements the {@code ValidationResult} interface and always returns false for {@code isValid()}.
 * It stores a reason explaining why the validation failed.
 */
public class Invalid implements ValidationResult{
    // explanation of why the validation failed
    private final String reason;

    /**
     * Constructs an {@code Invalid} validation result with the given reason.
     * If the provided reason is null, a default message "Validation failed" will be used.
     * @param reason the reason for the validation failure, or null to use default message.
     */
    public Invalid(String reason) {
        this.reason = reason != null ? reason : "Validation failed";}

    /**
     * Always returns false because this result means the validation failed.
     *
     * @return false
     */
    @Override
    public boolean isValid() {
        return false;
    }

    /**
     * Returns an {@code Optional} containing the failure reason.
     *
     * @return an {@code Optional} with the reason string
     */
    @Override
    public Optional<String> getReason() {
        return Optional.of(reason);
    }
}

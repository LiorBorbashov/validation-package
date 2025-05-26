package il.ac.hit.validation;

import java.util.Optional;

/**
 * The {@code Valid} class represents a successful validation result.
 * It implements the {@code ValidationResult} interface and always returns true for {@code isValid()}.
 */
public class Valid implements ValidationResult{

    /**
     * Creates a new {@code Valid} result, indicating that validation passed.
     */
    public Valid() {}

    /**
     * Always returns true because this result means the validation passed.
     *
     * @return true
     */
    @Override
    public boolean isValid() {
        return true;
    }

    /**
     * Returns an empty {@code Optional} because a valid result has no error message.
     *
     * @return an empty {@code Optional}
     */
    @Override
    public Optional<String> getReason() {
        return Optional.empty();
    }
}

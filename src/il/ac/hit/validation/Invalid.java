package il.ac.hit.validation;

import java.util.Optional;

public class Invalid implements ValidationResult{
    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public Optional<String> getReason() {
        return Optional.of("invalid reason");
    }
}

package il.ac.hit.validation;

import java.util.Optional;

public class Valid implements ValidationResult{

    public Valid() {}

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public Optional<String> getReason() {
        return Optional.empty();
    }
}

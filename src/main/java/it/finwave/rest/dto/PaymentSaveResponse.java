package it.finwave.rest.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@RegisterForReflection
public class PaymentSaveResponse {
    @Setter(lombok.AccessLevel.NONE)
    private long savedLines = 0;
    @Setter(lombok.AccessLevel.NONE)
    private Map<String, List<String>> errors = new HashMap<>();

    public void incrementSavedLines() {
        savedLines++;
    }

    public void putError(String reference, List<String> violations) {
        errors.put(reference, violations);
    }
}

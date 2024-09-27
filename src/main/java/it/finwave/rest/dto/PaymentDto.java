package it.finwave.rest.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;
import java.time.Instant;

@RegisterForReflection
public record PaymentDto(@NotBlank @UUID String paymentId, @NotBlank @Size(min = 10, max = 10) String payer,
                         @NotBlank @Size(min = 10, max = 10) String payee,
                         @DecimalMin(value = "0.0", inclusive = false) String amount,
                         @NotBlank @Size(min = 3, max = 3) String currency, @PastOrPresent Instant date,
                         String description) {
}

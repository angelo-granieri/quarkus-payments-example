package it.finwave.data;

import it.finwave.rest.dto.PaymentDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private java.util.UUID id;
    @NotBlank
    @UUID
    @Column(name = "payment_id", nullable = false)
    private String paymentId;
    @NotBlank
    @Size(min = 10, max = 10)
    @Column(name = "payer", nullable = false)
    private String payer;
    @NotBlank
    @Size(min = 10, max = 10)
    @Column(name = "payee", nullable = false)
    private String payee;
    @DecimalMin(value = "0.0", inclusive = false)
    @Column(name = "amount", nullable = false, precision = 20, scale = 2)
    private BigDecimal amount;
    @NotBlank
    @Size(min = 3, max = 3)
    @Column(name = "currency", nullable = false, length = 3)
    private String currency;
    @PastOrPresent
    @Column(name = "date", nullable = false)
    private Instant date;
    @Column(name = "description")
    private String description;

    public Payment(PaymentDto paymentDto) {
        this.paymentId = paymentDto.paymentId();
        this.payer = paymentDto.payer();
        this.payee = paymentDto.payee();
        this.amount = BigDecimal.valueOf(Double.parseDouble(paymentDto.amount()));
        this.currency = paymentDto.currency();
        this.date = paymentDto.date();
        this.description = paymentDto.description();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Payment payment = (Payment) o;
        return getId() != null && Objects.equals(getId(), payment.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

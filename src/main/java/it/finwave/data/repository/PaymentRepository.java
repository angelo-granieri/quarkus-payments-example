package it.finwave.data.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import it.finwave.data.Payment;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class PaymentRepository implements PanacheRepositoryBase<Payment, UUID> {
}

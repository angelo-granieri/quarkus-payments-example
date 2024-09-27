package it.finwave.parser;

import it.finwave.rest.dto.PaymentDto;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface PaymentParser extends AutoCloseable {
    Stream<PaymentDto> parse(Path file);
}

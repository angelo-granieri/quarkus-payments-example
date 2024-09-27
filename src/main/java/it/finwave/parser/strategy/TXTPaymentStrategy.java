package it.finwave.parser.strategy;

import io.quarkus.logging.Log;
import it.finwave.parser.PaymentParser;
import it.finwave.rest.dto.PaymentDto;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@RequestScoped
public class TXTPaymentStrategy implements PaymentParser {
    private final static String PAYMENT_REGEX = "([0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}),([\\w-]+),([\\w-]+),(\\d+\\.\\d{2}),(\\w{3}),(\\d{4}-\\d{2}-\\d{2}),(.+)";
    private BufferedReader reader;

    @Override
    public Stream<PaymentDto> parse(Path file) {
        try {
            reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);
            return reader.lines()
                    .map(this::parseLine)
                    .onClose(this::close);
        } catch (Exception e) {
            Log.error("Error reading file: %s".formatted(file), e);
            return Stream.empty();
        }
    }

    private PaymentDto parseLine(String line) {
        Pattern pattern = Pattern.compile(PAYMENT_REGEX);
        Matcher matcher = pattern.matcher(line);

        if (matcher.matches()) {
            try {
                return new PaymentDto(
                        matcher.group(1), // UUID
                        matcher.group(2), // Sender
                        matcher.group(3), // Receiver
                        matcher.group(4), // Amount
                        matcher.group(5), // Currency
                        Instant.parse(matcher.group(6)), // Date
                        matcher.group(7)  // Description
                );
            } catch (NumberFormatException e) {
                throw new RuntimeException("Error parsing line: %s".formatted(line), e);
            }
        } else {
            throw new IllegalArgumentException("Line does not match expected format: %s".formatted(line));
        }
    }

    @Override
    public void close() {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            Log.error("Error closing file", e);
            throw new WebApplicationException("Error closing file", e, Response.Status.UNSUPPORTED_MEDIA_TYPE);
        }
    }
}

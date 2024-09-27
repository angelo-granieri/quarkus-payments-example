package it.finwave.parser.strategy;

import io.quarkus.logging.Log;
import it.finwave.parser.PaymentParser;
import it.finwave.rest.dto.PaymentDto;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.stream.Stream;

@RequestScoped
public class CSVPaymentStrategy implements PaymentParser {
    private final static String SEPARATOR = ",";
    private BufferedReader reader;

    @Override
    public Stream<PaymentDto> parse(Path file) {
        try {
            reader = Files.newBufferedReader(file, StandardCharsets.UTF_8);
            StreamTokenizer tokenizer = new StreamTokenizer(reader);
            return reader.lines()
                    .skip(1)
                    .map(this::parseLine)
                    .onClose(this::close);
        } catch (Exception e) {
            Log.error("Error reading file: %s".formatted(file), e);
            return Stream.empty();
        }
    }


    public PaymentDto parseLine(String line) {
        if (line == null || line.isBlank()) {
            throw new IllegalArgumentException("Empty line");
        } else {
            String[] lines = line.split(SEPARATOR);
            if (lines.length != 7) {
                throw new IllegalArgumentException("Invalid number of fields");
            } else {
                try {
                    return new PaymentDto(
                            lines[0], // ID
                            lines[1], // Payer
                            lines[2], // Payee
                            lines[3], // Amount
                            lines[4], // Currency
                            Instant.parse(lines[5]), // Date
                            lines[6]  // Description
                    );
                } catch (Exception e) {
                    throw new RuntimeException("Error parsing line: %s".formatted(line), e);
                }
            }
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

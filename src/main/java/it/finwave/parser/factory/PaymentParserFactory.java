package it.finwave.parser.factory;

import it.finwave.parser.PaymentParser;
import it.finwave.parser.strategy.CSVPaymentStrategy;
import it.finwave.parser.strategy.TXTPaymentStrategy;

import java.util.HashMap;
import java.util.Map;


public class PaymentParserFactory {
    private static final Map<String, PaymentParser> parsers = new HashMap<>();

    static {
        parsers.put("text/csv", new CSVPaymentStrategy());
        parsers.put("text/plain", new TXTPaymentStrategy());
    }

    public static PaymentParser getParser(String mimeType) {
        PaymentParser parser = parsers.get(mimeType);
        if (parser == null) {
            throw new IllegalArgumentException("Unsupported document type: %s".formatted(mimeType));
        }
        return parser;
    }
}

package it.finwave.service;

import it.finwave.data.Payment;
import it.finwave.data.repository.PaymentRepository;
import it.finwave.parser.PaymentParser;
import it.finwave.parser.factory.PaymentParserFactory;
import it.finwave.rest.dto.PaymentDto;
import it.finwave.rest.dto.PaymentSaveResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.util.Set;

@ApplicationScoped
public class PaymentService {
    @Inject
    PaymentRepository paymentRepository;
    @Inject
    Validator validator;

    @Transactional
    public PaymentSaveResponse handleFile(FileUpload file) throws Exception {
        PaymentParser paymentParser = PaymentParserFactory.getParser(file.contentType());
        PaymentSaveResponse response = new PaymentSaveResponse();
        paymentParser.parse(file.uploadedFile()).forEach(payment -> persistIfValid(payment, response));
        paymentRepository.flush();
        return response;
    }

    private void persistIfValid(PaymentDto paymentDto, PaymentSaveResponse response) {
        Set<ConstraintViolation<PaymentDto>> violationSet = validator.validate(paymentDto);
        if (violationSet.isEmpty()) {
            paymentRepository.persist(new Payment(paymentDto));
            response.incrementSavedLines();
        } else {
            response.putError(paymentDto.paymentId(), violationSet.stream().map(violation -> String.format("%s - %s", violation.getPropertyPath().toString(), violation.getMessage())).toList());
        }
    }
}

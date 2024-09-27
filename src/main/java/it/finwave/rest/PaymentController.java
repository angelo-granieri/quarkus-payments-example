package it.finwave.rest;

import it.finwave.rest.dto.PaymentSaveResponse;
import it.finwave.service.PaymentService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

@Path("api/payments")
public class PaymentController {

    @Inject
    PaymentService paymentService;

    @GET
    public String hello() {
        return "Hello World from Quarkus!";
    }

    @POST
    @Produces("application/json")
    public Response postBinaryFile(@RestForm FileUpload file) throws Exception {
        PaymentSaveResponse paymentSaveResponse = paymentService.handleFile(file);
        if (paymentSaveResponse.getSavedLines() > 0) {
            return Response.ok(paymentSaveResponse).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity(paymentSaveResponse).build();
        }
    }
}

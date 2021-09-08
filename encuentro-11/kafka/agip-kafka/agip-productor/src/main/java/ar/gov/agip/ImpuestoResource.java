package ar.gov.agip;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import io.smallrye.mutiny.Multi;

import ar.gov.agip.model.Impuesto;

@Path("/impuestos")
public class ImpuestoResource {

    @Channel("impuesto-requests")
    Emitter<String> impuestoRequestEmitter;

    /**
     * Endpoint to generate a new quote request id and send it to "impuesto-requests" Kafka topic using the emitter.
     */
    @POST
    @Path("/request")
    @Produces(MediaType.TEXT_PLAIN)
    public String createRequest() {
        UUID uuid = UUID.randomUUID();
        impuestoRequestEmitter.send(uuid.toString());
        return uuid.toString();
    }

    @Channel("impuestos")
    Multi<Impuesto> impuestos;

    /**
     * Endpoint retrieving the "impuestos" Kafka topic and sending the items to a server sent event.
     */
    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS) // denotes that server side events (SSE) will be produced
    public Multi<Impuesto> stream() {
        return impuestos.log();
    }
}

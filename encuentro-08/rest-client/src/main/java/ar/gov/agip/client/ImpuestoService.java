package ar.gov.agip.client;

import java.util.Set;
import java.util.concurrent.CompletionStage;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import io.smallrye.mutiny.Uni;

@Path("/impuesto")
@RegisterRestClient
public interface ImpuestoService {

    @GET
    @Path("/nombre/{nombre}")
    @Produces("application/json")
    Impuesto getByNombre(@PathParam String nombre);

    @GET
    @Path("/{id}")
    @Produces("application/json")
    Impuesto getById(@PathParam Long id);

}

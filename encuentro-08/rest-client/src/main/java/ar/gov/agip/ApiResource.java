package ar.gov.agip;

import ar.gov.agip.client.Impuesto;
import ar.gov.agip.client.ImpuestoService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/api")
public class ApiResource {

    @Inject
    @RestClient
    ImpuestoService impuestoService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String checkApi() {
        return "Check API REST CLIENT";
    }


    @GET
    @Path("/impuesto/nombre/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Impuesto byNombre(@PathParam String nombre) {
        return impuestoService.getByNombre(nombre);
    }

    @GET
    @Path("/impuesto/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Impuesto byId(@PathParam Long id) {
        return impuestoService.getById(id);
    }
}
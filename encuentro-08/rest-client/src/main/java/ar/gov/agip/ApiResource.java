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

// Métricas
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

@Path("/api")
public class ApiResource {

    @Inject
    ApiService apiService;

    // Métricas
    @Counted(name = "countGetCheckApi", description = "Cuenta la cantidad de veces que se ha invocado el método checkApi.")
    @Timed(name = "timeGetCheckApi", description = "Cuanto Tiempo se tarda en invocar al método checkApi", unit = MetricUnits.MILLISECONDS)
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String checkApi() {
        return "Check API REST CLIENT";
    }


    @Counted(name = "countByNombre", description = "Cuenta la cantidad de veces que se ha invocado el método byNombre.")
    @Timed(name = "timeByNombre", description = "Cuanto Tiempo se tarda en invocar al método byNombre", unit = MetricUnits.MILLISECONDS)
    @GET
    @Path("/impuesto/nombre/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Impuesto byNombre(@PathParam String nombre) {
        return apiService.getImpuestoByNombre(nombre);
    }

    @GET
    @Path("/impuesto/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Impuesto byId(@PathParam Long id) {
        return apiService.getImpuestoById(id);
    }
}
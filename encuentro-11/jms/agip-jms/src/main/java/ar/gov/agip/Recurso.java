package ar.gov.agip;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Recurso para obtener los random
 */
@Path("/random")
public class Recurso {

    @Inject
    Consumidor consumidor;

    @GET
    @Path("ultimo")
    @Produces(MediaType.TEXT_PLAIN)
    public String last() {
        return consumidor.getRandomWord();
    }
}

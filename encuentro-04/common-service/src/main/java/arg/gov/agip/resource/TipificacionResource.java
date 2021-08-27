package arg.gov.agip.resource;

import arg.gov.agip.model.Tipificacion;
import arg.gov.agip.service.TipificacionService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/api/agip/common/tipificacion")
@Produces(APPLICATION_JSON)
public class TipificacionResource {

    @Inject
    TipificacionService tipificacionService;

    @GET
    public Response getAllTipificaciones() {
        List<Tipificacion> tipificacions = tipificacionService.findAll();
        return Response.ok(tipificacions).build();
    }

    @GET
    @Path("/{id}")
    public Response getTipificacion(
            @PathParam("id") Long id) {
        Tipificacion tipificacion = tipificacionService.findById(id);
        if (tipificacion != null) {
            return Response.ok(tipificacion).build();
        } else {
            return Response.noContent().build();
        }
    }

    @POST
    public Response createTipificacion(
            @Valid Tipificacion tipificacion, @Context UriInfo uriInfo) {
        tipificacion = tipificacionService.persistTipificacion(tipificacion);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder().path(Long.toString(tipificacion.id));
        return Response.created(builder.build()).build();
    }

    //TODO PUT and DELETE
}

package ar.gov.agip.resource;

import ar.gov.agip.model.Impuesto;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.CompositeException;
import io.smallrye.mutiny.Uni;

@Path("impuesto")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class ImpuestoResource {

    private static final Logger LOGGER = Logger.getLogger(ImpuestoResource.class.getName());

    @GET
    public Uni<List<Impuesto>> get() {
        return Impuesto.listAll(Sort.by("nombre"));
    }

    @GET
    @Path("{id}")
    public Uni<Impuesto> getSingle(@RestPath Long id) {
        return Impuesto.findById(id);
    }

    @POST
    public Uni<Response> create(Impuesto impuesto) {
        if (impuesto == null || impuesto.id != null) {
            throw new WebApplicationException("No se puede enviar un id para el impuesto", 422);
        }

        return Panache.withTransaction(impuesto::persist)
                .replaceWith(Response.ok(impuesto).status(CREATED)::build);
    }

    @PUT
    @Path("{id}")
    public Uni<Response> update(@RestPath Long id, Impuesto impuesto) {
        if (impuesto == null || impuesto.nombre == null || impuesto.descripcion == null) {
            throw new WebApplicationException("Los datos del impuesto no pueden ser nulos", 422);
        }

        return Panache
                .withTransaction(() -> Impuesto.<Impuesto> findById(id)
                        .onItem().ifNotNull().invoke(entity -> {
                            entity.nombre= impuesto.nombre;
                            entity.descripcion = impuesto.descripcion;
                        })
                )
                .onItem().ifNotNull().transform(entity -> Response.ok(entity).build())
                .onItem().ifNull().continueWith(Response.ok().status(NOT_FOUND)::build);
    }

    @DELETE
    @Path("{id}")
    public Uni<Response> delete(@RestPath Long id) {
        return Panache.withTransaction(() -> Impuesto.deleteById(id))
                .map(deleted -> deleted
                        ? Response.ok().status(NO_CONTENT).build()
                        : Response.ok().status(NOT_FOUND).build());
    }

    /**
     * HTTP response de excepciones
     * <pre>
     * HTTP/1.1 422 Unprocessable Entity
     * Content-Length: 111
     * Content-Type: application/json
     *
     * {
     *     "code": 422,
     *     "error": "Los datos del impuesto no pueden ser nulos.",
     *     "exceptionType": "javax.ws.rs.WebApplicationException"
     * }
     * </pre>
     */
    @Provider
    public static class ErrorMapper implements ExceptionMapper<Exception> {

        @Inject
        ObjectMapper objectMapper;

        @Override
        public Response toResponse(Exception exception) {
            LOGGER.error("Error handler request", exception);

            Throwable throwable = exception;

            int code = 500;
            if (throwable instanceof WebApplicationException) {
                code = ((WebApplicationException) exception).getResponse().getStatus();
            }

            // This is a Mutiny exception and it happens, for example, when we try to insert a new
            // fruit but the name is already in the database
            if (throwable instanceof CompositeException) {
                throwable = ((CompositeException) throwable).getCause();
            }

            ObjectNode exceptionJson = objectMapper.createObjectNode();
            exceptionJson.put("exceptionType", throwable.getClass().getName());
            exceptionJson.put("code", code);

            if (exception.getMessage() != null) {
                exceptionJson.put("error", throwable.getMessage());
            }

            return Response.status(code)
                    .entity(exceptionJson)
                    .build();
        }

    }
}

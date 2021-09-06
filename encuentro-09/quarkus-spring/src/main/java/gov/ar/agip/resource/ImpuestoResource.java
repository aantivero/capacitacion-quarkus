package gov.ar.agip.resource;

import gov.ar.agip.model.Impuesto;
import gov.ar.agip.repository.ImpuestoRepository;
import gov.ar.agip.service.ImpuestoMessageProducer;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("impuesto")
public class ImpuestoResource {

    private final ImpuestoRepository impuestoRepository;

    private final ImpuestoMessageProducer impuestoMessageProducer;

    public ImpuestoResource(ImpuestoRepository impuestoRepository, ImpuestoMessageProducer impuestoMessageProducer) {
        this.impuestoRepository = impuestoRepository;
        this.impuestoMessageProducer = impuestoMessageProducer;
    }

    @GET
    @Path("/mensaje")
    @Produces("application/text-plain")
    public String getMensaje() {
        return impuestoMessageProducer.getHeaderMensaje();
    }

    @GET
    @Produces("application/json")
    public Iterable<Impuesto> findAll() {
        return impuestoRepository.findAll();
    }

    @GET
    @Path("/nombre/{nombre}")
    @Produces("application/json")
    public List<Impuesto> findByNombre(@PathParam String nombre) {
        return impuestoRepository.findByNombre(nombre);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam long id) {
        impuestoRepository.deleteById(id);
    }

    @POST
    @Path("/nombre/{nombre}/descripcion/{descripcion}")
    @Produces("application/json")
    public Impuesto create(@PathParam String nombre, @PathParam String descripcion) {
        return impuestoRepository.save(new Impuesto(nombre, descripcion));
    }

    @PUT
    @Path("/id/{id}/nombre/{nombre}/descripcion/{descripcion}")
    @Produces("application/json")
    public Impuesto update(@PathParam Long id, @PathParam String nombre, @PathParam String descripcion) {
        Optional<Impuesto> optional = impuestoRepository.findById(id);
        if (optional.isPresent()) {
            Impuesto impuesto = optional.get();
            impuesto.setNombre(nombre);
            impuesto.setDescripcion(descripcion);
            return impuestoRepository.save(impuesto);
        }
        throw new IllegalArgumentException("No se encuentra Impuesto con ID:" + id);
    }
}

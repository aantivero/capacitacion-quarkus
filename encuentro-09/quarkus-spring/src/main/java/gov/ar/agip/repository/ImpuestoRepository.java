package gov.ar.agip.repository;

import gov.ar.agip.model.Impuesto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ImpuestoRepository extends CrudRepository<Impuesto, Long> {

    List<Impuesto> findByNombre(String nombre);
}

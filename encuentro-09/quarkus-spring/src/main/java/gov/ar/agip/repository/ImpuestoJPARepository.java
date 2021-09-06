package gov.ar.agip.repository;

import gov.ar.agip.model.Impuesto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImpuestoJPARepository extends JpaRepository<Impuesto, Long> {
}

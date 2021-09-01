package arg.gov.agip.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Tipificacion")
@NamedQuery(name = "Tipificacion.findAll", query = "SELECT t FROM Tipificacion t ORDER BY t.codigo", hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
@Cacheable
public class Tipificacion extends PanacheEntity {
//nno es acosejable cachear mas de un query por entidad
    @NotNull
    @Column(unique = true)
    public Integer codigo;

    @NotNull
    @Column(length = 255)
    public String descripcion;

    //Builder ??? -> pedir al que instancia objeto los propiedades necesaris

}

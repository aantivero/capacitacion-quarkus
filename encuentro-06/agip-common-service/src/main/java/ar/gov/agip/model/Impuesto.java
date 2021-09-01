package ar.gov.agip.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="impuestos")
@Cacheable
public class Impuesto extends PanacheEntity {

    @Column(length=50)
    public String descripcion;

    @Column(length=50)
    public String nombre;
}

package ar.gov.agip;

import java.util.Objects;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@RegisterForReflection
@CsvRecord(separator = ",")
public class Impuesto {

    @DataField(pos = 1)
    private int id;
    @DataField(pos = 2)
    private String nombre;
    @DataField(pos = 3)
    private String descripcion;

    public Impuesto() {
    }

    public Impuesto(int id, String nombre, String descripcion) {

        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Impuesto impuesto = (Impuesto) o;
        return id == impuesto.id && Objects.equals(nombre, impuesto.nombre)
                && Objects.equals(descripcion, impuesto.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, descripcion);
    }
}

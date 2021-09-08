package ar.gov.agip.model;

public class Impuesto {

    public String nombre;
    public int taza;

    public Impuesto() {
    }

    public Impuesto(String nombre, int taza) {
        this.nombre = nombre;
        this.taza = taza;
    }

    @Override
    public String toString() {
        return "Impuesto{" +
                "nombre='" + nombre + '\'' +
                ", taza=" + taza +
                '}';
    }
}

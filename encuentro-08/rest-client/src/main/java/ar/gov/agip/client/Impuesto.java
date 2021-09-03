package ar.gov.agip.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Impuesto {

    public String descripcion;

    public String nombre;
}

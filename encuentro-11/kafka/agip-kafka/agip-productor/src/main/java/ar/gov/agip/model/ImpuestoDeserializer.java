package ar.gov.agip.model;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class ImpuestoDeserializer extends ObjectMapperDeserializer<Impuesto> {

    public ImpuestoDeserializer() {
        super(Impuesto.class);
    }
}

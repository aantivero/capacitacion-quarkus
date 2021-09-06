package gov.ar.agip.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ImpuestoMessageProducer {

    @Value("${impuesto.message}")
    String impuestoMessage;

    public String getHeaderMensaje() {
        return impuestoMessage;
    }
}

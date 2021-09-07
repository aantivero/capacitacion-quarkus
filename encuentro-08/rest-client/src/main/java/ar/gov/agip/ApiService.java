package ar.gov.agip;

//import org.eclipse.microprofile.reactive.messaging.Channel;
//import org.eclipse.microprofile.reactive.messaging.Emitter;
import ar.gov.agip.client.Impuesto;
import ar.gov.agip.client.ImpuestoService;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Random;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

@ApplicationScoped
@Transactional(SUPPORTS)
public class ApiService {

    private static final Logger LOGGER = Logger.getLogger(ApiService.class);

    @Inject
    @RestClient
    ImpuestoService impuestoService;

    @Fallback(fallbackMethod = "obtenerFallBackImpuestoByNombre")
    Impuesto getImpuestoByNombre(String nombre) {
        return impuestoService.getByNombre(nombre);
    }

    @Fallback(fallbackMethod = "obtenerFallBackImpuestoById")
    public Impuesto getImpuestoById(Long id) {
        return impuestoService.getById(id);
    }

    public Impuesto obtenerFallBackImpuestoByNombre(String nombre) {
        LOGGER.error("Llamada al fallback impuesto by nombre");
        Impuesto impuesto = new Impuesto();
        impuesto.descripcion = "ERROR";
        impuesto.nombre = "ERROR";
        return impuesto;
    }

    public Impuesto obtenerFallBackImpuestoById(Long id) {
        LOGGER.error("Llamada al fallback impuesto by id");
        Impuesto impuesto = new Impuesto();
        impuesto.descripcion = "ERROR";
        impuesto.nombre = "ERROR";
        return impuesto;
    }
}

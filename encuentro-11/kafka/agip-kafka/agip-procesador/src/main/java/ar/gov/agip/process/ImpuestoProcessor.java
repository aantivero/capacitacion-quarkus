package ar.gov.agip.process;

import java.util.Random;

import javax.enterprise.context.ApplicationScoped;

import ar.gov.agip.model.Impuesto;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import io.smallrye.reactive.messaging.annotations.Blocking;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ImpuestoProcessor {

    private static final Logger LOGGER = Logger.getLogger(ImpuestoProcessor.class);

    private Random random = new Random();

    @Incoming("requests")
    @Outgoing("impuestos")
    @Blocking
    public Impuesto process(String nombre) throws InterruptedException {
        LOGGER.info("Procesar el impuesto: " + nombre);
        Thread.sleep(1500);
        Impuesto impuesto = new Impuesto(nombre, random.nextInt(100));
        LOGGER.info("Procesado:" + impuesto);
        return impuesto;
    }
}

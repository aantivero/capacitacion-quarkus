package ar.gov.agip;

import com.github.dhiraj072.randomwordgenerator.RandomWordGenerator;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Session;
/**
 * Cada 5 segundos produce una palabra aleatoria
 */
@ApplicationScoped
public class Productor implements Runnable{

    private static final Logger LOGGER = Logger.getLogger(Productor.class);

    @Inject
    ConnectionFactory connectionFactory;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("Productor onStart");
        scheduler.scheduleWithFixedDelay(this, 0L, 5L, TimeUnit.SECONDS);
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("Productor onStop");
        scheduler.shutdown();
    }

    @Override
    public void run() {
        String randomWord = RandomWordGenerator.getRandomWord();
        LOGGER.info("Word Random " + randomWord);
        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            context.createProducer().send(context.createQueue("agip-random"), randomWord);
        }
    }
}

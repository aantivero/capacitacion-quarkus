package ar.gov.agip;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;

/**
 * Consumidor de la JMS
 */
@ApplicationScoped
public class Consumidor implements Runnable{

    private static final Logger LOGGER = Logger.getLogger(Consumidor.class);

    @Inject
    ConnectionFactory connectionFactory;

    private final ExecutorService scheduler = Executors.newSingleThreadExecutor();

    private volatile String randomWord;

    public String getRandomWord() {
        return randomWord;
    }

    void onStart(@Observes StartupEvent ev) {
        scheduler.submit(this);
    }

    void onStop(@Observes ShutdownEvent ev) {
        scheduler.shutdown();
    }

    @Override
    public void run() {
        try (JMSContext context = connectionFactory.createContext(Session.AUTO_ACKNOWLEDGE)) {
            JMSConsumer consumer = context.createConsumer(context.createQueue("agip-random"));
            while (true) {
                Message message = consumer.receive();
                if (message == null) {
                    return;
                }
                randomWord = message.getBody(String.class);
                LOGGER.info("RandomWord:" + randomWord);
            }
        } catch (JMSException e) {
            LOGGER.error(e);
            throw new RuntimeException(e);
        }
    }
}

package ar.gov.agip;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;
import org.apache.commons.io.FileUtils;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.jboss.logging.Logger;

import static org.awaitility.Awaitility.await;


public class ArtemisTestResource implements QuarkusTestResourceLifecycleManager{

    private static final Logger LOGGER = Logger.getLogger(ArtemisTestResource.class);

    private EmbeddedActiveMQ embedded;

    @Override
    public Map<String, String> start() {
        try {
            FileUtils.deleteDirectory(Paths.get("./target/artemis").toFile());
            embedded = new EmbeddedActiveMQ();
            embedded.start();
            await().until(() -> embedded.getActiveMQServer().isActive()  && embedded.getActiveMQServer().isStarted());
            LOGGER.info("Artemis server started");
        } catch (Exception e) {
            throw new RuntimeException("Could not start embedded ActiveMQ server", e);
        }
        return Collections.emptyMap();
    }

    @Override
    public void stop() {
        try {
            embedded.stop();
            LOGGER.info("Artemis server stopped");
        } catch (Exception e) {
            throw new RuntimeException("Could not stop embedded ActiveMQ server", e);
        }
    }
}

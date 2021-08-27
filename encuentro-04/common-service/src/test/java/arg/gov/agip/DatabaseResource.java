package arg.gov.agip;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Collections;
import java.util.Map;

/**
 * Recurso para inicializar una BBDD devolviendo la configuración de la misma
 */
public class DatabaseResource implements QuarkusTestResourceLifecycleManager {

    private static final PostgreSQLContainer DATABASE = new PostgreSQLContainer<>("postgres:10.5")
        .withDatabaseName("common_database")
        .withUsername("agip")
        .withPassword("agip")
        .withExposedPorts(5432);
    @Override
    public Map<String, String> start() {
        DATABASE.start();
        return Collections.singletonMap("quarkus.datasource.jdbc.url", DATABASE.getJdbcUrl());
    }

    @Override
    public void stop() {
        DATABASE.stop();
    }
}

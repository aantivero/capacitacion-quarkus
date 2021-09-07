package ar.gov.agip.health;

import ar.gov.agip.ApiResource;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.Liveness;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.jboss.logging.Logger;

@Liveness
@ApplicationScoped
public class ApiHealthCheck implements HealthCheck {

    private static final Logger LOGGER = Logger.getLogger(ApiHealthCheck.class);

    @Inject
    ApiResource apiResource;

    @Override
    public HealthCheckResponse call() {
        LOGGER.info("CALL HEALTH CHECK");
        apiResource.checkApi();
        return HealthCheckResponse.named("Ping API REST Endpoint").up().build();
    }
}

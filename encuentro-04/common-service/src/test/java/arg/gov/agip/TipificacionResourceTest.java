package arg.gov.agip;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;

@QuarkusTest
@QuarkusTestResource(DatabaseResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TipificacionResourceTest {

    @Test
    public void shouldNotGetUnknownTipificacion() {
        Long randomId = new Random().nextLong();
        given()
                .pathParam("id", randomId)
                .when().get("/api/agip/common/tipificacion/{id}")
                .then()
                .statusCode(NO_CONTENT.getStatusCode());
    }
}

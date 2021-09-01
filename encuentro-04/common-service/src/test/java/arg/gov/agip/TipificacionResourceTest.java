package arg.gov.agip;

import arg.gov.agip.model.Tipificacion;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import static javax.ws.rs.core.HttpHeaders.*;
import static javax.ws.rs.core.Response.Status.*;

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

    @Test
    public void shouldAddValidaTipificacion() {
        //dado - cuando - entonces
        Tipificacion tipificacion = new Tipificacion();
        tipificacion.codigo = 123;
        tipificacion.descripcion = "test";

        given()
                .body(tipificacion)
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .header(ACCEPT, APPLICATION_JSON)
                .when()
                .post("/api/agip/common/tipificacion")
                .then()
                .statusCode(CREATED.getStatusCode());
    }
}

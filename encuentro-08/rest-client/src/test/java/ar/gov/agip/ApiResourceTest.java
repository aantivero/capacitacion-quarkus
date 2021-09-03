package ar.gov.agip;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import static javax.ws.rs.core.HttpHeaders.CONTENT_TYPE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@QuarkusTest
public class ApiResourceTest {

    @Test
    public void testCheckEndpoint() {
        given()
          .when().get("/api")
          .then()
             .statusCode(200)
             .body(is("Check API REST CLIENT"));
    }

    @Test
    void shouldGetImpuestoByNombre() {
        given()
                .when().get("/api/impuesto/nombre/BB")
                .then()
                .statusCode(200)
                .header(CONTENT_TYPE, APPLICATION_JSON);
    }

    @Test
    void shouldGetImpuestoByID() {
        given()
                .when().get("/api/impuesto/id/1")
                .then()
                .statusCode(200)
                .header(CONTENT_TYPE, APPLICATION_JSON);
    }
}
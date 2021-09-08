package ar.gov.agip;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ImpuestoResourceTest {

    @Test
    public void testImpuestoEvent() {

        String body = given()
                .when()
                .post("/impuestos/request")
                .then()
                .statusCode(200)
                .extract().body()
                .asString();
        assertDoesNotThrow(() -> UUID.fromString(body));
    }
}

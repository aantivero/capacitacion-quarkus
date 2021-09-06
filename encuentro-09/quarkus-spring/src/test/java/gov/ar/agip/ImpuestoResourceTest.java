package gov.ar.agip;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsNot.not;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ImpuestoResourceTest {

    @Test
    void testImpuestos() {
        //obtener todos
        given()
                .when().get("/impuesto")
                .then()
                .statusCode(200)
                .body(
                        containsString("automotor"),
                        containsString("inmobiliario"),
                        containsString("comercio"));

        //borrar automotor
        given()
                .when().delete("/impuesto/1")
                .then()
                .statusCode(204);

        //todos menos automotor
        given()
                .when().get("/impuesto")
                .then()
                .statusCode(200)
                .body(
                        not(containsString("automotor")),
                        containsString("inmobiliario"),
                        containsString("comercio"));

        //crear nuevo impuesto
        given()
                .when().post("/impuesto/nombre/IVA/descripcion/DescripcionIva")
                .then()
                .statusCode(200)
                .body(containsString("IVA"))
                .body("id", notNullValue())
                .extract().body().jsonPath().getString("id");

        //todos
        given()
                .when().get("/impuesto")
                .then()
                .statusCode(200)
                .body(
                        not(containsString("automotor")),
                        containsString("inmobiliario"),
                        containsString("comercio"),
                        containsString("IVA"));
    }

    @Test
    void testCheckMensaje() {

        given()
                .when().get("/impuesto/mensaje")
                .then()
                .statusCode(200)
                .body(
                        containsString("Bienvenido AGIP"));
    }
}

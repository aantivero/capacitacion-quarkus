package ar.gov.agip;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.text.IsEmptyString.emptyString;

@QuarkusTest
public class ImpuestoResourceTest {

    @Test
    public void testCRUDImpuestos() {
        //Lista todos los impuestos iniciales
        Response response = given()
                .when()
                .get("/impuesto")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();
        assertThat(response.jsonPath().getList("nombre")).containsExactlyInAnyOrder("automotor", "inmobiliario");

        // actualizar inmobiliario
        given()
                .when()
                .body("{\"nombre\" : \"inmobiliarioDos\", \"descripcion\" : \"inmobiliarioDescripcionDos\"}")
                .contentType("application/json")
                .put("/impuesto/2")
                .then()
                .statusCode(200)
                .body(
                        containsString("\"id\":"),
                        containsString("\"nombre\":\"inmobiliarioDos\""));

        //listar todos y verificar que el modificado exista
        response = given()
                .when()
                .get("/impuesto")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();
        assertThat(response.jsonPath().getList("nombre"))
                .containsExactlyInAnyOrder("automotor", "inmobiliarioDos");

        //borrar inmobiliario:
        given()
                .when()
                .delete("/impuesto/2")
                .then()
                .statusCode(204);

        //verificar que el inmobiliario no exista
        response = given()
                .when()
                .get("/impuesto")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();
        assertThat(response.jsonPath().getList("nombre"))
                .containsExactlyInAnyOrder("automotor");

        //Crear impuesto inmobiliario:
        given()
                .when()
                .body("{\"nombre\" : \"inmobiliario\", \"descripcion\" : \"descripcion inmobiliario\"}")
                .contentType("application/json")
                .post("/impuesto")
                .then()
                .statusCode(201)
                .body(
                        containsString("\"id\":"),
                        containsString("\"nombre\":\"inmobiliario\""));

        //listar todos
        response = given()
                .when()
                .get("/impuesto")
                .then()
                .statusCode(200)
                .extract().response();
        assertThat(response.jsonPath().getList("nombre"))
                .containsExactlyInAnyOrder("automotor", "inmobiliario");
    }

    @Test
    public void testEntityNotFoundForDelete() {
        given()
                .when()
                .delete("/impuesto/9999")
                .then()
                .statusCode(404)
                .body(emptyString());
    }

    @Test
    public void testEntityNotFoundForUpdate() {
        given()
                .when()
                .body("{\"nombre\" : \"inmobiliarioDos\", \"descripcion\" : \"inmobiliarioDescripcionDos\"}")
                .contentType("application/json")
                .put("/impuesto/6666")
                .then()
                .statusCode(404)
                .body(emptyString());
    }
}

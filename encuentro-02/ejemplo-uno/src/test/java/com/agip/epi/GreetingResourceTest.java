package com.agip.epi;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/hello")
          .then()
             .statusCode(200)
             .body(is("Hello RESTEasy"));
    }

    @Test
    public void testPartidaEndpoit() {
        given()
                .pathParam("cuit", "00000")
                .when().get("/hello/partida/{cuit}")
                .then()
                .statusCode(200)
                .body(is("Partida al cuit:00000"));
    }

}
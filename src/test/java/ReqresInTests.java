import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class ReqresInTests extends TestBase {
    @Test
    void checkSingleUserDataTest() {
        given()
                .when()
                .get("/users/2")
                .then()
                .statusCode(200)
                .body("data.id", equalTo(2),
                        "data.email", equalTo("janet.weaver@reqres.in"),
                        "data.first_name", equalTo("Janet"),
                        "data.last_name", equalTo("Weaver")
                );
    }

    @Test
    void UpdateUserDataTest() {
        given()
                .body("{\"name\": \"morpheus\", \"job\": \"leader\"}")
                .contentType(JSON)
                .when()
                .put("/users/2")
                .then()
                .statusCode(200);

    }

    @Test
    void checkNotExistentUserTest() {
        given()
                .when()
                .get("/users/0")
                .then()
                .statusCode(404);
    }

    @Test
    void createUserTest() {
        given()
                .body("{\"name\": \"morpheus\",\"job\": \"leader\"}")
                .contentType(JSON)
                .when()
                .post("/users")
                .then()
                .statusCode(201);
    }

    @Test
    void deleteUserTest() {
        given()
                .when()
                .delete("/users/2")
                .then()
                .statusCode(204);
    }

    @Test
    void checkSuccessfulRegisterTest() {
        given()
                .body("{ \"email\": \"eve.holt@reqres.in\",\"password\": \"pistol\"}")
                .contentType(JSON)
                .when()
                .post("/register")
                .then()
                .statusCode(200)
                .body("id", is(4),
                        "token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void checkUnsuccessfulRegisterTest() {
        given()
                .body("{\"email\": \"sydney@fife\"}")
                .contentType(JSON)
                .when()
                .post("/register")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}

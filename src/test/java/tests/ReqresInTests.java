package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import models.lombok.BodyData;
import models.pojo.UserData;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.Specs.*;

@Tag("Api")
public class ReqresInTests extends TestBase {
    @Test
    void listUsersTest() {
        given()
                .spec(request)
                .when()
                .get("/users?page=2")
                .then()
                .spec(responseSucces)
                .log().body();
    }

    @Test
    void listSingleUserTest() {

        given()
                .spec(request)
                .when()
                .get("/users/2")
                .then()
                .spec(responseSucces)
                .body("data.first_name", is("Janet"));
    }

    @Test
    void createUserTest() {
        String body = "{ \"name\": \"morpheus\", " +
                "\"job\": \"leader\" }";
        given()
                .filter(withCustomTemplates())
                .spec(request)
                .body(body)
                .when()
                .post("/api/users")
                .then()
                .spec(responseCreate)
                .body("name", is("morpheus"));
    }

    @Test
    void loginUnsuccesfullTest() {
        String body = "{ \"email\": \"peter@klaven\" }";
        given()
                .spec(request)
                .body(body)
                .when()
                .post("/login")
                .then()
                .spec(responseUnsuccesLogin)
                .body("error", is("Missing password"));
    }

    @Test
    void deleteUserTest() {
        given()
                .when()
                .delete("https://reqres.in/api/users2")
                .then()
                .spec(responseDelete);
    }

    @Test
    void singleUserWithModel() {
        UserData data = given()
                .spec(request)
                .when()
                .get("/users/2")
                .then()
                .spec(responseSucces)
                .log().body()
                .extract().as(UserData.class);

        assertEquals(2, data.getUser().getId());
    }

    @Test
    void singleUserWithLombok() {
        UserData data = given()
                .spec(request)
                .when()
                .get("/users/2")
                .then()
                .spec(responseSucces)
                .log().body()
                .extract().as(UserData.class);
        assertEquals(2, data.getUser().getId());
    }

    @Test
    void UsersWithLombok() {
        BodyData data = given()
                .spec(request)
                .when()
                .get("/users/2")
                .then()
                .spec(responseSucces)
                .log().body()
                .extract().as(BodyData.class);
        assertEquals(null, data.getBody().getUserData());
    }


    @Test
    void ListUserTest() {
        given()
                .spec(request)
                .body("User")
                .when()
                .get("/users?page=2")
                .then()
                .spec(responseSucces)
                .log().body();
    }
}
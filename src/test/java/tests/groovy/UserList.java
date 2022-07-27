package tests.groovy;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static specs.Specs.request;

public class UserList {
    public void checkListOfUsersByGroovy() {
        // @formatter:off
        given()
                .spec(request)
                .when()
                .get("/users")
                .then()
                .log().body()
                .body("data.findAll{it.user =~/.*?@reqres.in/}.user.flatten()",
                        hasItem("id"));
    }
}

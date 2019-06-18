package my.api.rest.test;

import com.jayway.restassured.http.ContentType;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

public class ReqResPatch {

    @Test
    public void validDataTest() {
        int objectId = 5;
        JSONObject requestBody = new JSONObject();
        requestBody
                .put("name", "hello, world")
                .put("job", "hello, job");

        System.out.println("--------------------------\nTest\nRequest: \n" + requestBody.toString() + "\nResponse:");

        given()
                .when()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody.toString())
                .patch("https://reqres.in/api/users/" + objectId)
                .then()
                .log()
                .body()
                .assertThat()
                .statusCode(200)
                .log()
                .status();
    }

    @Test
    public void emptyBodyTest() {
        int objectId = 5;
        String requestBody = "";

        System.out.println("--------------------------\nTest\nRequest: \n" + requestBody.toString() + "\nResponse:");

        given()
                .when()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody)
                .patch("https://reqres.in/api/users/" + objectId)
                .then()
                .log()
                .body()
                .assertThat()
                .statusCode(200)
                .log()
                .status();
    }

    @Test
    public void noObjectIdTest() {
        JSONObject requestBody = new JSONObject();
        requestBody
                .put("name", "hello, world")
                .put("job", "hello, job");

        System.out.println("--------------------------\nTest\nRequest: \n" + requestBody.toString() + "\nResponse:");

        given()
                .when()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestBody.toString())
                .patch("https://reqres.in/api/users/")
                .then()
                .log()
                .body()
                .assertThat()
                .statusCode(200)
                .log()
                .status();
    }

}

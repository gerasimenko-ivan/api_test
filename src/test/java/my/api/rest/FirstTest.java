package my.api.rest;

import com.jayway.restassured.http.ContentType;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

public class FirstTest {

    @Test
    public void doRegisterTest() {
        JSONObject jsonObject = new JSONObject();
        jsonObject
                .append("email", "milli@mail.ru")
                .append("name", "Машенька")
                .append("password", "1");

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .post(" http://users.bugred.ru/tasks/rest/doregister");
    }
}

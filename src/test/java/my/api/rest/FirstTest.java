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
                .append("email", "1234milli@mail.ru")
                .append("name", "1223Машенька")
                .append("password", "1");

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .post(" http://users.bugred.ru/tasks/rest/doregister");
    }

    @Test
    public void doRegisterLogResponseTest() {
        JSONObject jsonObject = new JSONObject();
        jsonObject
                .put("email", "Hello.Rest.The.World@earth.com")
                .put("name", "Hello Rester")
                .put("password", "1");

        System.out.println(jsonObject.toString());
        given()
                .when()
                .contentType(ContentType.JSON)
                .body(jsonObject.toString())
                .post(" http://users.bugred.ru/tasks/rest/doregister")
                .print();
    }

    @Test
    public void doRegisterTooSimpleTest() {


        given()
                .when()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"email\": \"milli3@mail.ru\",\n" +
                        "    \"name\": \"Машенька3\",\n" +
                        "    \"password\": \"1\"\n" +
                        "}")
                .post(" http://users.bugred.ru/tasks/rest/doregister")
                .print();
    }
}

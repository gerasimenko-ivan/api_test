package my.api.rest.test;

import com.jayway.restassured.http.ContentType;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class FirstTest {

    @Test
    public void doRegisterTest() {
        JSONObject jsonObject = new JSONObject();
        jsonObject
                .put("email", "1234milli@mail.ru")
                .put("name", "1223Машенька")
                .put("password", "1");

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


    @Test
    public void doRegister_SuccessTest() {
        JSONObject jsonObject = new JSONObject();
        String nameKey = "name";
        String nameValue = "Hello Rester5";
        String emailKey = "email";
        String emailValue = "Hello.Rest.The.World5@earth.com";
        jsonObject
                .put(emailKey, emailValue)
                .put(nameKey, nameValue)
                .put("password", "1");
        System.out.println(jsonObject.toString());

        given()
                .when()
                    .contentType(ContentType.JSON)
                    .body(jsonObject.toString())
                .post(" http://users.bugred.ru/tasks/rest/doregister")

                .then()
                    .log()
                    .body()
                    .assertThat()
                    .body(nameKey, equalTo(nameValue))
                    .body("avatar", equalTo("http://users.bugred.ru//tmp/default_avatar.jpg"))
                    .body("birthday", equalTo(0))
                    .body(emailKey, equalTo(emailValue));

    }


}

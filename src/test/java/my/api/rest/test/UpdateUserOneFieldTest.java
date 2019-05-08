package my.api.rest.test;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import my.api.rest.dataobjects.User;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.testng.Assert.fail;

public class UpdateUserOneFieldTest {
    private static User testUser =
            new User()
                    .setName("hw_user")
                    .setEmail("hello.world.user@mail.test.ts")
                    .setPassword("mY 5UpEr 5EcrEt pA55w0r:-D");

    @BeforeClass
    public void testUserForUpdateCreationOrCheckExists() {


        JSONObject testUserAsJson = new JSONObject();
        String nameKey = "name";
        String emailKey = "email";
        testUserAsJson
                .put(emailKey, testUser.email)
                .put(nameKey, testUser.name)
                .put("password", testUser.password);
        System.out.println("Request: \n" + testUserAsJson.toString() + "\n");

        Response response = given()
                .when()
                .contentType(ContentType.JSON)
                .body(testUserAsJson.toString())
                .post(" http://users.bugred.ru/tasks/rest/doregister");

        assertThat("Check response code", response.statusCode(), equalTo(200));

        System.out.println("Response:\n" + response.getBody().asString());
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());

        if (jsonResponse.has("type") && jsonResponse.has("message")) {
            assertThat(jsonResponse.get("type").toString(), equalTo("error"));
            assertThat(jsonResponse.get("message").toString(), containsString(testUser.email));
        } else if (jsonResponse.has(nameKey) && jsonResponse.has(emailKey)) {
            assertThat(jsonResponse.get(nameKey).toString(), equalTo(testUser.name));
            assertThat(jsonResponse.get(emailKey).toString(), equalTo(testUser.email));
        } else {
            fail("Unexpected response body contents (expect created user info OR error message that user exists)");
        }
    }


    @Test(dataProvider = "getValidUserInfoForUpdate")
    public void updateUserOneField_validInputDataTest(User user) {

    }

    @DataProvider
    public Object[] getValidUserInfoForUpdate() {
        Random rnd = new Random();

        return new Object[]
                {
                        // different types of valid emails:
                        new User().setName("ABC" + rnd.nextInt(1000000000)).setEmail("validemail" + rnd.nextInt(1000000000) + "@test.ts").setPassword("1")
                };
    }

}

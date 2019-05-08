package my.api.rest.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import my.api.rest.dataobjects.User;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
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
    public void updateUserOneField_validInputDataTest(User userWithOneNewFieldValue) {
        System.out.println("\n----------------------------\nTest");
        String userWithOneNewFieldValue_asJsonString = null;
        try {
            userWithOneNewFieldValue_asJsonString = new ObjectMapper().writeValueAsString(userWithOneNewFieldValue);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        JSONObject userWithOneNewFieldValue_asJson = new JSONObject(userWithOneNewFieldValue_asJsonString);
        Iterator<String> keys = userWithOneNewFieldValue_asJson.keys();
        String fieldName = "";
        String fieldValue = "";
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = userWithOneNewFieldValue_asJson.get(key);
            if (!value.toString().equals("null")) {
                fieldName = key;
                fieldValue = value.toString();
            }
        }

        JSONObject userUpdateAsJson = new JSONObject();
        String emailKey = "email";
        userUpdateAsJson
                .put(emailKey, testUser.email)
                .put("field", fieldName)
                .put("value", fieldValue);
        System.out.println("\nRequest: \n" + userUpdateAsJson.toString() + "\nResponse:");

        given()
                .when()
                .contentType(ContentType.JSON)
                .body(userUpdateAsJson.toString())
                .post(" http://users.bugred.ru/tasks/rest/useronefield")
                .then()
                .log()
                .body()
                .assertThat()
                .statusCode(equalTo(200))
                .body("type", equalTo("error"))
                .body("message", equalTo("Поле " + fieldName + " успешно изменено на " + fieldValue + " у пользователя с email " + testUser.email));


    }

    @DataProvider
    public Object[] getValidUserInfoForUpdate() {
        Random rnd = new Random();

        return new Object[]
                {
                        new User().setSurname1("new Surname1 " + rnd.nextInt(100000)),
                        new User().setName1("new name1 " + rnd.nextInt(100000)),
                        new User().setFathername1("new Fathername1 " + rnd.nextInt(100000)),

                        new User().setHobby("new Hobby " + rnd.nextInt(100000)),
                        new User().setCat("new Cat " + rnd.nextInt(100000)),
                        new User().setDog("new Dog " + rnd.nextInt(100000)),
                        new User().setParrot("new Parrot " + rnd.nextInt(100000)),
                        new User().setCavy("new Cavy " + rnd.nextInt(100000)),
                        new User().setHamster("my Hamster name " + rnd.nextInt(100000)),
                        new User().setSquirrel("my Squirrel name " + rnd.nextInt(100000)),
                        new User().setPhone("+" + rnd.nextInt(10) + "(" + rnd.nextInt(1000) + ")" + rnd.nextInt(1000) + "-" + rnd.nextInt(100000) + " (add./доб. " + rnd.nextInt(100000) + ")"),
                        new User().setAdres("my Adres bla-bla-bla-bla-bla bla-bla-bla-bla-bla " + rnd.nextInt(100000)),
                        new User().setGender(rnd.nextInt(2) == 0 ? "m" : "f"),
                        new User().setBirthday(String.format("%02d", (1 + rnd.nextInt(31))) + "." + String.format("%02d", 1 + rnd.nextInt(12)) + "." + (1900 + rnd.nextInt(200))),
                        new User().setDate_start(String.format("%02d", (1 + rnd.nextInt(31))) + "." + String.format("%02d", 1 + rnd.nextInt(12)) + "." + (1900 + rnd.nextInt(200))),
                        new User().setInn(String.format("%06d", rnd.nextInt(1000000)) + String.format("%06d", rnd.nextInt(1000000)))
                };
    }

}

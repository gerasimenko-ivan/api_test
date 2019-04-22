package my.api.rest.test;

import com.jayway.restassured.http.ContentType;
import my.api.rest.dataobjects.User;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Random;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class DoRegisterTest {

    @Test(dataProvider = "getValidUserToRegister")
    public void doRegister_SuccessTest(User user) {
        JSONObject jsonObject = new JSONObject();
        String nameKey = "name";
        String emailKey = "email";
        jsonObject
                .put(emailKey, user.email)
                .put(nameKey, user.name)
                .put("password", user.password);
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
                .body(nameKey, equalTo(user.name))
                .body("avatar", equalTo("http://users.bugred.ru//tmp/default_avatar.jpg"))
                .body("birthday", equalTo(0))
                .body(emailKey, equalTo(user.email));

    }

    @DataProvider
    public Object[] getValidUserToRegister() {
        Random rnd = new Random();

        return new Object[]
                {
                        // different types of valid emails:
                        new User().setName("ABC" + rnd.nextInt(1000000000)).setEmail("validemail" + rnd.nextInt(1000000000) + "@test.ts").setPassword("1"),
                        new User().setName("ABC" + rnd.nextInt(1000000000)).setEmail("valid.email." + rnd.nextInt(1000000000) + "@test.ts").setPassword("1"),
                        new User().setName("ABC" + rnd.nextInt(1000000000)).setEmail("valid_email_" + rnd.nextInt(1000000000) + "@test.ts").setPassword("1"),
                        new User().setName("ABC" + rnd.nextInt(1000000000)).setEmail("validemail." + rnd.nextInt(1000000000) + "@subdomain.subdomain.test.ts").setPassword("1"),
                        new User().setName("ABC" + rnd.nextInt(1000000000)).setEmail("validemail+" + rnd.nextInt(1000000000) + "@test.ts").setPassword("1"),
                        new User().setName("ABC" + rnd.nextInt(1000000000)).setEmail("validemail+" + rnd.nextInt(1000000000) + "@123.255.0.1").setPassword("1"),
                        new User().setName("ABC" + rnd.nextInt(1000000000)).setEmail("validemail+" + rnd.nextInt(1000000000) + "@[123.255.0.1]").setPassword("1"),
                        new User().setName("ABC" + rnd.nextInt(1000000000)).setEmail("\"valid\"email+" + rnd.nextInt(1000000000) + "@test.ts").setPassword("1"),
                        new User().setName("ABC" + rnd.nextInt(1000000000)).setEmail(rnd.nextInt(1000000000) + rnd.nextInt(1000000000) + "@test.ts").setPassword("1"),
                        new User().setName("ABC" + rnd.nextInt(1000000000)).setEmail("validemail." + rnd.nextInt(1000000000) + "@subdomain-subdomain-test.ts").setPassword("1"),
                        new User().setName("ABC" + rnd.nextInt(1000000000)).setEmail("validemail." + rnd.nextInt(1000000000) + "@test.name").setPassword("1"),      // 	.name is valid Top Level Domain name
                        new User().setName("ABC" + rnd.nextInt(1000000000)).setEmail("validemail." + rnd.nextInt(1000000000) + "@test.co.ts").setPassword("1"),     //  Dot in Top Level Domain name also considered valid (use co.ts as example here)
                        new User().setName("ABC" + rnd.nextInt(1000000000)).setEmail("valid-email-" + rnd.nextInt(1000000000) + "@test.ts").setPassword("1")
                };
    }
}

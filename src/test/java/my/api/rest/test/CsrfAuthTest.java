package my.api.rest.test;

import com.jayway.restassured.authentication.FormAuthConfig;
import com.jayway.restassured.response.Response;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.authentication.FormAuthConfig.formAuthConfig;

public class CsrfAuthTest {


    @Test
    public void getInitialCsrfTokenTest() {
        Response response = given()
                .when()
                .get("https://xxxxxxx.ru/admin/");

        System.out.println("Response:\n" + response.getBody().asString());
        System.out.println("csrftoken: " + response.getCookie("csrftoken"));
    }

    @Test
    public void loginTest() {
        Response response = given()
                .when()
                .get("https://xxxxxxxxxx.ru/admin/");

        System.out.println("Response:\n" + response.getBody().asString());
        String csrftoken = response.getCookie("csrftoken");
        System.out.println("csrftoken: " + csrftoken);

        Response response2 = given()
                .when()
                .header("Referer", "https://xxxxxxxxxx/admin/login/?next=/admin/")
                .header("Connection", "keep-alive")
                .header("Content-Type", "application/x-www-form-urlencoded")
                //.header("", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                //.cookie("csrftoken=3m3KTii1sgp0CumlGIQgy7UTAzTnCq8j")
                .header("Cookie", "_ym_uid=1536224714252300321; _ym_d=1552573193; csrftoken=" + csrftoken)
                .formParam("username", "xxxxxx")
                .formParam("password", "xxxxxxx")
                .formParam("next", "/admin/")
                .formParam("csrfmiddlewaretoken", csrftoken)
                .post("https://xxxxxxxxxxx.ru/admin/login/?next=/admin/");

        System.out.println("Response:\n" + response2.getBody().asString());
        System.out.println("csrftoken: " + response2.getCookie("csrftoken"));
        System.out.println("sessionid: " + response2.getCookie("sessionid"));
    }

    @Test
    public void loginWithRestAssuredCsrfTest() {
        given()
                .when()
                .auth().form("xxxxx", "xxxxxx", formAuthConfig().withAutoDetectionOfCsrf())
                .get("https://xxxxxxxxxxx.ru/admin/")
                .then()
                .log()
                .body();
    }

    @Test
    public void test() {
        given()
                .when()
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .header("Cookie", "mos_id=CllGxlr77jt/Mx05PYI4AgA=; _ym_uid=1536224714252300321; _ym_d=1552573193; sessionid=p8cnrshhzwfjefx7ev8pyd34exyqcylj; csrftoken=" + "qIXt60oAT8B8lvSckdCh8LVbKMrcDDQA")
                .get("https://xxxxxxxxxxx.ru/admin/admin_app/role/3/change/")
                .then()
                .log()
                .body();
    }

    @Test
    public void logout() {
        given()
                .when()
                .get("https://xxxxxxxxxxxxx.ru/admin/logout/")
                .then()
                .log()
                .body();
    }

}

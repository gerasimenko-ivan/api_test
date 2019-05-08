package my.api.rest.test;

import com.jayway.restassured.http.ContentType;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class BuggyTest {


    @Test
    public void suggestFio_SuccessTest() {
        JSONObject jsonObject = new JSONObject();
        String queryKey = "query";
        String fioSuggest = "Мир";
        jsonObject
                .put(queryKey, fioSuggest)
                .put("count", 3);
        System.out.println("Request: \n" + jsonObject.toString() + "\nResponse:");

        given()
                .when()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(jsonObject.toString())
                .post("http://85.143.174.131:18080/suggestions/api/4_1/rs/suggest/fio")
                .then()
                //.log()
                .assertThat()
                //.body(matchesJsonSchemaInClasspath("C:\\Users\\gis\\IdeaProjects\\api_test\\src\\test\\java\\my\\api\\rest\\test\\suggestionFio.json"))
                .body(matchesJsonSchemaInClasspath("suggestionFio.json"))
                .log();


    }
}

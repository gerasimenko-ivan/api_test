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

public class MagicSearch {

    @BeforeClass
    public void createTestUsers() {
        // we need:
        // 1 user with 1 company (& 1 user with 2+ companies) - 231 SC
        // 2/+/ users (without company(ies)) names: testStatusCodeUser12345678901234567890END1, testStatusCodeUser12345678901234567890END2 - 231, 232 SC
        // 2/+/ companies (without user(s)) names: testStatusCodeCompany12345678901234567890END1, testStatusCodeCompany12345678901234567890END2 - 234 SC
        //

        for (User testUser : testUsers)
            createUser(testUser);
    }

    private User userNoCompanies = new User().setName("hello12345678901234567890END").setEmail("hello12345678901234567890END@mail.ml").setPassword("1");
    private User[] testUsers = new User[] {
            userNoCompanies
    };

    private void createUser(User testUser) {
        JSONObject testUserAsJson = new JSONObject();
        String nameKey = "name";
        String emailKey = "email";
        testUserAsJson
                .put(emailKey, testUser.email)
                .put(nameKey, testUser.name)
                .put("password", testUser.password);
        System.out.println("Request: \n" + testUserAsJson.toString() + "\n");

        Response response = given()
                .when()                         //.accept(ContentType.XML) - response content type
                .contentType(ContentType.JSON)  // request content type
                .body(testUserAsJson.toString())
                .post("http://users.bugred.ru/tasks/rest/doregister");

        assertThat("Check response code", response.statusCode(), equalTo(200));

        System.out.println("Response:\n" + response.getBody().asString());
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());

        if (jsonResponse.has("type") && jsonResponse.has("message")) {
            assertThat(jsonResponse.get("type").toString(), equalTo("error"));
            assertThat(jsonResponse.get("message").toString(), containsString(testUser.name));
        } else if (jsonResponse.has(nameKey) && jsonResponse.has(emailKey)) {
            assertThat(jsonResponse.get(nameKey).toString(), equalTo(testUser.name));
            assertThat(jsonResponse.get(emailKey).toString(), equalTo(testUser.email));
        } else {
            fail("Unexpected response body contents (expect created user info OR error message that user exists)");
        }
    }

    @BeforeClass
    public void createTestCompanies() {
        // we need:
        // 1 user with 1 company (& 1 user with 2+ companies) - 231 SC
        // 2/+/ users (without company(ies)) names: testStatusCodeUser12345678901234567890END1, testStatusCodeUser12345678901234567890END2 - 231, 232 SC
        // 2/+/ companies (without user(s)) names: testStatusCodeCompany12345678901234567890END1, testStatusCodeCompany12345678901234567890END2 - 234 SC
        //


    }

    ////// SUCCESSFUL SEARCH

    @Test
    public void checkStatusCode230_nothingFound() {
        Random rnd = new Random();
        JSONObject userDoesNotExist =
                new JSONObject()
                        .put("query", "abracadabra" + rnd.nextInt(1000000000) + rnd.nextInt(1000000000) + rnd.nextInt(1000000000) + rnd.nextInt(1000000000) + rnd.nextInt(1000000000) + rnd.nextInt(1000000000) + rnd.nextInt(1000000000) + rnd.nextInt(1000000000) + rnd.nextInt(1000000000));

        magicSearchPostRequestWithStatusCodeCheck(userDoesNotExist, 230);
    }

    @Test
    public void checkStatusCode231_oneUserFound() {
        // 1 user no comp
        // 1 user - 1 comp in this user ?
        // ? 1 user - 2 comp in this user

    }

    @Test
    public void checkStatusCode232_twoUserPlusAndNoCompaniesFound() {
        // 2 users no comp
    }


    //// CLIENT ERRORS

    @Test
    public void checkStatusCode455_noQueryParam() {
        JSONObject userDoesNotExist =
                new JSONObject()
                        .put("partyType", JSONObject.NULL)
                        .put("fullSimilarity", JSONObject.NULL)
                        .put("taskStatus", JSONObject.NULL)
                        .put("include", JSONObject.NULL)
                        .put("maxCount", JSONObject.NULL);

        magicSearchPostRequestWithStatusCodeCheck(userDoesNotExist, 455);
    }

    @Test
    public void checkStatusCode456_queryLengthMoreThan1000() {
        JSONObject userDoesNotExist =
                new JSONObject()
                        .put("query", new String(new char[1001]).replace("\0", "Q"));

        magicSearchPostRequestWithStatusCodeCheck(userDoesNotExist, 456);
    }

    @Test
    public void checkStatusCode230_queryLength1000_BVAofCode456() {
        JSONObject userDoesNotExist =
                new JSONObject()
                        .put("query", new String(new char[1000]).replace("\0", "Q"));

        magicSearchPostRequestWithStatusCodeCheck(userDoesNotExist, 230);
    }

    @Test(dataProvider = "invalidPartyTypes")   // TODO: check valid partyTypes
    public void checkStatusCode457_invalidPartyType(String invalidPartyType) {
        JSONObject userWithInvalidPartyType =
                new JSONObject()
                        .put("query", userNoCompanies.name)
                        .put("partyType", invalidPartyType);  // ALL // USER // COMPANY // null

        magicSearchPostRequestWithStatusCodeCheck(userWithInvalidPartyType, 457);
    }
    @DataProvider
    private Object[] invalidPartyTypes(){
        return new String[] {
                "all",
                "ALOL",
                "AL",
                "AALL",
                "ALLL",
                "user",
                "USLER",
                "SER",
                "USERa",
                " USER",
                "company",
                "COMPANYY",
                "COMPANY ",
                "CCOMPANY",
                "COMPANIES",
                "null"
        };
    }

    @Test(dataProvider = "invalidTaskStatuses")
    public void checkStatusCode458_invalidTaskStatus(String invalidTaskStatus) { // TODO: check also valid: ALL, ACTUAL, COMPLETE, FAIL
        JSONObject userWithInvalidTaskStatus =
                new JSONObject()
                        .put("query", userNoCompanies.name)
                        .put("taskStatus", invalidTaskStatus);

        magicSearchPostRequestWithStatusCodeCheck(userWithInvalidTaskStatus, 458);
    }
    @DataProvider
    private Object[] invalidTaskStatuses(){
        return new String[] {
                "all",
                "ALOL",
                "AL",
                "AALL",
                "ALLL",
                "actual",
                "aCTUAL",
                "AACTUAL",
                "ACTUALY",
                " ACTUAL",
                "ACTUAL ",
                "complete",
                "COMPLETE ",
                " COMPLETE",
                "cOMPLETE",
                "COMPLETED",
                "fail",
                "FAILED",
                "FAIL ",
                " FAIL",
                "null",

                "EVERY",
                "REAL",
                "GOOD",
                "PASS",
                "PASSED",
                "BAD"
        };
    }

    @Test(dataProvider = "invalidIncludes")
    public void checkStatusCode459_invalidInclude(String invalidInclude) { // TODO: check also valid: ALL, USER, COMPANY, TASK, WHY
        JSONObject userWithInvalidInclude =
                new JSONObject()
                        .put("query", userNoCompanies.name)
                        .put("taskStatus", invalidInclude);

        magicSearchPostRequestWithStatusCodeCheck(userWithInvalidInclude, 459);
    }
    @DataProvider
    private Object[] invalidIncludes() {
        return new String[]{
                "all",
                "ALOL",
                "AL",
                "AALL",
                "ALLL",
                "user",
                "USLER",
                "SER",
                "USERa",
                " USER",
                "company",
                "COMPANYY",
                "COMPANY ",
                "CCOMPANY",
                "COMPANIES",
                "task",
                "TASK ",
                " TASK",
                "TASKS",
                "tASK",
                "why",
                "WHY ",
                " WHY",
                "",
                "null",

                "JOB",
                "JOBS",
        };
    }


    ////////////////////////////// SUPPORT FUNCTION

    private void magicSearchPostRequestWithStatusCodeCheck(JSONObject userDoesNotExist, int expectedStatusCode) {
        System.out.println("--------------------------\nTest\nRequest: \n" + userDoesNotExist.toString() + "\nResponse:");

        given()
                .when()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(userDoesNotExist.toString())
                .post("http://users.bugred.ru/tasks/rest/magicsearch")
                .then()
                .log()
                .body()
                .assertThat()
                .statusCode(expectedStatusCode);
    }
}

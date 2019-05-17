package my.api.rest.test;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import my.api.rest.dataobjects.Company;
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
        // 2/+/ users (without company(ies)) names: testStatusCodeUser_twoUsers_noCompanies_12345678901234567890END1, testStatusCodeUser_twoUsers_noCompanies_12345678901234567890END2 - 231, 232 SC
        // 2/+/ companies (without user(s)) names: testStatusCodeCompany12345678901234567890END1, testStatusCodeCompany12345678901234567890END2 - 234 SC
        //

        for (User testUser : testUsers)
            createUser(testUser);
    }

    Random rnd = new Random();
    private User userNoCompanies = new User().setName("hello12345678901234567890END").setEmail("hello12345678901234567890END@mail.ml").setPassword("1");
    private String twoUsersNoCompanies_samePartOfName = "testStatusCodeUser_twoUsers_noCompanies_12345678901234567890END";
    private User userCompanyOwner = new User().setName("companyowner12345678901234567890end").setEmail("companyowner12345678901234567890end@mail.ml").setPassword("1");
    private String theSameNamePartOfOneUserAndOneCompany = "1userand1company" + rnd.nextInt(1000000000) + rnd.nextInt(1000000000) + rnd.nextInt(1000000000) + "end";
    private String theSameNamePartOfOneUserAndTwoCompanies = "1userand2comp_" + rnd.nextInt(1000000000) + rnd.nextInt(1000000) + rnd.nextInt(1000000) + "end";
    private String theSameNamePartOfTwoUsersAndOneCompany = "2usersand1comp_" + rnd.nextInt(1000000000) + rnd.nextInt(1000000) + rnd.nextInt(1000000) + "end";
    private User[] testUsers = new User[] {
            userNoCompanies,
            new User().setName(twoUsersNoCompanies_samePartOfName + "1").setEmail(twoUsersNoCompanies_samePartOfName + "1@mail.ml").setPassword("1"),
            new User().setName(twoUsersNoCompanies_samePartOfName + "2").setEmail(twoUsersNoCompanies_samePartOfName + "2@mail.ml").setPassword("1"),
            userCompanyOwner,
            new User().setName(theSameNamePartOfOneUserAndOneCompany + "_user").setEmail(theSameNamePartOfOneUserAndOneCompany + "_user@mail.ml").setPassword("1"),
            new User().setName(theSameNamePartOfOneUserAndTwoCompanies + "_user").setEmail(theSameNamePartOfOneUserAndTwoCompanies + "_user@mail.ml").setPassword("1"),
            new User().setName(theSameNamePartOfTwoUsersAndOneCompany + "_user1").setEmail(theSameNamePartOfTwoUsersAndOneCompany + "_user1@mail.ml").setPassword("1"),
            new User().setName(theSameNamePartOfTwoUsersAndOneCompany + "_user2").setEmail(theSameNamePartOfTwoUsersAndOneCompany + "_user2@mail.ml").setPassword("1")
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

    private String testCompanyName = "testCompany" + rnd.nextInt(1000000000) + rnd.nextInt(1000000000) + rnd.nextInt(1000000000) + "END";
    private String twoCompanies_samePartOfName = "twoCompaniesStatusCodeTest" + rnd.nextInt(1000000000) + rnd.nextInt(1000000000) + rnd.nextInt(1000000000) + "END";

    private Company[] testCompanies = new Company[] {
            new Company()
                    .setCompany_name(testCompanyName)
                    .setCompany_type("ООО")
                    .setCompany_users(new String[]{userCompanyOwner.email})
                    .setEmail_owner(userCompanyOwner.email),
            new Company()
                    .setCompany_name(twoCompanies_samePartOfName + "1")
                    .setCompany_type("ООО")
                    .setCompany_users(new String[]{userCompanyOwner.email})
                    .setEmail_owner(userCompanyOwner.email),
            new Company()
                    .setCompany_name(twoCompanies_samePartOfName + "2")
                    .setCompany_type("ООО")
                    .setCompany_users(new String[]{userCompanyOwner.email})
                    .setEmail_owner(userCompanyOwner.email),
            new Company()
                    .setCompany_name(theSameNamePartOfOneUserAndOneCompany + "_company")
                    .setCompany_type("ООО")
                    .setCompany_users(new String[]{userCompanyOwner.email})
                    .setEmail_owner(userCompanyOwner.email),
            new Company()
                    .setCompany_name(theSameNamePartOfOneUserAndTwoCompanies + "_company1")
                    .setCompany_type("ООО")
                    .setCompany_users(new String[]{userCompanyOwner.email})
                    .setEmail_owner(userCompanyOwner.email),
            new Company()
                    .setCompany_name(theSameNamePartOfOneUserAndTwoCompanies + "_company2")
                    .setCompany_type("ООО")
                    .setCompany_users(new String[]{userCompanyOwner.email})
                    .setEmail_owner(userCompanyOwner.email),
            new Company()
                    .setCompany_name(theSameNamePartOfTwoUsersAndOneCompany + "_company")
                    .setCompany_type("ООО")
                    .setCompany_users(new String[]{userCompanyOwner.email})
                    .setEmail_owner(userCompanyOwner.email)
    };

    @BeforeClass
    public void createTestZCompanies() {
        System.out.println("----------------\ncreateTestCompanies");
        // we need:
        // 1 user with 1 company (& 1 user with 2+ companies) - 231 SC
        // 2/+/ users (without company(ies)) names: testStatusCodeUser12345678901234567890END1, testStatusCodeUser12345678901234567890END2 - 231, 232 SC
        // 2/+/ companies (without user(s)) names: testStatusCodeCompany12345678901234567890END1, testStatusCodeCompany12345678901234567890END2 - 234 SC
        //



        for (Company testCompany: testCompanies)
            createCompany(testCompany);
    }

    private void createCompany(Company testCompany) {
        JSONObject testCompanyAsJson = new JSONObject();
        String company_nameKey = "company_name";
        String company_typeKey = "company_type";
        String company_usersKey = "company_users";
        String email_ownerKey = "email_owner";
        testCompanyAsJson
                .put(company_nameKey, testCompany.company_name)
                .put(company_typeKey, testCompany.company_type)
                .put(company_usersKey, testCompany.company_users)
                .put(email_ownerKey, testCompany.email_owner);
        System.out.println("Request: \n" + testCompanyAsJson.toString() + "\n");

        Response response = given()
                .when()                         //.accept(ContentType.XML) - response content type
                .contentType(ContentType.JSON)  // request content type
                .body(testCompanyAsJson.toString())
                .post("http://users.bugred.ru/tasks/rest/createcompany");

        assertThat("Check response code", response.statusCode(), equalTo(200));

        System.out.println("Response:\n" + response.getBody().asString());
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());

        if (jsonResponse.has("type") && jsonResponse.has("id_company") && jsonResponse.has("company")) {
            assertThat(jsonResponse.get("type").toString(), equalTo("success"));
        } else {
            fail("Unexpected response body contents (expect created user info OR error message that user exists)");
        }

        /* SUCCESS EXAMPLE
        {
            "type": "success",
            "id_company": 29,
            "company": {
                "name": "erftghj",
                "type": "ООО",
                "inn": "",
                "ogrn": "",
                "kpp": "",
                "phone": "",
                "adress": "",
                "users": [
                    "testtest@mail.ml"
                ]
            }
        }*/
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
        JSONObject singleUser =
                new JSONObject()
                        .put("query", userNoCompanies.name);

        magicSearchPostRequestWithStatusCodeCheck(singleUser, 231);
        // 1 user - 1 comp in this user ?
        // ? 1 user - 2 comp in this user
    }

    @Test
    public void checkStatusCode232_twoUserPlusAndNoCompaniesFound() {
        // 2 users no comp
        JSONObject twoUsers =
                new JSONObject()
                        .put("query", twoUsersNoCompanies_samePartOfName);

        magicSearchPostRequestWithStatusCodeCheck(twoUsers, 232);
    }

    @Test
    public void checkStatusCode233_oneCompanyAndNoUsersFound() {
        JSONObject oneCompany =
                new JSONObject()
                        .put("query", testCompanyName);

        magicSearchPostRequestWithStatusCodeCheck(oneCompany, 233);
    }

    @Test
    public void checkStatusCode234_twoCompaniesAndNoUsersFound() {
        JSONObject twoCompanies =
                new JSONObject()
                        .put("query", twoCompanies_samePartOfName);

        magicSearchPostRequestWithStatusCodeCheck(twoCompanies, 234);
    }

    @Test
    public void checkStatusCode235_oneCompanyAndOneUserFound() {
        JSONObject oneUserOneCompany =
                new JSONObject()
                        .put("query", theSameNamePartOfOneUserAndOneCompany);

        magicSearchPostRequestWithStatusCodeCheck(oneUserOneCompany, 235);
    }

    @Test
    public void checkStatusCode235_twoCompaniesAndOneUserFound() {
        JSONObject oneUserOneCompany =
                new JSONObject()
                        .put("query", theSameNamePartOfOneUserAndTwoCompanies);

        magicSearchPostRequestWithStatusCodeCheck(oneUserOneCompany, 235);
    }

    @Test
    public void checkStatusCode235_oneCompanyAndTwoUsersFound() {
        JSONObject oneUserOneCompany =
                new JSONObject()
                        .put("query", theSameNamePartOfTwoUsersAndOneCompany);

        magicSearchPostRequestWithStatusCodeCheck(oneUserOneCompany, 235);
    }

    @Test
    public void checkStatusCode235_twoPlusCompaniesAndTwoPlusUsersFound() {
        JSONObject oneUserOneCompany =
                new JSONObject()
                        .put("query", "end");

        magicSearchPostRequestWithStatusCodeCheck(oneUserOneCompany, 235);
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
                "All",
                "ALL ",
                " ALL",

                "user",
                "USER ",
                " USER",
                "USERS",

                "company",
                "COMPANY ",
                " COMPANY",
                "COMPANIES",

                "",
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
                "All",
                "ALL ",
                " ALL",

                "actual",
                "Actual",
                " ACTUAL",
                "ACTUAL ",
                "ACTUALLY",

                "complete",
                "Complete",
                "COMPLETE ",
                " COMPLETE",
                "COMPLETED",

                "fail",
                "Fail",
                "FAILED",
                "FAIL ",
                " FAIL",

                "",
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
                        .put("include", invalidInclude);

        magicSearchPostRequestWithStatusCodeCheck(userWithInvalidInclude, 459);
    }
    @DataProvider
    private Object[] invalidIncludes() {
        return new String[]{
                "all",
                "All",
                " ALL",
                "ALL ",

                "user",
                "User",
                "USER ",
                " USER",
                "USERS",
                "Users",

                "company",
                "Company",
                " COMPANY",
                "COMPANY ",
                "COMPANIES",

                "task",
                "Task",
                "TASK ",
                " TASK",
                "TASKS",
                "why",
                "WHY ",
                " WHY",

                "",
                "null"
        };
    }

    // error 400 don't test


    ////////////////////////////// SUPPORT FUNCTION

    private void magicSearchPostRequestWithStatusCodeCheck(JSONObject requestQuery, int expectedStatusCode) {
        System.out.println("--------------------------\nTest\nRequest: \n" + requestQuery.toString() + "\nResponse:");

        given()
                .when()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(requestQuery.toString())
                .post("http://users.bugred.ru/tasks/rest/magicsearch")
                .then()
                .log()
                .body()
                .assertThat()
                .statusCode(expectedStatusCode)
                .log()
                .status();
    }
}

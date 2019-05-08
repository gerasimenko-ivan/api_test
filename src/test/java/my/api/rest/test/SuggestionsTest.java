package my.api.rest.test;

import com.jayway.restassured.http.ContentType;
import org.json.JSONObject;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.matcher.RestAssuredMatchers.matchesXsd;
import static com.jayway.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;

//import static com.jayway.restassured.internal.matcher.xml.XmlXsdMatcher.matchesXsdInClasspath;

public class SuggestionsTest {

    @Test
    public void suggestFio_SuccessTest() {
        JSONObject jsonObject = new JSONObject();
        String queryKey = "query";
        String fioSuggest = "Мир";
        jsonObject
                .put(queryKey, fioSuggest);
        System.out.println("Request: \n" + jsonObject.toString() + "\nResponse:");

        given()
                .when()
                .contentType(ContentType.JSON)
                .accept(ContentType.XML)
                .header("Authorization", "Token 793d2830a6360a1569c8e7b9dd1afeb2f57ffa77")
                .body(jsonObject.toString())
                .post("https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/fio")

                .then()
                /*.log()
                .body()*/
                .body(matchesXsd("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                        "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" +
                        "    <xs:element name=\"SuggestResponse\">\n" +
                        "        <xs:complexType>\n" +
                        "            <xs:sequence>\n" +
                        "                <xs:element name=\"suggestions\">\n" +
                        "                    <xs:complexType>\n" +
                        "                        <xs:sequence>\n" +
                        "                            <xs:element name=\"value\" type=\"xs:string\"/>\n" +
                        "                            <xs:element name=\"unrestricted_value\" type=\"xs:string\"/>\n" +
                        "                            <xs:element name=\"data\">\n" +
                        "                                <xs:complexType>\n" +
                        "                                    <xs:sequence>\n" +
                        "                                        <xs:element name=\"surname\" type=\"xs:string\">\n" +
                        "                                            <xs:complexType>\n" +
                        "                                                <xs:attribute name=\"wstxns\" type=\"xs:string\" use=\"required\"/>\n" +
                        "                                            </xs:complexType>\n" +
                        "                                        </xs:element>\n" +
                        "                                        <xs:element name=\"name\" type=\"xs:string\">\n" +
                        "                                            <xs:complexType>\n" +
                        "                                                <xs:attribute name=\"wstxns\" type=\"xs:QName\" use=\"required\"/>\n" +
                        "                                            </xs:complexType>\n" +
                        "                                        </xs:element>\n" +
                        "                                        <xs:element name=\"patronymic\" type=\"xs:string\">\n" +
                        "                                            <xs:complexType>\n" +
                        "                                                <xs:attribute name=\"wstxns\\d+\" type=\"xs:QName\" use=\"required\"/>\n" +
                        "                                            </xs:complexType>\n" +
                        "                                        </xs:element>\n" +
                        "                                        <xs:element name=\"gender\" type=\"xs:string\">\n" +
                        "                                            <xs:complexType>\n" +
                        "                                                <xs:attribute name=\"wstxns\\d+\" type=\"xs:QName\" use=\"required\"/>\n" +
                        "                                            </xs:complexType>\n" +
                        "                                        </xs:element>\n" +
                        "                                    </xs:sequence>\n" +
                        "                                </xs:complexType>\n" +
                        "                            </xs:element>\n" +
                        "                        </xs:sequence>\n" +
                        "                    </xs:complexType>\n" +
                        "                </xs:element>\n" +
                        "            </xs:sequence>\n" +
                        "        </xs:complexType>\n" +
                        "    </xs:element>\n" +
                        "</xs:schema>"))
                ;
    }
}

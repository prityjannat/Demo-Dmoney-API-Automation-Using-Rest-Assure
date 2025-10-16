import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.jupiter.api.Test;
import utils.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class MyAssure {
    Properties prop;
    public MyAssure() throws IOException {
        prop = new Properties();
        FileInputStream fileInputStream = new FileInputStream("./src/test/resources/config.properties");
        prop.load(fileInputStream);
    }
    @Test
    public void dologin() throws ConfigurationException {
        RestAssured.baseURI = "https://dmoney.roadtocareer.net";
        Response res = given().contentType("application/json")
                .body("{\n" +
                        "    \"email\":\"admin@roadtocareer.net\",\n" +
                        "    \"password\":\"1234\"\n" +
                        "}")
                .when().post("/user/login");
        System.out.println(res.asString());

        JsonPath jsonobject = res.jsonPath();
        String token = jsonobject.get("token");
        System.out.println(token);
        Utils.setEVV("Token",token);
    }
    @Test
    public void searchUserId() throws IOException {
        RestAssured.baseURI = prop.getProperty("baseURL");
        Response res = given().contentType("application/json").header("Authorization", "Bearer " +prop.getProperty("Token"))
                .when().get("/user/search/id/98344");
        System.out.println(res.asString());
       // System.out.println(given().contentType("application/json").header("Authorization", "bearer" + prop.getProperty("token")));
    }
    @Test
    public void createUser() throws IOException {
        RestAssured.baseURI = prop.getProperty("baseURL");
        Response response = given().contentType("application/json")
                .header("Authorization", "Bearer " + prop.getProperty("Token"))
                .body("{\n" +
                        "    \"name\":\"Jannat TestUser 222\",\n" +
                        "    \"email\":\"testuser222@test.com\",\n" +
                        "    \"password\":\"1234\",\n" +
                        "    \"phone_number\":\"01855435859\",\n" +
                        "    \"nid\":\"123432789\",\n" +
                        "    \"role\":\"Customer\"\n" +
                        "}")
                .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerkey")).when()
                .post("/user/create");
        System.out.println();
        System.out.println(response.asString());
    }

}

package controller;

import config.UserModel;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.Properties;

import static io.restassured.RestAssured.given;

public class UserController {
    Properties prop;
    public UserController(Properties prop){
        this.prop = prop;
    }
    public Response dologin(UserModel userModel){
        RestAssured.baseURI = prop.getProperty("baseURL");
        Response res = given().contentType("application/json")
                .body(userModel)
                .when().post("/user/login");
        return res;
       // System.out.println(res.asString());
    }
    public Response createUser(UserModel userModel){
        RestAssured.baseURI = prop.getProperty("baseURL");
        return given().contentType("application/json")
                .header("Authorization", "Bearer " + prop.getProperty("Token"))
                .body(userModel)
                .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerkey")).when()
                .post("/user/create");
    }
    public Response searchUser(String UserId){
        RestAssured.baseURI = prop.getProperty("baseURL");
        return given().contentType("application/json").header("Authorization", "Bearer " +prop.getProperty("Token"))
                .when().get("/user/search/id/"+UserId);

    }
    public Response deleteUser(String UserId){
        RestAssured.baseURI = prop.getProperty("baseURL");
        return given().contentType("application/json")
                .header("Authorization", "Bearer " +prop.getProperty("Token"))
                .header("X-AUTH-SECRET-KEY", prop.getProperty("partnerkey"))
                .when().delete("/user/delete/"+UserId);
    }
}

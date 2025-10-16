package testrunner;

import com.github.javafaker.Faker;
import config.SetUp;
import config.UserModel;
import controller.UserController;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Utils;

public class UserTestRunner extends SetUp {
   @Test(priority = 1, description = "User can login successfully")
    public void dologin() throws ConfigurationException {
        UserController userController = new UserController(prop);
        UserModel userModel = new UserModel();
        userModel.setEmail("admin@roadtocareer.net");
        userModel.setPassword("1234");
        Response res = userController.dologin(userModel);
        JsonPath jsonObj = res.jsonPath();
        String token = jsonObj.get("token");
        Utils.setEVV("Token",token);
    }
    @Test(priority = 2, description = "User can create successfully")
    public void createUser() throws ConfigurationException {
        UserController userController = new UserController(prop);
        UserModel userModel = new UserModel();
        Faker faker =new Faker();
        userModel.setName(faker.name().firstName());
        userModel.setEmail(faker.internet().emailAddress());
        userModel.setPassword("1234");
        String phone = "0130"+Utils.generateRandomNumber(1000000,9999999);
        userModel.setPhone_number(phone);
        userModel.setNid("123456789");
        userModel.setRole("Customer");

        Response response = userController.createUser(userModel);
        System.out.println(response.asString());
        JsonPath jsonObject = response.jsonPath();
        int userId = jsonObject.get("user.id");
        System.out.println(userId);
        Utils.setEVV("UserId",String.valueOf(userId));
    }

    @Test(priority = 3, description = "Search the user by using userId")
    public void searchUser(){
        UserController userController = new UserController(prop);
        Response response = userController.searchUser(prop.getProperty("UserId"));
        System.out.println(response.asString());

        JsonPath jsonPath = response.jsonPath();
        String ActualMessage = jsonPath.get("message");
        String ExpectedMessage = "User found";
        Assert.assertEquals(ActualMessage,ExpectedMessage);
    }
    @Test(priority = 4, description = "User deleted")
    public void deleteUser(){
        UserController userController = new UserController(prop);
        Response response = userController.deleteUser(prop.getProperty("UserId"));
        System.out.println(response.asString());
    }
}

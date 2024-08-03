import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class UserController extends Setup {
    public UserController() throws IOException {
        initConfig();
    }
    public void doLogin(String email, String password) throws ConfigurationException {
        RestAssured.baseURI= prop.getProperty("baseUrl");
        UserModel model=new UserModel();
        model.setEmail(email);
        model.setPassword(password);
        Response res=given().contentType("application/json").body(model).post("/user/login");
        System.out.println(res.asString());
        JsonPath jsonPath=res.jsonPath();
        String token= jsonPath.get("token");
        System.out.println(token);
        Utils.setEvnVar("token",token);

    }
    public JsonPath searchUser(String userId) throws IOException {
        RestAssured.baseURI=prop.getProperty("baseUrl");
        Response res=given().contentType("application/json").header("Authorization",prop.get("token"))
                .when().get("/user/search/id/"+userId);
        return res.jsonPath();
    }
    public JsonPath createUser(UserModel model) throws ConfigurationException {
        RestAssured.baseURI= prop.getProperty("baseUrl");
        Response res=given().contentType("application/json")
                .header("Authorization",prop.getProperty("token"))
                .header("X-AUTH-SECRET-KEY",prop.getProperty("partnerKey"))
                .body(model)
                .when().post("/user/create");
        System.out.println(res.asString());
        return res.jsonPath();


    }



}

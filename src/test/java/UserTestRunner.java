import io.restassured.path.json.JsonPath;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class UserTestRunner {
    @Test(priority = 1, enabled = true)
    public void doLogin() throws IOException, ConfigurationException {
        UserController userController=new UserController();
        userController.doLogin("admin@roadtocareer.net","1234");
    }
    //@Test(priority = 2)
    public void searchUser() throws IOException {
        UserController userController=new UserController();
        JsonPath jsonPath= userController.searchUser("6535");
        String message= jsonPath.get("message");
        Assert.assertTrue(message.contains("User found"));
    }
    @Test(priority = 3)
    public void createUser() throws IOException, ConfigurationException {
        UserController userController=new UserController();
        UserModel model=new UserModel();
        model.setName("Rest assured user 1");
        model.setEmail("rest_b11@test.com");
        model.setPassword("P@ssword123");
        model.setPhone_number("01506656555");
        model.setNid("123456789");
        model.setRole("Customer");
        userController.createUser(model);
    }
}

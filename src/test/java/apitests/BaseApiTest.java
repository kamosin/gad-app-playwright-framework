package apitests;

import api.RequestManager;
import api.models.LoginRequest;
import api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import testutils.TestDataGenerator;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseApiTest {

    protected RequestManager requestManager;

    @BeforeEach
    public void baseSetup() {
        requestManager = new RequestManager();
    }

    protected void authUser() {
        var userService = new UserService(requestManager);
        var user = TestDataGenerator.generateUser();
        var response = userService.createUser(user);
        assertTrue(response.ok());
        requestManager.setToken(new LoginRequest(user.email(), user.password()));
    }
}

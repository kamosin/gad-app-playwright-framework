package apitests;


import api.RequestManager;
import api.models.LoginRequest;
import api.services.LoginService;
import api.services.UserService;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testutils.TestDataGenerator;

import static org.junit.jupiter.api.Assertions.*;

@UsePlaywright
public class ApiRegistrationTests {

    RequestManager requestManager;
    UserService userService;

    @BeforeEach
    public void setUp(){
        requestManager = new RequestManager();
        userService = new UserService(requestManager);
    }

    @Test
    public void userRegistrationAndLoginWithProperData(){
        //Given

        var loginService = new LoginService(requestManager);
        var user = TestDataGenerator.generateUser();

        //When
        var userResponse = userService.createUser(user);
        assertTrue(userResponse.ok());
        var loginResponse = loginService.login(new LoginRequest(user.email(), user.password()));

        //Then
        assertEquals(200, loginResponse.status());
    }
}

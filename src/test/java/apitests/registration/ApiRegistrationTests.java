package apitests.registration;

import api.TestUtils;
import api.models.LoginRequest;
import api.models.User;
import api.services.LoginService;
import api.services.UserService;
import apitests.BaseApiTest;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import testutils.ReusableData;
import testutils.TestDataGenerator;

import static org.junit.jupiter.api.Assertions.*;

@UsePlaywright
public class ApiRegistrationTests extends BaseApiTest {

    UserService userService;

    @BeforeEach
    public void setUp(){
        userService = new UserService(requestManager);
    }

    @Test
    @Tag("api")
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


    @Test
    @Tag("api")
    public void registerWithExistingEmail(){
        //Given
        var userService = new UserService(requestManager);
        var user = TestDataGenerator.generateUser();

        //When
        var userResponse = userService.createUser(user);
        assertTrue(userResponse.ok());
        userResponse = userService.createUser(new User(TestDataGenerator.generateFirstName(), TestDataGenerator.generateLastName(),
                user.email(), TestDataGenerator.generateBirthdate(), TestDataGenerator.generatePassword(), "0797eae7-5f95-4985-8ac8-10c58e17c769.jpg"));

        //Then
        assertEquals(409, userResponse.status());
        assertEquals(ReusableData.emailNotUniqueApiMessage, TestUtils.getJsonPath(userResponse, "error.message"));
    }

    @Test
    @Tag("api")
    public void registerOnlyWithEmail(){
        //Given
        var userService = new UserService(requestManager);
        var user = new User("", "", TestDataGenerator.generateEmail(), "", "", "");

        //When
        var userResponse = userService.createUser(user);
        System.out.println(userResponse.text());

        //Then
        assertEquals(422, userResponse.status());
        assertEquals(ReusableData.mandatoryFieldsMissingApiMessage, TestUtils.getJsonPath(userResponse, "error.message"));
    }

    @Test
    @Tag("api")
    public void registerWithWrongDateFormat(){
        //Given
        var userService = new UserService(requestManager);
        var user = new User(TestDataGenerator.generateFirstName(), TestDataGenerator.generateLastName(),
                "12-12-1996", TestDataGenerator.generateBirthdate(), TestDataGenerator.generatePassword(), "0797eae7-5f95-4985-8ac8-10c58e17c769.jpg");

        //When
        var userResponse =userService.createUser(user);

        //Then
        assertEquals(422, userResponse.status());
        assertEquals(ReusableData.invalidEmailFormatApiMessage, TestUtils.getJsonPath(userResponse, "error.message"));
    }
}

package apitests.flashposts;


import api.TestUtils;
import api.models.flashpost.FlashpostRequest;
import api.models.flashpost.FlashpostSettings;
import api.services.FlashpostsService;
import api.services.LoginService;
import apitests.BaseApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import testutils.TestDataGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class LoggedInFlashpostsTests extends BaseApiTest {

    private FlashpostsService flashpostsService;

    @BeforeEach
    public void setUp(){
        flashpostsService = new FlashpostsService(requestManager);
        authUser();
    }

    @Test
    @Tag("api")
    public void getAllFlashposts(){
        //When
        var response = flashpostsService.getFlashposts();
        //Then
        assertEquals(200, response.status());
    }

    @Test
    @Tag("api")
    public void createFlashpostWithValidData(){
        //Given
        var flashpostRequest = new FlashpostRequest(TestDataGenerator.generateText(50), new FlashpostSettings("#dddfff"), false);
        var initialNrOfFlashposts = flashpostsService.getNumberOfFlashposts();
        //When
        var response = flashpostsService.createFlashpost(flashpostRequest);
        //Then
        var nrOfFlaspostsAfterCreation = initialNrOfFlashposts + 1;
        assertEquals(201, response.status());
        assertEquals(flashpostsService.getNumberOfFlashposts(), nrOfFlaspostsAfterCreation);
    }

    @Test
    @Tag("api")
    public void createFlashpostWithTooLongMessage(){
        //Given
        var flashpostRequest = new FlashpostRequest("Automatyzuj testy z Playwright i Git! Zwiększ efektywność, wdrażaj szybciej i poprawiaj jakość kodu. Przyszłość IT w twoich rękach!", new FlashpostSettings(null), true);
        //When
        var response = flashpostsService.createFlashpost(flashpostRequest);
        //Then
        assertEquals(422, response.status());
//        response.then().body("error.message", containsString(ReusableData.flashpostsFieldValidationMessage));

    }

    @Test
    @Tag("api")
    public void createPublicFlashpostThenLogoutAndCheckVisibility(){
        //Given
        var flashpostText = TestDataGenerator.generateText(50);
        var flashpostRequest = new FlashpostRequest(flashpostText, new FlashpostSettings("#dddfff"), true);
        var loginService = new LoginService(requestManager);
        //When
        var response = flashpostsService.createFlashpost(flashpostRequest);
        loginService.logout();

        //Then
        response = flashpostsService.getFlashpostsById(Integer.parseInt(TestUtils.getJsonPath(response, "id")));
        assertEquals(200, response.status());
        assertEquals(TestUtils.getJsonPath(response, "body"), flashpostText);
    }

    @Test
    @Tag("api")
    @Tag("brokenTests")
    public void createNonPublicFlashpostThenLogoutAndCheckVisibility(){
        //Given
        var flashpostText = TestDataGenerator.generateText(50);
        var flashpostRequest = new FlashpostRequest(flashpostText, new FlashpostSettings("#dddfff"), false);
        var loginService = new LoginService(requestManager);
        //When
        var response = flashpostsService.createFlashpost(flashpostRequest);
        loginService.logout();
        //Then
        response = flashpostsService.getFlashpostsById(Integer.parseInt(TestUtils.getJsonPath(response, "id")));
        System.out.println(response.text());
        assertEquals(401, response.status());
        assertNotEquals(TestUtils.getJsonPath(response, "body"), flashpostText);

    }

}

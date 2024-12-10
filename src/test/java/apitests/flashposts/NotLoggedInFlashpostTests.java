package apitests.flashposts;

import api.models.flashpost.FlashpostRequest;
import api.models.flashpost.FlashpostSettings;
import api.services.FlashpostsService;
import apitests.BaseApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import testutils.TestDataGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotLoggedInFlashpostTests extends BaseApiTest {

    private FlashpostsService flashpostsService;

    @BeforeEach
    public void setUp(){
        flashpostsService = new FlashpostsService(requestManager);
    }

    @Test
    @Tag("api")
    public void createFlashpostAsNotLoggedInUser(){
        //Given
        var flashpostRequest = new FlashpostRequest(TestDataGenerator.generateText(50), new FlashpostSettings("#dddfff"), false);
        //When
        var response = flashpostsService.createFlashpost(flashpostRequest);
        //Then
        assertEquals(401, response.status());
    }
}

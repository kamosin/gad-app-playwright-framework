import api.models.User;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.*;
import pageobjects.LoginPage;
import pageobjects.MyAccountPage;
import pageobjects.RegistrationPage;
import testutils.ReusableData;
import testutils.TestDataGenerator;

import java.nio.file.Paths;

@DisplayName("Registration")
@UsePlaywright(BrowserOptions.class)
public class RegistrationTests {

    User user;

    @BeforeEach
    void openHomePage(BrowserContext browserContext, Page page) {
        browserContext.tracing().start(
                new Tracing.StartOptions()
                        .setScreenshots(true)
                        .setSnapshots(true)
                        .setSources(true)
        );
        page.navigate("http://localhost:3001");
    }

    @AfterEach
    void recordTrace(BrowserContext browserContext){
        browserContext.tracing().stop(
                new Tracing.StopOptions()
                        .setPath(Paths.get("trace.zip"))
        );
    }

    @Test
    public void RegistrationAndLoggingTest(Page page) {
        //Given
        var registrationPage = new RegistrationPage(page);
        user = TestDataGenerator.generateUser();
        //When
        String registrationInfo = registrationPage.registerWithAllFields(user.firstname(), user.lastname(), user.email(), user.birthDate(),
                user.password(), user.avatar());
        //Then
        Assertions.assertEquals(ReusableData.userCreatedExpectedMessage, registrationInfo);

        //When
        LoginPage loginPage = new LoginPage(page);
        loginPage.enterAllLoginData(user.email(), user.password());
        loginPage.clickLoginButton();
        MyAccountPage myAccountPage = new MyAccountPage(page);

        //Then
        Assertions.assertEquals(myAccountPage.getWelcomeText(), "Hi " + user.email() + "!");

    }

}

import api.models.User;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pageobjects.LoginPage;
import pageobjects.MyAccountPage;
import pageobjects.RegistrationPage;
import testutils.ReusableData;
import testutils.TestDataGenerator;

@DisplayName("Registration")
@UsePlaywright(BrowserOptions.class)
public class RegistrationTests {

    User user;

    @BeforeEach
    void openHomePage(Page page) {
        page.navigate("http://localhost:3001");
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
//        Assertions.assertEquals(ReusableData.userCreatedExpectedMessage, registrationInfo);

        //When
        LoginPage loginPage = new LoginPage(page);
        loginPage.enterAllLoginData(user.email(), user.password());
        loginPage.clickLoginButton();
        MyAccountPage myAccountPage = new MyAccountPage(page);

        //Then
        Assertions.assertEquals(myAccountPage.getWelcomeText(), "Hi " + user.email() + "!");

    }

}

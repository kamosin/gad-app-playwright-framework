package guitests;

import api.RequestManager;
import api.models.User;
import api.services.UserService;
import org.junit.jupiter.api.*;
import pageobjects.LoginPage;
import pageobjects.MyAccountPage;
import pageobjects.RegistrationPage;
import testutils.CommonActions;
import testutils.ReusableData;
import testutils.TestDataGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Registration")
//@UsePlaywright(BrowserOptions.class)
public class RegistrationTests extends BasePlaywrightTest{

    User user;

    @Test
    @Tag("gui")
    public void RegistrationAndLoggingTest() {
        //Given
        var registrationPage = new RegistrationPage(page);
        user = TestDataGenerator.generateUser();
        //When
        registrationPage.registerWithAllFields(user.firstname(), user.lastname(), user.email(), user.birthDate(),
                user.password(), user.avatar());
        var registrationInfo = CommonActions.getAlertText(page);
        //Then
        assertEquals(ReusableData.userCreatedExpectedMessage, registrationInfo);

        //When
        LoginPage loginPage = new LoginPage(page);
        loginPage.enterAllLoginData(user.email(), user.password());
        loginPage.clickLoginButton();
        MyAccountPage myAccountPage = new MyAccountPage(page);

        //Then
        assertEquals(myAccountPage.getWelcomeText(), "Hi " + user.email() + "!");

    }

    @Test
    @Tag("gui")
    public void RegistrationWithExistingUserEmail() {
        //Given
        user = TestDataGenerator.generateUser();
        var registrationPage = new RegistrationPage(page);

        //When
        registrationPage.registerWithAllFields(user.firstname(), user.lastname(), user.email(), user.birthDate(),
                user.password(), user.avatar());
        var registrationInfo = CommonActions.getAlertText(page);
        assertEquals(ReusableData.userCreatedExpectedMessage, registrationInfo);

        registrationPage.registerWithAllFields(user.firstname(), user.lastname(), user.email(), user.birthDate(),
                user.password(), user.avatar());
        registrationInfo = CommonActions.getAlertText(page);
        //Then
        assertEquals(ReusableData.emailNotUniqueExpectedMessage, registrationInfo);
    }

    @Test
    @Tag("gui")
    public void RegistrationWithOnlyEmail() {
        //Given
        user = TestDataGenerator.generateUser();
        var registrationPage = navigationBar.clickRegisterButton();

        //When
        registrationPage.enterEmail(user.email());
        registrationPage.clickRegisterButton();

        //Then
        assertTrue(registrationPage.isPasswordValidationTextVisible(ReusableData.requiredFieldMessage) &&
                registrationPage.isLastNameValidationTextVisible(ReusableData.requiredFieldMessage) &&
                registrationPage.isFirstNameValidationTextVisible(ReusableData.requiredFieldMessage));
    }

    @Test
    @Tag("gui")
    public void RegistrationWithWrongFormatData(){
        //Given
        var requestManager = new RequestManager();
        var wrongFirstName = "John1";
        var wrongLastName = "Smith2";
        var wrongEmail = "john.smith@mail";
        var wrongDate = "12-12-1969";
        var userService = new UserService(requestManager);
        var numberOfUsersBeforeRegistration = userService.getNumberOfUsers();

        //When
        var registrationPage = navigationBar.clickRegisterButton();
        registrationPage.enterAllData(wrongFirstName, wrongLastName, wrongEmail, wrongDate, TestDataGenerator.generatePassword(), ReusableData.userAvatar);
        registrationPage.clickRegisterButton();

        //Then
        assertTrue(registrationPage.isFirstNameValidationTextVisible(ReusableData.wrongFirstNameMessage) &&
                registrationPage.isLastNameValidationTextVisible(ReusableData.wrongLastNameMessage) &&
                registrationPage.isEmailValidationTextVisible(ReusableData.wrongEmailMessage) &&
                registrationPage.isDateValidationTextVisible(ReusableData.wrongDateMessage));
        assertEquals(userService.getNumberOfUsers(), numberOfUsersBeforeRegistration);
    }

}

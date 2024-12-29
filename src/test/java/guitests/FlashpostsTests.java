package guitests;

import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pageobjects.LandingPage;
import testutils.TestDataGenerator;

import static testutils.ReusableData.*;

public class FlashpostsTests extends BasePlaywrightTest{

    @Test
    @Tag("gui")
    public void AddFlashpostWithCorrectData(){
        //Given
        var user = registerAndLogin();
        //When
        var flashpostsPage = navigationBar.clickFlashpostsPageButton();
        var newFlashpostModal = flashpostsPage.clickCreateFlashpostsButton();
        newFlashpostModal.enterFlashpostData("It is never too late to start IT!");
        newFlashpostModal.setBackgroundColor("#000000");
        newFlashpostModal.clickCreateButton();
        //Then
        PlaywrightAssertions.assertThat(page.locator("#simple-alert")).containsText(flashpostCreatedMessage);
        Assertions.assertThat(flashpostsPage.getFlashpostAuthor(0)).isEqualTo(user.firstname() + " " + user.lastname());
    }

    @Test
    @Tag("gui")
    public void AddFlashpostWithTooLongMessage(){
        //When
        registerAndLogin();
        var flashpostsPage = navigationBar.clickFlashpostsPageButton();
        var newFlashpostModal = flashpostsPage.clickCreateFlashpostsButton();
        newFlashpostModal.enterFlashpostData(TestDataGenerator.generateText(130));
        newFlashpostModal.setBackgroundColor("#000000");
        int length = newFlashpostModal.getFlashpostTextLength();
        newFlashpostModal.enterFlashpostData("Some more data");
        //Then
        Assertions.assertThat(newFlashpostModal.getFlashpostTextLength()).isEqualTo(length);
    }

    @Test
    @Tag("gui")
    public void AddAFlashpostByNotLoggedInUser(){
        //When
        LandingPage landingPage = new LandingPage(page);
        landingPage.clickStartButton();
        var flashpostsPage = navigationBar.clickFlashpostsPageButton();
        var newFlashpostModal = flashpostsPage.clickCreateFlashpostsButton();
        newFlashpostModal.clickCreateButton();

        PlaywrightAssertions.assertThat(page.locator("#simple-alert")).containsText(flashpostNotEmptyMessage);
        newFlashpostModal.enterFlashpostData("It is a trap!");
        newFlashpostModal.setBackgroundColor("#dddddd");
        newFlashpostModal.clickCreateButton();

        //Then
        PlaywrightAssertions.assertThat(page.locator("#simple-alert").last()).containsText(flashpostNotCreatedMessage);
        newFlashpostModal.clickCancelButton();
        Assertions.assertThat(flashpostsPage.getNumberOfFlashposts()).isNotZero();
    }

    @Test
    @Tag("gui")
    public void AddPublicFlashpostAndCheckByNonLoggedUser(){
        //Given
        var flashpostPublicMessage = TestDataGenerator.generateText(15);
        var user = registerAndLogin();
        //When
        var flashpostsPage = navigationBar.clickFlashpostsPageButton();
        var newFlashpostModal = flashpostsPage.clickCreateFlashpostsButton();
        newFlashpostModal.enterFlashpostData(flashpostPublicMessage);
        newFlashpostModal.clickCheckbox();
        newFlashpostModal.clickCreateButton();
        PlaywrightAssertions.assertThat(page.locator("#simple-alert")).containsText(flashpostCreatedMessage);
        navigationBar.clickLogoutButton();
        flashpostsPage = navigationBar.clickFlashpostsPageButton();
        //Then
        Assertions.assertThat(flashpostsPage.getFlashpostAuthor(0)).isEqualTo(user.firstname());
        Assertions.assertThat(flashpostsPage.getFlashpostText(0)).isEqualTo(flashpostPublicMessage);
    }

    @Test
    @Tag("gui")
    public void AddNonPublicFlashpostAndCheckByNonLoggedUser(){
        //Given
        var flashpostPublicMessage = TestDataGenerator.generateText(15);
        var user = registerAndLogin();
        //When
        var flashpostsPage = navigationBar.clickFlashpostsPageButton();
        var newFlashpostModal = flashpostsPage.clickCreateFlashpostsButton();
        newFlashpostModal.enterFlashpostData(flashpostPublicMessage);
        newFlashpostModal.clickCreateButton();
        PlaywrightAssertions.assertThat(page.locator("#simple-alert")).containsText(flashpostCreatedMessage);
        navigationBar.clickLogoutButton();
        flashpostsPage = navigationBar.clickFlashpostsPageButton();
        //Then
        Assertions.assertThat(flashpostsPage.getFlashpostAuthor(0)).isNotEqualTo(user.firstname());
        Assertions.assertThat(flashpostsPage.getFlashpostText(0)).isNotEqualTo(flashpostPublicMessage);
    }
}

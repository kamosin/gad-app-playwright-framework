package guitests;

import api.RequestManager;
import api.services.ArticlesService;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pageobjects.LandingPage;
import pageobjects.articles.NewArticleModal;
import testutils.ReusableData;
import testutils.TestDataGenerator;

@DisplayName("Articles")
public class ArticlesTests extends BasePlaywrightTest{

    RequestManager requestManager;
    ArticlesService articlesAPI;

    @BeforeEach
    public void setUpRequestManager(){
        requestManager = new RequestManager();
        articlesAPI = new ArticlesService(requestManager);
    }

    @Test
    @Tag("gui")
    public void AddArticleWithProperData(){
        //Given
        var newArticleTitle = TestDataGenerator.generateText(20);
        var newArticleBody = TestDataGenerator.generateText(50);
        var picture = ReusableData.articlePictureName;
//        var requestManager = new RequestManager();
//        var articlesAPI = new ArticlesService(requestManager);

        //When
        registerAndLogin();

        int numberOfArticlesBeforeAdding = articlesAPI.getNumberOfArticles();
        navigationBar.clickArticlesPageButton();
        NewArticleModal newArticleModal = navigationBar.clickAddArticleButton();
        newArticleModal.enterAllData(newArticleTitle, newArticleBody, picture);
        newArticleModal.clickSaveButton();

        //Then
        PlaywrightAssertions.assertThat(page.locator("#alertPopup")).containsText(ReusableData.expectedArticleCreatedMessage);
        Assertions.assertThat(numberOfArticlesBeforeAdding).isEqualTo(articlesAPI.getNumberOfArticles()-1);
    }

    @Test
    @Tag("gui")
    public void AddArticleWithMissingData(){
        //Given
//        var requestManager = new RequestManager();
        var articlesAPI = new ArticlesService(requestManager);
        var numberOfArticlesBeforeAdding = articlesAPI.getNumberOfArticles();
        var randomTitle = TestDataGenerator.generateText(15);
        var randomBody = TestDataGenerator.generateText(50);
        //When
        registerAndLogin();
        navigationBar.clickArticlesPageButton();
        NewArticleModal newArticleModal = navigationBar.clickAddArticleButton();
        newArticleModal.enterTitle(randomTitle);
        newArticleModal.clickSaveButton();
        //Then
        PlaywrightAssertions.assertThat(page.locator("#alertPopup")).containsText(ReusableData.expectedArticleNotCreatedMessage);
        Assertions.assertThat(numberOfArticlesBeforeAdding).isEqualTo(articlesAPI.getNumberOfArticles());

        //When
        newArticleModal.clearTitle();
        newArticleModal.enterBody(randomBody);
        newArticleModal.clickSaveButton();

        //Then
        PlaywrightAssertions.assertThat(page.locator("#alertPopup")).containsText(ReusableData.expectedArticleNotCreatedMessage);
        Assertions.assertThat(numberOfArticlesBeforeAdding).isEqualTo(articlesAPI.getNumberOfArticles());
    }

    @Test
    @Tag("gui")
    public void TryAddArticleByNotLoggedUser(){
        //When
        LandingPage landingPage= new LandingPage(page);
        landingPage.clickStartButton();

        //Then
        PlaywrightAssertions.assertThat(page.locator("#add-new")).not().isVisible();
//        Assert.assertFalse(navigationBar.isAddArticleButtonVisible());
    }

    @Test
    @Tag("gui")
    public void AddArticleWithPolishAndSpecialCharacters(){
        //Given
        var polishAndSpecialCharactersTitle = "Żółw wśród@ raf koralowych – #wyjątkowa podróż pełna emocji i % niespodzianek!";
        var polishAndSpecialCharactersBody = "Podróżując # wśród@ malowniczych! @ra$f ()&%koralowych, żółw majestatycznie unosi się w krystalicznie czystej wodzie. Odkryj piękno oceanu, którego tajemnice skrywają niezwykłe stworzenia. W tej podróży zobaczysz bogactwo barw, odcieni oraz różnorodność fauny, jakiej nie znajdziesz nigdzie indziej. Zbliż się do przyrody i zanurz w świat pełen emocji – od fascynacji po zachwyt. Niech ta przygoda pozostawi w Tobie niezapomniane wspomnienia.";
//        var articlesAPI = new ArticlesService(requestManager);
        var numberOfArticlesBeforeAdding = articlesAPI.getNumberOfArticles();

        //When
        registerAndLogin();
        navigationBar.clickArticlesPageButton();
        NewArticleModal newArticleModal = navigationBar.clickAddArticleButton();
        newArticleModal.enterAllData(polishAndSpecialCharactersTitle, polishAndSpecialCharactersBody, ReusableData.articlePictureName);
        newArticleModal.clickSaveButton();

        //Then
        PlaywrightAssertions.assertThat(page.locator("#alertPopup")).containsText(ReusableData.expectedArticleCreatedMessage);
//        Assert.assertEquals(commonComponent.getPopupText(), ReusableData.expectedArticleCreatedMessage);
        Assertions.assertThat(numberOfArticlesBeforeAdding).isEqualTo(articlesAPI.getNumberOfArticles()-1);
//        Assert.assertEquals(articlesAPI.getNumberOfArticles(appUrl), numberOfArticlesBeforeAdding +1);
    }
}

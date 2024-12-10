package apitests.articles;

import api.TestUtils;
import api.models.ArticleRequest;
import api.services.ArticlesService;
import apitests.BaseApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import testutils.ReusableData;
import testutils.TestDataGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoggedInArticlesTests extends BaseApiTest {

    ArticlesService articlesService;

    @BeforeEach
    public void setUp(){
        articlesService = new ArticlesService(requestManager);
        authUser();
    }

    @Test
    @Tag("api")
    public void addArticleByRegisteredAndLoggedInUser(){
        //Given
        var article = TestDataGenerator.generateArticle();

        //When
        var response = articlesService.createArticle(article);

        //Then
        assertEquals(201, response.status());

    }

    @Test
    @Tag("api")
    public void addArticleWithMissingData(){
        //Given
        var articleWithoutBody = new ArticleRequest(TestDataGenerator.generateArticleTitle(), "", TestDataGenerator.currentDate(), ReusableData.articlePictureName);
        var articleWithoutTitle = new ArticleRequest("", TestDataGenerator.generateText(50), TestDataGenerator.currentDate(), ReusableData.articlePictureName);
        //When
        var response = articlesService.createArticle(articleWithoutBody);
        //Then
        assertEquals(422, response.status());
        assertEquals(ReusableData.mandatoryFieldsMissingApiMessage, TestUtils.getJsonPath(response, "error.message"));
        //When
        response = articlesService.createArticle(articleWithoutTitle);
        //Then
        assertEquals(422, response.status());
        assertEquals(ReusableData.mandatoryFieldsMissingApiMessage, TestUtils.getJsonPath(response, "error.message"));

    }

    @Test
    @Tag("api")
    public void AddArticleWithTitleLongerThan128Characters(){
        //Given
        var article = new ArticleRequest(TestDataGenerator.generateText(130), TestDataGenerator.generateText(100),
                TestDataGenerator.currentDate(), ReusableData.articlePictureName);

        //When
        var response = articlesService.createArticle(article);
        var text = TestUtils.getJsonPath(response, "error.message");

        //Then
        assertEquals(422, response.status());
//        assertTrue(text.contains(ReusableData.articleTitleTooLongErrorMessageText));
    }
}

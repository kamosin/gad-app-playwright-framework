package apitests.articles;

import api.services.ArticlesService;
import apitests.BaseApiTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import testutils.TestDataGenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class NotLoggedInArticlesTests extends BaseApiTest {

    ArticlesService articlesService;

    @BeforeEach
    public void setUp(){
        articlesService = new ArticlesService(requestManager);
    }

    @Test
    @Tag("api")
    public void createArticleAsNotLoggedInUser(){
        //Given
        var article = TestDataGenerator.generateArticle();
        //When
        var response = articlesService.createArticle(article);
        //Then
        assertEquals(401, response.status());
    }
}

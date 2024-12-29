package pageobjects.articles;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;
import java.util.stream.Collectors;

public class ArticlesPage {

    private final Page page;

    Locator articlesSearchInput;
    Locator searchArticleButton;
    List<Locator> singleArticleWrapper;
    Locator seeMoreClickableText;
    Locator singleArticleName;
    Locator singleArticleUser;

    public ArticlesPage(Page page) {
        this.page = page;
        this.articlesSearchInput = page.locator("#search-input");
        this.searchArticleButton = page.getByTestId("search-button");
        this.singleArticleWrapper = page.locator("css=.card-wrapper").all();
        this.seeMoreClickableText = page.getByText("See More...");
        this.singleArticleName = page.locator(".//strong//a");
        this.singleArticleUser = page.locator(".//span//a)[1]");

    }

    public SingleArticlePage clickSeeMore(String articleTitle){
        var wrapper = getArticleWrapperByArticleName(articleTitle);
        wrapper.locator(seeMoreClickableText).click();
        return new SingleArticlePage(page);
    }

    private Locator getArticleWrapperByArticleName(String articleTitle) {
        return singleArticleWrapper.stream()
                .filter(wrapper -> articleTitle.equals(wrapper.locator(singleArticleName).textContent()))
                .findFirst()
                .orElse(null);
    }

    public void searchArticle(String articleData){
        articlesSearchInput.fill(articleData);
        searchArticleButton.click();

    }

    public int returnNumberOfArticlesVisible(){
        return singleArticleWrapper.size();
    }

    public List<String> returnNamesOfArticles(){
        return singleArticleWrapper.stream()
                .map(wrapper -> wrapper.locator(singleArticleName).textContent())
                .collect(Collectors.toList());
    }

    public String returnTitleOfArticle(int number){
        return singleArticleWrapper.get(number).locator(singleArticleName).textContent();
    }

    public String returnUserOfArticle(int number){
        return singleArticleWrapper.get(number).locator(singleArticleUser).textContent();
    }
}

package pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pageobjects.articles.ArticlesPage;

public class LandingPage {

    private final Page page;

    Locator startButton;

    public LandingPage(Page page) {
        this.page = page;
        this.startButton = page.locator("#btnGui");
    }

    public void goToLandingPage(String url){
        page.navigate(url);
    }

    public ArticlesPage clickStartButton(){
        startButton.click();
        return new ArticlesPage(page);
    }
}

package pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class MyAccountPage {

    private final Page page;
    Locator welcomeElement;
    Locator surveysButton;

    public MyAccountPage(Page page) {
        this.page = page;
        this.welcomeElement = page.getByTestId("hello");
        this.surveysButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Surveys"));
    }

    public String getWelcomeText(){
        return welcomeElement.textContent();
    }
}

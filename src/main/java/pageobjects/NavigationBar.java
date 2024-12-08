package pageobjects;


import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class NavigationBar {

    private final Page page;
    Locator avatarIcon;
    Locator registerButton;
    Locator loginButton;
    Locator logoutButton;
    Locator articlesButton;
    Locator commentsButton;
    Locator addNewArticleButton;
    Locator flashPostsButton;

    public NavigationBar(Page page) {
        this.page = page;
        this.avatarIcon = page.getByTestId("btn-dropdown");
        this.registerButton = page.locator("#registerBtn");
        this.loginButton= page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
        this.logoutButton = page.locator("#logoutBtn");
        this.articlesButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Articles"));
        this.commentsButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Comments"));
        this.addNewArticleButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add Article"));
        this.flashPostsButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Flashposts"));
    }

    public RegistrationPage clickRegisterButton(){
        avatarIcon.hover();
        registerButton.waitFor();
        registerButton.click();
        return new RegistrationPage(page);
    }

    public LoginPage clickLoginButton(){
        avatarIcon.hover();
        loginButton.click();
        return new LoginPage(page);
    }

    public LoginPage clickLogoutButton(){
        avatarIcon.hover();
        logoutButton.click();
        return new LoginPage(page);
    }


}

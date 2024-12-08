package pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage {

    private final Page page;
    Locator emailInput;
    Locator passwordInput;
    Locator loginButton;

    public LoginPage(Page page) {
        this.page = page;
        this.emailInput = page.getByPlaceholder("Enter User Email");
        this.passwordInput = page.getByPlaceholder("Enter Password");
        this.loginButton = page.locator("#loginButton");
    }


    public void enterEmail(String email){
        emailInput.fill(email);
    }

    public void enterPassword(String password){
        passwordInput.fill(password);
    }

    public void enterAllLoginData(String email, String password){
        enterEmail(email);
        enterPassword(password);
    }

    public void clickLoginButton(){
        loginButton.click();
    }

    public void login(String email, String password){
        enterAllLoginData(email, password);
        clickLoginButton();
    }
}

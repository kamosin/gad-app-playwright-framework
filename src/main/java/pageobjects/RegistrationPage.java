package pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.time.Duration;

public class RegistrationPage {

    private final Page page;
    NavigationBar navigationBar;
    Locator firstNameInput;
    Locator lastNameInput;
    Locator emailInput;
    Locator datepickerInput;
    Locator datePicker;
    Locator datePickerDoneButton;
    Locator passwordInput;
    Locator imageSelector;
    Locator registerButton;
    Locator alertPopup;

    Locator firstNameValidation;
    Locator lastNameValidation;
    Locator emailValidation;
    Locator dateValidation;
    Locator passwordValidation;

    public RegistrationPage(Page page) {
        this.page = page;
        this.firstNameInput = page.getByPlaceholder("Enter User First Name");
        this.lastNameInput = page.getByPlaceholder("Enter User Last Name");
        this.emailInput = page.getByPlaceholder("Enter User Email");
        this.datepickerInput = page.getByPlaceholder("Enter Birth Date");
        this.datePicker = page.locator("#ui-datepicker-div");
        this.datePickerDoneButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Done"));
        this.passwordInput = page.getByPlaceholder("Enter Password");
        this.imageSelector = page.locator("select#avatar");
        this.registerButton = page.getByTestId("register-button");
        this.alertPopup = page.locator("#alertPopup");
        this.firstNameValidation = page.locator("#octavalidate_firstname");
        this.lastNameValidation = page.locator("#octavalidate_lastname");
        this.emailValidation = page.locator("#octavalidate_email");
        this.dateValidation = page.locator("#octavalidate_datepicker");
        this.passwordValidation = page.locator("#octavalidate_password");
    }

    public void enterFirstName(String firstName){
        firstNameInput.fill(firstName);
    }

    public void enterLastName(String lastName){
        lastNameInput.fill(lastName);
    }

    public void enterEmail(String email){
        emailInput.fill(email);
    }

    public void enterDate(String date){
        datepickerInput.fill(date);
        datePickerDoneButton.click();
    }

    public void enterPassword(String password){
        passwordInput.fill(password);
    }

    public void selectImage(String value){
        imageSelector.selectOption(value);
    }

    public void enterAllData(String firstName, String lastName,String email, String date, String password, String imageName){
        enterFirstName(firstName);
        enterLastName(lastName);
        enterEmail(email);
        enterDate(date);
        enterPassword(password);
        selectImage(imageName);
    }

    public void clickRegisterButton(){
        registerButton.click();
    }

    public void registerWithAllFields(String firstName, String lastName, String email, String date,
                                        String password, String imageName) {
        navigationBar = new NavigationBar(page);
        var registrationPage = navigationBar.clickRegisterButton();
        registrationPage.enterAllData(firstName, lastName, email, date,
                password, imageName);
        clickRegisterButton();
    }

    public boolean isFirstNameValidationTextVisible(String text){
        return firstNameValidation.isVisible() && firstNameValidation.textContent().equals(text);
    }

    public boolean isLastNameValidationTextVisible(String text){
        return lastNameValidation.isVisible() && lastNameValidation.textContent().equals(text);
    }

    public boolean isEmailValidationTextVisible(String text){
        return emailValidation.isVisible() && emailValidation.textContent().equals(text);
    }

    public boolean isDateValidationTextVisible(String text){
        return dateValidation.isVisible() && dateValidation.textContent().equals(text);
    }

    public boolean isPasswordValidationTextVisible(String text){
        return passwordValidation.isVisible() && passwordValidation.textContent().equals(text);
    }
}

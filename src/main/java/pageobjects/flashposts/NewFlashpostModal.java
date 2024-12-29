package pageobjects.flashposts;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class NewFlashpostModal {

    private final Page page;
    Locator flashpostTextArea;
    Locator backgroundColorPicker;
    Locator createButton;
    Locator cancelButton;
    Locator publicCheckbox;

    public NewFlashpostModal(Page page) {
        this.page = page;
        this.flashpostTextArea = page.locator("#flashpost-text");
        this.backgroundColorPicker = page.locator("#background-color-picker");
        this.createButton = page.locator("css=.create");
        this.cancelButton = page.locator("css=.cancel");
        this.publicCheckbox = page.locator("#public-checkbox");

    }

    public void enterAllFlashpostData(String text, String hexColor, boolean isPublic){
        enterFlashpostData(text);
        setBackgroundColor(hexColor);
        if(isPublic){
            clickCheckbox();
        }
    }
    public void enterFlashpostData(String text){
        flashpostTextArea.fill(text);
    }

    public void setBackgroundColor(String hexColor){
        backgroundColorPicker.fill(hexColor);
    }

    public void clickCheckbox(){
        publicCheckbox.click();
    }

    public void clickCreateButton(){
        createButton.click();
    }

    public void clickCancelButton(){
        cancelButton.click();
    }

    public int getFlashpostTextLength(){
        return flashpostTextArea.textContent().length();
    }


}

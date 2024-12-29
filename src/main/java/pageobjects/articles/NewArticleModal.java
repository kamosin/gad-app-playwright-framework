package pageobjects.articles;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class NewArticleModal {

    private final Page page;

    Locator header;
    Locator titleInput;
    Locator bodyTextArea;
    Locator imageSelector;
    Locator saveButton;
    Locator cancelButton;
    Locator closeModalButton;

    public NewArticleModal(Page page) {
        this.page = page;
        this.header = page.getByText("Add New Entry");
        this.titleInput = page.getByTestId("title-input");
        this.bodyTextArea = page.locator("#body");
        this.imageSelector = page.locator("#image");
        this.saveButton = page.getByTestId("save");
        this.cancelButton = page.locator("css=.cancel");
        this.closeModalButton = page.locator("#close");

    }

    public void enterTitle(String title){
        titleInput.fill(title);
    }

    public void enterBody(String body){
        bodyTextArea.fill(body);
    }

    public void selectPicture(String pictureName){
        imageSelector.selectOption(pictureName);
    }

    public void enterAllData(String title, String body, String pictureName){
        enterTitle(title);
        enterBody(body);
        selectPicture(pictureName);
    }

    public void clickSaveButton(){
        saveButton.click();
    }

    public void clickCancelButton(){
        cancelButton.click();
    }

    public void clearTitle(){
        titleInput.clear();
    }

    public  void clearBody(){
        bodyTextArea.clear();
    }

}

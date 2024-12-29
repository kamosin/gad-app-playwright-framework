package pageobjects.comments;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AddCommentModal {

    private final Page page;

    public AddCommentModal(Page page) {
        this.page = page;
        this.commentBody = page.locator("#body");
        this.saveCommentButton = page.locator("css=.save");
    }

    Locator commentBody;
    Locator saveCommentButton;

    public void enterBodyText(String text){
        commentBody.fill(text);
    }

    public void clickSaveCommentButton(){
        saveCommentButton.click();
    }
}

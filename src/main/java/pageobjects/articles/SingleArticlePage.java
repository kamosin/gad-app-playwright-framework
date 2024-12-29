package pageobjects.articles;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import pageobjects.comments.AddCommentModal;

public class SingleArticlePage {

    private final Page page;

    Locator addNewCommentButton;

    public SingleArticlePage(Page page) {
        this.page = page;
        this.addNewCommentButton = page.locator("#add-new");
    }



    public AddCommentModal clickAddNewCommentButton(){
        addNewCommentButton.click();
        return new AddCommentModal(page);
    }

    public void addComment(String commentText){
        var commentModal = clickAddNewCommentButton();
        commentModal.enterBodyText(commentText);
        commentModal.clickSaveCommentButton();
    }
}

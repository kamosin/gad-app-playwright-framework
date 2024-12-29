package pageobjects.flashposts;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.List;

public class FlashpostsPage {

    private final Page page;
    Locator createFlashpostsButton;
    List<Locator> flashpostContainers;
    Locator flashpostAuthor;
    Locator flashpostText;


    public FlashpostsPage(Page page) {
        this.page = page;
        this.createFlashpostsButton = page.locator("css=.create-flashpost-btn");
        this.flashpostAuthor = page.locator("css=.flashpost-author");
        this.flashpostText = page.locator("css=.flashpost-message");
    }

    private List<Locator> getFlashpostContainers() {
        return page.locator(".flashpost-container").all();
    }

    public void createNewFlashpost(String text, String hexColor, boolean isPublic){
        var flashpostsModal = clickCreateFlashpostsButton();
        flashpostsModal.enterAllFlashpostData(text, hexColor, isPublic);
        flashpostsModal.clickCreateButton();
    }

    public NewFlashpostModal clickCreateFlashpostsButton(){
        createFlashpostsButton.click();
        return new NewFlashpostModal(page);
    }

    public String getFlashpostAuthor(int containerNumber){
        page.waitForSelector(".flashpost-container",
                new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
        return getFlashpostContainers().get(containerNumber).locator(flashpostAuthor).textContent();
    }

    public String getFlashpostText(int containerNumber){
        return getFlashpostContainers().get(containerNumber).locator(flashpostText).textContent();
    }

    public int getNumberOfFlashposts(){
        return getFlashpostContainers().size();
    }
}
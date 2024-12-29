//package pageobjects.comments;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.PageFactory;
//import pageobjects.CommonComponent;
//
//public class SingleCommentModal {
//
//    WebDriver driver;
//    CommonComponent commonComponent;
//
//    public SingleCommentModal(WebDriver driver) {
//        this.driver = driver;
//        this.commonComponent = new CommonComponent(driver);
//        PageFactory.initElements(driver, this);
//    }
//
//    @FindBy(css = "span[data-testid='comment-body']")
//    WebElement commentBody;
//
//    public String getCommentText(){
//        commonComponent.waitForElementToAppear(commentBody);
//        return commentBody.getText();
//    }
//}

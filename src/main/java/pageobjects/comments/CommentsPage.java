//package pageobjects.comments;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.PageFactory;
//import pageobjects.CommonComponent;
//
//import java.util.List;
//
//public class CommentsPage {
//
//    WebDriver driver;
//    CommonComponent commonComponent;
//
//    public CommentsPage(WebDriver driver) {
//        this.driver = driver;
//        this.commonComponent = new CommonComponent(driver);
//        PageFactory.initElements(driver, this);
//    }
//
//    @FindBy(css = ".card-comment")
//    List<WebElement> commentCard;
//
//    By seeCommentClickableText = By.xpath(".//strong//a");
//
//    public SingleCommentModal clickSeeMore(int number){
//        commonComponent.waitForElementsToAppear(commentCard);
//        commentCard.get(number).findElement(seeCommentClickableText).click();
//        return new SingleCommentModal(driver);
//    }
//
//
//}

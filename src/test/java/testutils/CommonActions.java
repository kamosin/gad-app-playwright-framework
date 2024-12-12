package testutils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

public class CommonActions {

    public static String getAlertText(Page page){
        Locator alertPopup = page.locator("#alertPopup");
        alertPopup.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        return alertPopup.textContent();
    }
}

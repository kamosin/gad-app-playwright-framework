package guitests;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;

public class BrowserOptions implements OptionsFactory {
    @Override
    public Options getOptions() {
        return new Options().setLaunchOptions(
                        new BrowserType.LaunchOptions()
                ).setHeadless(false)
                .setTestIdAttribute("data-testid")
                .setBaseUrl("http://localhost:3001");
    }




}

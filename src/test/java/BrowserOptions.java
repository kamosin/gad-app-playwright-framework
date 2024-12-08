import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;

import java.util.List;

public class BrowserOptions implements OptionsFactory {
    @Override
    public Options getOptions() {
        return new Options().setLaunchOptions(
                        new BrowserType.LaunchOptions()
                                .setArgs(List.of("--start-fullscreen"))
                ).setHeadless(false)
                .setTestIdAttribute("data-testid")
                .setBaseUrl("http://localhost:3001");
    }
}

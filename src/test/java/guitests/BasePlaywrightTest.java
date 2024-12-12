package guitests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import pageobjects.NavigationBar;

import java.nio.file.Paths;
import java.util.Arrays;

public abstract class BasePlaywrightTest {


    protected static Playwright playwright;
    protected static Browser browser;
    protected static BrowserContext browserContext;

    protected Page page;
    protected NavigationBar navigationBar;

    @BeforeAll
    static void setUpBrowser() {
        playwright = Playwright.create();
        playwright.selectors().setTestIdAttribute("data-testid");
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(Arrays.asList("--no-sandbox", "--disable-extensions", "--disable-gpu"))
        );
    }

    @BeforeEach
    void setUpBrowserContext() {
        browserContext = browser.newContext();
        browserContext.tracing().start(
                new Tracing.StartOptions()
                        .setScreenshots(true)
                        .setSnapshots(true)
                        .setSources(true)
        );
        page = browserContext.newPage();
        navigationBar = new NavigationBar(page);
        page.navigate("http://localhost:3001");
    }

    @AfterEach
    void closeContext(TestInfo testInfo) {
        String traceName = testInfo.getDisplayName().replace(" ","-").toLowerCase();
        browserContext.tracing().stop(
                new Tracing.StopOptions()
                        .setPath(Paths.get("target/traces/trace-" + traceName + ".zip"))
        );
        browserContext.close();
    }

    @AfterAll
    static void tearDown() {
        browser.close();
        playwright.close();
    }
}

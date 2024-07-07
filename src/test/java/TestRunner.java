import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pageObjects.MainPage;
import pageObjects.ProductPage;
import pageObjects.YourCartPage;
import utill.ScreenshotOnFailureExtension;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(ScreenshotOnFailureExtension.class)
@Timeout(120)
public class TestRunner {

    protected WebDriver driver;
    protected MainPage mainPage;
    protected ProductPage productPage;
    protected YourCartPage yourCartPage;

    @BeforeEach
    @SneakyThrows
    public void setUp() {
        WebDriverManager.chromedriver().arm64().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-http2");
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--window-size=2560,1440");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        ScreenshotOnFailureExtension.init(driver);

        //Init Page Objects
        mainPage = new MainPage(driver);
        yourCartPage = new YourCartPage(driver);
        productPage = new ProductPage(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

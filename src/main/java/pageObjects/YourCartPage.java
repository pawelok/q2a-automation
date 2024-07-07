package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class YourCartPage {
    private WebDriver driver;

    @FindBy(xpath = "//div[contains(@class,'PriceDetails')]//span[@data-locator='zth-price']")
    private List<WebElement> productPrice;

    @FindBy(xpath = "//div[contains(@class,'ContainerDescription')]//h3[contains(@class,'TitleStyle')]")
    private List<WebElement> productName;

    public YourCartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public List<String> getProductNames() {
        return productName.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public List<String> getProductPrices() {
        return productPrice.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public Map<String, String> getProductNamesWithPrices() {
        List<String> name = getProductNames();
        List<String> price = getProductPrices();
        if (name.size() != price.size()) {
            throw new IllegalArgumentException("Product names and prices lists must be the same size");
        }

        return IntStream.range(0, name.size()).boxed()
                        .collect(Collectors.toMap(name::get, price::get, (price1, price2) -> price1));
    }

    public boolean isGameDisplayed(String gameTitle) {
        return driver.findElement(By.name(gameTitle)).isDisplayed();
    }

    public void clickOnProductCard(String gameTitle) {
        driver.findElement(By.xpath("//input[@name='" + gameTitle + "' and contains(@class,'productCard')]")).click();
    }
}
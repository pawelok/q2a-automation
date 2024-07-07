package pageObjects;

import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage {
    private WebDriver driver;
    private static final String BASE_URL = "https://www.g2a.com";

    @FindBy(xpath = "//input[@type='search']")
    private WebElement searchTextBox;

    @FindBy(xpath = "//ul[contains(@class,'ItemsListContainer')]//li")
    private List<WebElement> searchResults;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(BASE_URL);
    }

    public boolean isSearchBoxDisplayed() {
        return searchTextBox.isDisplayed();
    }

    public void clickOnFirstSearchResult() {
        searchResults.get(0).click();
    }

    @SneakyThrows
    public void searchForProduct(String productName) {
        searchTextBox.click();
        searchTextBox.sendKeys(productName);
    }

    public int numberOfSearchResults() {
        return searchResults.size();
    }
}

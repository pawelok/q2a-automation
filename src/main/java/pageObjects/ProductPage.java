package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage {

    private WebDriver driver;

    @FindBy(xpath = "//div[@data-locator='product-info']//h1[@data-locator='ppa-summary__title']")
    private WebElement productTitle;

    @FindBy(xpath = "//div[@data-locator='product-info']//span[@data-locator='zth-price']")
    private WebElement productPrice;

    @FindBy(xpath = "//div[@data-locator='product-info']//button[@data-locator='ppa-payment__btn']")
    private WebElement addProductToCartButton;

    @FindBy(xpath = "//button[@data-test-id='primary-button'][text()='Add to cart']")
    private WebElement confirmAddToCartButton;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isProductDisplayed(String productName) throws Exception {
        productTitle.isDisplayed();
        if (!productTitle.getText().equals(productName))
            throw new Exception("Product in not visible on this page. Unable to get product price.");
        else return true;
    }

    public String getProductPrice(String productName) throws Exception {
        isProductDisplayed(productName);
        productPrice.isDisplayed();
        return productPrice.getText();
    }

    public String getProductTitle() {
        return productTitle.getText();
    }

    public void addProductToCart(String productName) throws Exception {
        isProductDisplayed(productName);
        addProductToCartButton.click();
    }

    public void clickOnConfirmAddToCartButton() {
        confirmAddToCartButton.click();
    }
}

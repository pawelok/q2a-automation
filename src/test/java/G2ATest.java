import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class G2ATest extends TestRunner {


    @Test
    public void verifyProductPrice() throws Exception {
        // Step 0
        String productName = System.getProperty("product_name");
        Assertions.assertTrue(productName != null && productName.length() > 0, "product_name is null or empty");

        // Step 1 - Open page
        mainPage.open();
        Assertions.assertTrue(mainPage.isSearchBoxDisplayed());

        // Step 2 - Search for item
        mainPage.searchForProduct(productName);
        assertTrue(mainPage.numberOfSearchResults() > 0, "No products matching to " + productName + " were found");

        // Step 3 - open product page
        mainPage.clickOnFirstSearchResult();
        assertEquals(productName, productPage.getProductTitle(), "Provided product name doesn't match");


        // Step 4 - get product price and add product to cart
        String price1 = productPage.getProductPrice(productName);
        productPage.addProductToCart(productName);
        productPage.clickOnConfirmAddToCartButton();

        // Step 5 - compare prices
        Map<String, String> map = yourCartPage.getProductNamesWithPrices();
        String price2 = map.get(productName);
        assertEquals(price1, price2, "Product price: " + price1 +
                " is not equal to price in cart: " + price2);
    }
}

package org.swapcard.qa.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.swapcard.qa.pages.AmazonPages;
import org.swapcard.qa.pages.ProductPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class AmazonTestNew {
    WebDriver driver;
    AmazonPages amazonPage;
    ProductPage productPage;

    @BeforeMethod
    public void setup() {
        String browserName = System.getProperty("browser", "chrome");

        if ("chrome".equals(browserName)) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--incognito");
            driver = new ChromeDriver(options);
        } else if ("firefox".equals(browserName)) {
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("-private");
            driver = new FirefoxDriver(options);
        }

        driver.get("https://www.amazon.com");
        driver.manage().window().maximize();
        
        amazonPage = new AmazonPages(driver);
        productPage = new ProductPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void addAndRemoveProductWillResultInLessThan100Bucks() throws InterruptedException {
        amazonPage.changeLocationToMexico();
        
        // Search and add first product
        amazonPage.searchProduct("Ariano Suassuna");
        productPage.selectFirstBookAndAddToCart();

        // Search and add second product
        amazonPage.searchProduct("When: The Scientific Secrets of Perfect Timing");
//        // Change quantity 
//        productPage.changeQuantityTo(2);
        productPage.selectSecondBookAndAddToCart();
    
        productPage.goToCart();

        // Decrement the quantity and verify subtotal
        productPage.decrementProductQuantity();
        float subTotal = productPage.getCartSubtotal();

        assertTrue(subTotal < 100, "Subtotal should be less than 100 USD");
    }

    @Test
    public void check5StarRatingsGreaterThan70Per() throws InterruptedException {
        amazonPage.searchProduct("Jorge Amado");
        
        productPage.clickBooksFilter();
        
        productPage.selectThirdBook();

        // Get the rating percentage of the book using the ProductPage's method
        int ratingPercentage = productPage.getBookRatingPercentage();

        // Step 2: Assert that the rating is greater than 70%
        assertTrue(ratingPercentage > 70, "The rating should be greater than 70%. Found: " + ratingPercentage);
     
    }
}

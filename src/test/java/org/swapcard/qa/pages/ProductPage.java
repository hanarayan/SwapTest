package org.swapcard.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.JavascriptExecutor;
import java.time.Duration;

public class ProductPage {
    WebDriver driver;
    WebDriverWait wait;

    // Locators for elements on the Product Page
    @FindBy(partialLinkText = "Auto da Compadecida")
    WebElement bookLink1;

    @FindBy(id = "add-to-cart-button-ubb")
    WebElement addToCartButton1;

    @FindBy(partialLinkText = "When: The Scientific Secrets of Perfect Timing")
    WebElement bookLink2;

    @FindBy(id = "add-to-cart-button")
    WebElement addToCartButton2;

    @FindBy(xpath = "//select[@id='quantity-selector']")
    WebElement quantityDropdown;

    @FindBy(id = "add-to-cart-button")
    WebElement addToCart;

    @FindBy(partialLinkText = "Go to Cart")
    WebElement goToCart;

    @FindBy(css = "[data-a-selector=\"decrement\"]")
    WebElement decrementButton;

    @FindBy(css = "#sc-subtotal-amount-activecart .sc-price")
    WebElement subtotalPrice;
    
    @FindBy(css = "[data-cy=\"title-recipe\"] a")
    WebElement titleBook;
    
    @FindBy(xpath = "(//a[@class='a-size-base a-link-normal _cr-ratings-histogram_style_histogram-row-container__Vh7Di'])[1]//following-sibling::div//div[@class='a-meter']")
    WebElement ratingsMeter;
    
    @FindBy(xpath = "//span[@class='a-size-base a-color-base' and text()='Books']")
    WebElement booksFilter;
    
    @FindBy(partialLinkText = "Gabriela, Clove and Cinnamon")
    WebElement bookLink3;
    
    
    

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    public void selectFirstBookAndAddToCart() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(bookLink1)).click();
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton1)).click();
      
    }

    public void selectSecondBookAndAddToCart() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(bookLink2)).click();
        changeQuantityTo(2);
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton2)).click();
     
    }

    public void changeQuantityTo(int quantity) throws InterruptedException {
    	Thread.sleep(5000);
    	JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = '" + quantity + "'; arguments[0].dispatchEvent(new Event('change', { bubbles: true, cancelable: true }));", quantityDropdown);
        Thread.sleep(5000);
    }

    public void goToCart() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(goToCart)).click();
    
    }

    public void decrementProductQuantity() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(decrementButton)).click();
     
    }

    public float getCartSubtotal() {
        String subTotal = wait.until(ExpectedConditions.elementToBeClickable(subtotalPrice)).getText();
        return Float.parseFloat(subTotal.replaceAll("[^\\d.]", ""));
    }
    
    public int getBookRatingPercentage() {
        WebElement meterElement = wait.until(ExpectedConditions.visibilityOf(ratingsMeter));
        String ariaValue = meterElement.getDomAttribute("aria-valuenow");
        return Integer.parseInt(ariaValue);
    }
    
    public void clickBooksFilter() {
        wait.until(ExpectedConditions.elementToBeClickable(booksFilter)).click();
    }
    
    public void selectThirdBook() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(bookLink3)).click();
    }
}

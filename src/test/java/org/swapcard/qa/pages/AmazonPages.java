package org.swapcard.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class AmazonPages {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//*[@id='nav-packard-glow-loc-icon']")
    WebElement locationLink;

    @FindBy(xpath = "//*[@id='GLUXCountryValue']")
    WebElement countryValue;

    @FindBy(xpath = "//a[text()='Mexico']")
    WebElement mexicoCountryLink;

    @FindBy(xpath = "//*[@name='glowDoneButton']")
    WebElement doneButton;

    @FindBy(css = "[placeholder=\"Search Amazon\"]")
    WebElement searchBox;

    public AmazonPages(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this); 
    }
    
	// ADDED THIS CODE BECUASE MY LOCATION IS INDIA WHERE ADD TO CARD WAS NOT
	// VISISBLE
	// SO TO ENABLE THE ADD TO CARD BUTTON ENCHANCED THE CODE/
    public void changeLocationToMexico() throws InterruptedException {
        Actions actions = new Actions(driver);
    	actions.doubleClick( wait.until(ExpectedConditions.elementToBeClickable(locationLink))).perform();
    	Thread.sleep(2000); // to handle page load time
        wait.until(ExpectedConditions.elementToBeClickable(countryValue)).click();
        wait.until(ExpectedConditions.elementToBeClickable(mexicoCountryLink)).click();
        wait.until(ExpectedConditions.elementToBeClickable(doneButton)).click();
        Thread.sleep(2000); // to handle page load time
    }

    public void searchProduct(String productName) {
        wait.until(ExpectedConditions.elementToBeClickable(searchBox)).sendKeys(productName);
        searchBox.submit(); 
    }
}

package org.swapcard.qa.tests;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

///Without Page Object Factory

public class AmazonTests {
	WebDriver driver;
	WebDriverWait wait;
	String browserName;

	@BeforeMethod
	public void setup() throws InterruptedException, IOException {
	
         
		browserName = System.getProperty("browser", "chrome");

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
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		
		// ADDED THIS CODE BECUASE MY LOCATION IS INDIA WHERE ADD TO CARD WAS NOT
		// VISISBLE
		// SO TO ENABLE THE ADD TO CARD BUTTON ENCHANCED THE CODE/

		WebElement locLink = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='nav-packard-glow-loc-icon']")));
		//locLink.click();
		
		Actions actions = new Actions(driver);
	
		actions.doubleClick(locLink).perform();
		Thread.sleep(2000);

		WebElement countryValue = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='GLUXCountryValue']")));
		countryValue.click();

		WebElement countryLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Mexico']")));
		countryLink.click();

		WebElement doneClick = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@name='glowDoneButton']")));
		doneClick.click();

		Thread.sleep(2000);

	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void addAndRemoveProductWillResultInLessThan100Bucks() throws InterruptedException {
	

		WebElement searchBox = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[placeholder=\"Search Amazon\"]")));
		searchBox.sendKeys("Ariano Suassuna" + Keys.ENTER);

		WebElement bookLink = wait
				.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Auto da Compadecida")));
		bookLink.click();

		WebElement addToCartButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button-ubb")));
		addToCartButton.click();

		WebElement anotherBook = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[placeholder=\"Search Amazon\"]")));
		anotherBook.sendKeys("When: The Scientific Secrets of Perfect Timing" + Keys.ENTER);

		
		WebElement titleBook = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-cy=\"title-recipe\"] a")));
		titleBook.click();
		
		Thread.sleep(5000);

		WebElement quantity1 = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='quantity-selector']")));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].value = '2'; arguments[0].dispatchEvent(new Event('change', { bubbles: true, cancelable: true }));", quantity1);
	
		WebElement addToCart = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button")));
		addToCart.click();

		
		WebElement goToCart = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Go to Cart")));
		goToCart.click();
	
		
		WebElement decrement = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-a-selector=\"decrement\"]")));
		decrement.click();
	
		
		WebElement price = wait.until(
				ExpectedConditions.elementToBeClickable(By.cssSelector("#sc-subtotal-amount-activecart .sc-price")));

		String subTotal = price.getText();

		float subTotalFloat = Float.parseFloat(subTotal.replaceAll("[^\\d.]", ""));

		assert (subTotalFloat < 100) : "Subtotal should be less than 100 USD";
	}

	@Test
	public void check5StarRatingsGreaterThan70Per() throws InterruptedException {
		WebElement searchBox = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[placeholder=\"Search Amazon\"]")));
		searchBox.sendKeys("Jorge Amado" + Keys.ENTER);

		WebElement doneClick = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//span[@class='a-size-base a-color-base' and text()='Books']")));
		doneClick.click();
		WebElement bookLink = wait
				.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Gabriela, Clove and Cinnamon")));
		bookLink.click();

		WebElement meterElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
				"(//a[@class='a-size-base a-link-normal _cr-ratings-histogram_style_histogram-row-container__Vh7Di'])[1]//following-sibling::div//div[@class='a-meter']")));

		String ariaValue = meterElement.getDomAttribute("aria-valuenow");

		assert (Integer.parseInt(ariaValue) > 70) : "Book Rating is greater than 70 % i.e " + ariaValue;
	}
}
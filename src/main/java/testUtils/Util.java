package testUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.TestBase;

public class Util extends TestBase {

	/*
	 * @method launchBrowser
	 * @argument String browser
	 * @return void
	 */
	public void launchBrowser(String browser) {
		switch (browser.toLowerCase()) {

		case "chrome":

			ChromeOptions chOptions = new ChromeOptions();
			chOptions.addArguments("--incognito");
			chOptions.addArguments("--start-maximized");

			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "//src//test//resources//drivers//chromedriver.exe");
			driver = new ChromeDriver(chOptions);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.get(baseUrl+"/flights");
			break;

		case "firefox":

			FirefoxOptions ffOptions = new FirefoxOptions();
			ffOptions.setAcceptInsecureCerts(true);
			ffOptions.addArguments("-private");
			ffOptions.addArguments("--start-maximized");

			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir") + "//src//test//resources//drivers//geckodriver.exe");
			driver = new FirefoxDriver(ffOptions);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			//driver.manage().window().maximize();
			driver.get(baseUrl+"/flights");
			break;

		case "edge":
			System.setProperty("webdriver.edge.driver",
					System.getProperty("user.dir") + "//src//test//resources//drivers//msedgedriver.exe");
			driver = new EdgeDriver();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.get(baseUrl+"/flights");
			break;
		}
	}

	/*
	 * @method captureScreenShot
	 * @return String
	 */
	public static void captureScreenShot() {
		// String screenShotPath = null;
		try {
			File outputFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String absolutePath = System.getProperty("user.dir") + "/screenShots/"
					+ new SimpleDateFormat("dd_MM_yyyy_HHmmss").format(new Date()) + ".png";
			// String basePath = absolutePath;
			// screenShotPath = "../" + new File(basePath).toURI().relativize(new
			// File(absolutePath).toURI()).getPath();
			FileUtils.copyFile(outputFile, new File(absolutePath));
		} catch (final Exception e) {

		}
	}

	/**
	 * @method getWebElement to return WebElement based on locators
	 * @param locator
	 * Eg: 'id:SubmitButton'
	 * @return WebElement
	 */
	public static WebElement getWebElement(String locator) {

		WebElement element = null;

		String[] splitLocator = locator.split(":");
		String locatorType = splitLocator[0];
		String locatorValue = splitLocator[1];

		try {
			if (locatorType.toLowerCase().equals("id"))
				element = driver.findElement(By.id(locatorValue));
			else if (locatorType.toLowerCase().equals("name"))
				element = driver.findElement(By.name(locatorValue));
			else if ((locatorType.toLowerCase().equals("classname")) || (locatorType.toLowerCase().equals("class")))
				element = driver.findElement(By.className(locatorValue));
			else if ((locatorType.toLowerCase().equals("tagname")) || (locatorType.toLowerCase().equals("tag")))
				element = driver.findElement(By.className(locatorValue));
			else if ((locatorType.toLowerCase().equals("linktext")) || (locatorType.toLowerCase().equals("link")))
				element = driver.findElement(By.linkText(locatorValue));
			else if (locatorType.toLowerCase().equals("partiallinktext"))
				element = driver.findElement(By.partialLinkText(locatorValue));
			else if ((locatorType.toLowerCase().equals("cssselector")) || (locatorType.toLowerCase().equals("css")))
				element = driver.findElement(By.cssSelector(locatorValue));
			else if (locatorType.toLowerCase().equals("xpath"))
				element = driver.findElement(By.xpath(locatorValue));
			else
				throw new Exception("Unknown locator type '" + locatorType + "'");
		} catch (final Exception e) {

		}

		return element;
	}

	/*
	 * @method isElementEnabled
	 * @param String locator
	 * @return boolean
	 */
	public boolean isElementEnabled(String locator) {
		boolean enabledFlag = false;
		try {
			enabledFlag = getWebElement(locator).isEnabled();
		} catch (Exception e) {

		}

		return enabledFlag;
	}

	/*
	 * @method SelectUsingIndex
	 * @argument String locator
	 * @argument int index
	 */
	public void SelectUsingIndex(String locator, int index) {

		Select select = new Select(getWebElement(locator));
		select.selectByIndex(index-1);
	}

	/*
	 * @method SelectUsingValue
	 */
	public void SelectUsingValue(String locator, String value) {
		Select select = new Select(getWebElement(locator));
		select.selectByValue(value);
	}

	/*
	 *
	 */
	public String getHtmlAttribute(String locator,String htmlAttr) {
		String attrVal = null;

		if(isElementEnabled(locator)) {
			attrVal = getWebElement(locator).getAttribute(htmlAttr).toString();
		}

		return attrVal;
	}

	/*
	* @method waitForElementOnDOM
	* @argument WebDriver driver
	* @argument String locator
	* @argument long timeOutInSeconds
	* @return void
	*/
	/*public static void waitForElementOnDOM(WebDriver driver, String locator, long timeOutInSeconds) {

		WebDriverWait wait = new WebDriverWait(driver,timeOutInSeconds);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(configFile.getProperty("locator")));
		}
		catch (NoSuchElementException e) {
		}
	}*/
		
}
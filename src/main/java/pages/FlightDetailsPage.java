package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import testUtils.Util;

public class FlightDetailsPage extends TestBase {

	ArrayList<String> Prices = new ArrayList<String>();
	JavascriptExecutor js = (JavascriptExecutor) driver;
	private static WebDriver driver;

	public FlightDetailsPage(WebDriver driver){
		this.driver = driver;
	}

	public void selectFlightByDepTime(String depTime) throws InterruptedException {

		/*
		 * String depTimes = "//div[contains(@class,'__details ms-grid-row-2')]" +
		 * "/div[@class='ms-grid-column-2']" +
		 * "/div[contains(@class,'__time ms-grid-row-2')]/div[1]";
		 */

		WebElement bookBtn = null;

		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()='Search']")));
		WebElement btnSearch = driver.findElement(By.xpath("//button[text()='Search']"));
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(btnSearch)));
		Util.captureScreenShot();

		String gridCol2 = "//div[contains(@class,'details__time ms-grid-row-2')]/div[1]";

		List<WebElement> flightTimes = driver.findElements(By.xpath(gridCol2));
		boolean flightFound = false;

		for (WebElement flightTime : flightTimes) {

			WebElement elePrice = flightTime.findElement(By.xpath("../../following-sibling::div[contains(@class,'price flex ms-grid-column-3')]/div[2]"));
			String Price = elePrice.getText();
			Prices.add(Price);

			if (flightTime.getText().equals(depTime)) {
				flightFound = true;
				bookBtn = flightTime.findElement(By.xpath("../../following-sibling::div[contains(@class,'ms-grid-column-4')]/button"));
				break;
			}
		}

		if (flightFound == false) {
			System.out.println("No Flights found for the requested time: "+depTime);
		}

		if(flightFound) {
			bookBtn.click();
			reviewItinerary(depTime);
		}
	}

	/*
	 * @method reviewItinerary
	 * @argument String depTime
	 * @return void
	 */
	public boolean reviewItinerary(String depTime) throws InterruptedException {

		boolean timeMatches = false;

		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

		//switch to new tab
		driver.switchTo().window(tabs.get(1));

		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[@class='pageTitle']")));

		// Capture Itinerary page
		Util.captureScreenShot();

		WebElement itinerary_onWardJourney = driver.findElement(By.xpath("//div[@class='itinerary clearFix onwBlock']//ul"));

		List<WebElement> details = itinerary_onWardJourney.findElements(By.tagName("li"));
		String source_Time = details.get(1).findElement(By.cssSelector("span.placeTime")).getText();
		String[] sourceTime = source_Time.split(" ");
		String time = sourceTime[1];

		// Assert the selected depTime with time in Itinerary page.
		if (depTime.equalsIgnoreCase("time")) {
			timeMatches = true;
		}

		return timeMatches;
	}
}
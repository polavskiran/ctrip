package pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import testUtils.Util;

public class ItineraryPage extends TestBase {

	public void reviewItinerary() throws InterruptedException {
		
		Thread.sleep(5000);
		System.out.println(driver.getCurrentUrl()+"==="+driver.getTitle());

		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[@class='pageTitle']")));

		// Capture Itinerary page
		Util.captureScreenShot();

		WebElement itinerary_onWardJourney = driver
				.findElement(By.xpath("//div[@class='itinerary clearFix onwBlock']//ul"));
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.stalenessOf(itinerary_onWardJourney)));

		List<WebElement> details = itinerary_onWardJourney.findElements(By.tagName("li"));
		String source_Time = details.get(1).findElement(By.cssSelector("span.placeTime")).getText();
		String[] sourceTime = source_Time.split(" ");
		String time = sourceTime[1];
		System.out.println("Time is: " + time);

		// Assert.assertEquals(departureTime, dateText);
	}
}
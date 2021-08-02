package pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import testUtils.Util;

public class HomePage extends TestBase {

	private Util util = new Util();
	
	public String getTitle() {
		String homePageTitle = "";

		try {
			homePageTitle = driver.getTitle();
		}
		catch (Exception e) {
		}

		return homePageTitle;
	}
	
	public void setValue(String inputVal) {
		
	}

	/*
	 * @method enterOriginStation
	 * @argument String originCity
	 * @return void
	 */
	public static void enterOriginStation(String originCity) throws InterruptedException {

		Util.getWebElement(configFile.getProperty("source_stn")).click();
		Util.getWebElement(configFile.getProperty("source_stn")).clear();
		Util.getWebElement(configFile.getProperty("source_stn")).sendKeys(originCity);

		Thread.sleep(2000);
		WebElement listCities = driver.findElement(By.cssSelector("ul#ui-id-1"));
		List<WebElement> Cities = listCities.findElements(By.tagName("li"));		
		Cities.get(0).click();
	}

	/*
	 * @method enterDestination
	 * @argument String destCity
	 * @return void
	 */
	public static void enterDestination(String destCity) throws InterruptedException {

		Util.getWebElement(configFile.getProperty("dest_stn")).click();
		Util.getWebElement(configFile.getProperty("dest_stn")).clear();
		Util.getWebElement(configFile.getProperty("dest_stn")).sendKeys(destCity);

		/*
		 * WebElement toCity = driver.findElement(By.id("ToTag")); toCity.click();
		 * toCity.clear(); toCity.sendKeys(destCity);
		 */
		Thread.sleep(2000);
		WebElement listCities = driver.findElement(By.cssSelector("ul#ui-id-2"));
		List<WebElement> Cities = listCities.findElements(By.tagName("li"));
		Cities.get(0).click();
	}

	/*
	 * @method enterDate
	 * @argument String date
	 * @return void
	 */
	public static void enterDate(String date) throws ParseException {

		Util.getWebElement(configFile.getProperty("departOn")).click();

		String strDate;

		Date fromDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
		SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy");
		strDate = sdf.format(fromDate);

		String[] dates = strDate.split(" ");
		String monthday = dates[0];
		String month = dates[1].trim();
		// String year = dates[2];

		WebElement monthVal = driver
				.findElement(By.xpath("//div[@id='ui-datepicker-div']//span[@class='ui-datepicker-month']"));

		while (!driver.findElement(By.xpath("//div[@id='ui-datepicker-div']//span[@class='ui-datepicker-month']"))
				.getText().equals(month)) {
			driver.findElement(By.xpath("//div[@class='monthBlock last']//a[@class='nextMonth ']")).click();
		}

		List<WebElement> allDays = driver.findElements(By.xpath("//table[@class='calendar']//td"));

		for (WebElement day : allDays) {
			String sDate = day.getText();

			if (sDate.contains(monthday)) {
				day.click();
				break;
			}
		}
	}

	/*
	 * @method pick Adults
	 */
	public void selectPassengers(int adults, int child, int infants) {
		if(child >= 1)
			child = child+1;

		util.SelectUsingIndex(configFile.getProperty("adults"), adults);
		util.SelectUsingIndex(configFile.getProperty("child"), child);
	}

	/*
	 * @method searchFlights
	 *
	 * @return void
	 */
	public void searchFlights() throws ParseException, InterruptedException {

		util.launchBrowser(browserName);
		enterOriginStation(configFile.getProperty("sourceStation"));
		enterDestination(configFile.getProperty("destinationStation"));
		enterDate(configFile.getProperty("travelDate"));
		this.selectPassengers(2, 2, 0);

		// Click on Search button
		Util.getWebElement(configFile.getProperty("btnSearch")).click();
	}
}
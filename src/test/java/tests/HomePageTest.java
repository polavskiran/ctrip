package tests;

import java.text.ParseException;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import pages.HomePage;
import pages.TestBase;

public class HomePageTest extends TestBase {

	String homepagetitle = "Flight bookings, Cheap flights, Lowest Air tickets @Cleartrip";

	@Test
	public void homePageTest() throws InterruptedException {

		try {
			new HomePage().searchFlights();
			String pageTitle = new HomePage().getTitle();
			Assert.assertEquals(pageTitle,homepagetitle);
			//new FlightDetailsPage().selectFlightByDepTime("11:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@AfterTest
	public void tearDown() {
		if(driver != null)
			driver.quit();
	}
}
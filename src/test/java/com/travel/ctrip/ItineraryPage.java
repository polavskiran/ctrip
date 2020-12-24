package com.travel.ctrip;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class ItineraryPage {

	static WebDriver driver;
	static String url = "https://www.cleartrip.com/flights/itinerary/68ac7a1958-f32e-4ae9-88d1-201123221149/review";
	static List<String> itineraryDetails = new ArrayList<String>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		FirefoxOptions ffOptions = new FirefoxOptions();
		ffOptions.setAcceptInsecureCerts(true);
		ffOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		ffOptions.addArguments("-private");		

		System.setProperty("webdriver.gecko.driver","E:\\Drivers\\geckodriver.exe");
		driver = new FirefoxDriver(ffOptions);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(url);
		
		WebElement itinerary_onWardJourney = driver.findElement(By.xpath("//div[@class='itinerary clearFix onwBlock']//ul"));
		
		List<WebElement> details = itinerary_onWardJourney.findElements(By.tagName("li"));
		
		itineraryDetails.add(details.get(0).findElement(By.cssSelector("span.name")).getText());
		itineraryDetails.add(details.get(1).findElement(By.cssSelector("span.placeTime")).getText());
		itineraryDetails.add(details.get(2).getText());
		itineraryDetails.add(details.get(3).findElement(By.cssSelector("span.placeTime")).getText());
		
		String source_Time = details.get(1).findElement(By.cssSelector("span.placeTime")).getText();
		String[] sourceTime = source_Time.split(" ");
		String time = sourceTime[1];
		System.out.println("Time is: "+time);
		
		Iterator<String> itr = itineraryDetails.iterator();
		while(itr.hasNext()) {
			System.out.println(itr.next());
		}
	}
}
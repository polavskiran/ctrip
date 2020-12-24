package com.travel.ctrip;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class TestPage {

	static String link = "https://www.cleartrip.com/flights/results?origin=Hyderabad,+IN+-+Rajiv+Gandhi+International+(HYD)&from=HYD&destination=Mumbai,+IN+-+Chatrapati+Shivaji+Airport+(BOM)&to=BOM&depart_date=10/12/2020&adults=1&childs=0&infants=0&class=Economy&airline=&carrier=&intl=n&sd=1606224034641&rnd_one=O";
	static WebDriver driver;
	static List<String> Prices = new ArrayList<String>();

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		FirefoxOptions ffOptions = new FirefoxOptions();
		ffOptions.setAcceptInsecureCerts(true);
		ffOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		ffOptions.addArguments("-private");

		System.setProperty("webdriver.gecko.driver", "E:\\Drivers\\geckodriver.exe");
		driver = new FirefoxDriver(ffOptions);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(link);

		String totalFlights = driver.findElement(By.xpath("//h2/b")).getText();
		System.out.println(totalFlights);
		String[] flightsplit = totalFlights.split(" ");
		int iflights = Integer.parseInt(flightsplit[0]);
		System.out.println(iflights);

		String gridCol2 = "//div[contains(@class,'details__time ms-grid-row-2')]/div[1]";

		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		for(int i=0; i <= 5; i++) {
			js.executeScript("window.scrollBy(0,500)","");
		}

		List<WebElement> flightTimes = driver.findElements(By.xpath(gridCol2));

		for (WebElement flightTime : flightTimes) {

			WebElement elePrice = flightTime.findElement(
					By.xpath("../../following-sibling::div[contains(@class,'price flex ms-grid-column-3')]/div[2]"));
			String Price = elePrice.getText();
			Prices.add(Price);
		}

		Iterator<String> priceitr = Prices.iterator();

		while (priceitr.hasNext()) {
			System.out.println(priceitr.next().substring(1));
		}
	}
}
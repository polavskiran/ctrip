package pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

public class TestBase {

	protected static WebDriver driver;
	protected static String baseUrl;
	protected static String browserName;
	protected static Properties configFile = new Properties();

	public TestBase() {

		//browserName = System.getProperty("browser");

		try {
			File file = new File(System.getProperty("user.dir") + "//src//test//resources//propertyFiles//config.properties");
			FileInputStream inputStream = new FileInputStream(file);

			configFile.load(inputStream);
			//browserName = configFile.getProperty("browser");
			browserName = System.getProperty("browser");
			baseUrl		= configFile.getProperty("baseURL");
		}catch(Exception e) {

		}
	}
}
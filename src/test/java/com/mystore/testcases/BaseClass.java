package com.mystore.testcases;

import java.io.File;
import java.io.IOException;
//import java.time.Duration;
//import java.time.LocalDateTime;

import org.apache.logging.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import com.mystore.utilities.ReadConfig;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

	ReadConfig readConfig = new ReadConfig();

	String url = readConfig.getBaseUrl();
	String browser = readConfig.getBrowser().toLowerCase();

	public String emailAddress = readConfig.getEmail() ;
	String password = readConfig.getPassword();
	
	
	public static WebDriver driver;
	public static Logger logger;

	@BeforeMethod
	public void setup()
	{

		//launch browser
//		switch(browser)
//		{
//		case "chrome":
//			WebDriverManager.chromedriver().setup();
//			driver = new ChromeDriver();
//			break;
//
//		case "msedge":
//			WebDriverManager.edgedriver().setup();
//			driver = new EdgeDriver();
//			break;
//
//		case "firefox":
//			WebDriverManager.firefoxdriver().setup();
//			driver = new FirefoxDriver();
//			break;
//		default:
//			driver = null;
//			break;
//
//		}

		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("msedge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else {
			driver = null;
			System.out.println("Invalid browser specified");
		}


		//implicit wait of 10 secs
//		driver.manage().timeouts().implicitlyWait(LocalDateTime.now().getSecond());

		//for logging
		logger = LogManager.getLogger("MyStoreV1");
		
		//open url
		driver.get(url);
		logger.info("url opened");

	}



	@AfterMethod
	public void tearDown()
	{
		driver.close();
		driver.quit();
	}

	
	//user method to capture screen shot
	public void captureScreenShot(WebDriver driver,String testName) throws IOException
	{
		//step1: convert webdriver object to TakesScreenshot interface
		TakesScreenshot screenshot = ((TakesScreenshot)driver);
		
		//step2: call getScreenshotAs method to create image file
		
		File src = screenshot.getScreenshotAs(OutputType.FILE);
		
		File dest = new File(System.getProperty("user.dir") + "//Screenshots//" + testName + ".png");
	
		//step3: copy image file to destination
		FileUtils.copyFile(src, dest);
	}
	

}

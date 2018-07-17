package com.automation.selenium.ExtentReportScreenShot;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentScreenShot {

	public static WebDriver driver;
	public ExtentReports extentReport;
	public ExtentTest extentTest;
	String tcName;
	WebElement next ;

	@BeforeTest
	public void setExtent() {

		extentReport = new ExtentReports(System.getProperty("user.dir") + "//Reports//result.html");

	}

	@AfterTest
	public void endExtent() {

		extentReport.close();

	}

	public static String getScreenShot(WebDriver driver, String ScreenShotName) throws IOException {

		SimpleDateFormat dateName = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File file = screenshot.getScreenshotAs(OutputType.FILE);
		File fileDestination = new File(
				System.getProperty("user.dir") + "\\ScreenShots\\" + ScreenShotName + dateName.format(new Date()) + ".jpeg");
		FileUtils.copyFile(file, fileDestination);
		return ScreenShotName;

	}

	public static void captureElementScreenshot(WebDriver driver,WebElement element, String tcName) throws IOException {

		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File file = screenshot.getScreenshotAs(OutputType.FILE);

		int width = element.getSize().getWidth();
		int height = element.getSize().getWidth();
		
		Point point = element.getLocation();
		int xCoordinate = point.getX();
		int yCoordinate = point.getY();
		
		BufferedImage img = ImageIO.read(file);
		BufferedImage dest = img.getSubimage(xCoordinate, yCoordinate, width, height);
		ImageIO.write(dest, "jpeg", file);
		FileUtils.copyFile(file,
				new File("E:\\selenium\\ExtentReportScreenShot\\ElementScreenshot\\" + tcName + ".jpeg"));

	}

	@BeforeSuite
	public void launch() {
		System.setProperty("webdriver.chrome.driver",
				"E:\\selenium\\ExtentReportScreenShot\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	@AfterSuite
	public void Browserclose() {
		if (driver != null)
			driver.close();
	}

	@Test(priority = 0)
	public void gmailLogin() throws InterruptedException, IOException {
		driver.get("https://www.gmail.com");
		driver.findElement(By.id("identifierId")).sendKeys("jbabu294@gmail.com");
		extentTest.log(LogStatus.INFO, "gmail entered as jbabu294");
		driver.findElement(By.id("identifierNext")).click();
		extentTest.log(LogStatus.INFO, "pressed enter button");

		Thread.sleep(2000);
		driver.findElement(By.name("password")).sendKeys("76264326");
		extentTest.log(LogStatus.INFO, "password is entered");
		next = driver.findElement(By.id("passwordNex"));
		next.click();
		
		extentTest.log(LogStatus.INFO, "click on sign in button");

	}

	@Test(enabled=true)
	
	public void fbLogin() {

		driver.get("http://facebook.com");
		extentTest.log(LogStatus.INFO, "facebook page is launched");
		WebElement un = driver.findElement(By.id("email"));
		un.clear();
		un.sendKeys("jbabu5467@gmail.com");
		extentTest.log(LogStatus.INFO, "username is entered as jbabu5467");
		driver.findElement(By.id("pass")).sendKeys("jagadeesh");
		extentTest.log(LogStatus.INFO, "password is entered as jagadeesh");
	}

	@BeforeMethod
	public void beforeTC(Method method) {
		String testName = method.getName();
		extentTest = extentReport.startTest(testName);
	}

	@AfterMethod
	public void afterTC(ITestResult result) throws IOException {
		tcName = result.getName();
		if (result.getStatus() == ITestResult.FAILURE) {
			System.out.println("FAILED TC NAME IS: " + result.getName());
			ExtentScreenShot.getScreenShot(driver, tcName);
			//ExtentScreenShot.captureElementScreenshot(driver, next, tcName);
			extentTest.log(LogStatus.FAIL, result.getThrowable());
			extentTest.log(LogStatus.FAIL, extentTest.addScreenCapture(ExtentScreenShot.getScreenShot(driver, tcName)));
			extentTest.log(LogStatus.FAIL, extentTest.addScreencast(ExtentScreenShot.getScreenShot(driver, tcName)));

		}
		extentReport.endTest(extentTest);
		extentReport.flush();

	}
}

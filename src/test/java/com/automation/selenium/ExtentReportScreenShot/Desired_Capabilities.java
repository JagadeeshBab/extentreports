package com.automation.selenium.ExtentReportScreenShot;

import java.io.File;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Desired_Capabilities {

	public static void main(String[] args) {
		
		DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
		DesiredCapabilities cap = new DesiredCapabilities();
		File file = new File("E:\\selenium\\JavaPrograms\\drivers\\IEDriverServer.exe");
		capability.setCapability(CapabilityType.BROWSER_NAME, "ie");
		capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		capability.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
		System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
		WebDriver driver = new InternetExplorerDriver(capability);
		driver.manage().window().maximize();
		driver.get("http:\\www.gmail.com");
			}

}

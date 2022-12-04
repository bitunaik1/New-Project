package com.crm.vtiger.genericutility;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.crm.vtiger.objectrepository.LoginPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	public WebDriver driver;
//	public DatabaseUtility dLib=new DatabaseUtility();
	public FileUtility fLib=new FileUtility();
	public static WebDriver listenerDriver;
@BeforeSuite
public void createDatabaseConnection() {
	//dLib.createConnectionToDB();
	System.out.println("DB connected");
}
@BeforeTest
public void pralallelExecution() {
	System.out.println("Parallel Execution started.");
}
@BeforeClass
public void launchBrowser() {
	String browser=null;
	try {
		browser=fLib.getData("browser");
	} catch (Throwable e) {
		browser="chrome";
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if(browser.equals("chrome")) {
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
	}else if(browser.equals("firefox")) {
		WebDriverManager.firefoxdriver().setup();
		driver=new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
	}else if(browser.equals("edge")) {
		WebDriverManager.edgedriver().setup();
		driver=new EdgeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
	}else {
		System.out.println(browser+" is not available in this framework, Please change the browser to CHROME or FIREFOX or EDGE");
	}
	driver.get(IPathConstants.VTIGER_URL);
	listenerDriver=driver;
}
@BeforeMethod
public void loginToApplication() {
	new LoginPage(driver).login();
}
@AfterMethod
public void logoutFromApplication() {
	System.out.println("logout");
}
@AfterClass
public void closeBrowser() {
	try {
		Thread.sleep(6000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	driver.close();
}
@AfterTest
public void closePrallel() {
	System.out.println("close parallel");
}
@AfterSuite
public void closeDBConnection() {
//	dLib.closeConnection();
	System.out.println("Database connection closed.");
}
}

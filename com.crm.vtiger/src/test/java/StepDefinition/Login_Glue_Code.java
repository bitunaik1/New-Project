package StepDefinition;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.crm.vtiger.genericutility.FileUtility;
import com.crm.vtiger.genericutility.IPathConstants;
import com.crm.vtiger.objectrepository.LoginPage;

import io.cucumber.java.en.*;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Login_Glue_Code {
	public WebDriver driver;
//	public DatabaseUtility dLib=new DatabaseUtility();
	public FileUtility fLib=new FileUtility();
	@Given("^user is on login page$")
	public void user_is_on_login_page() {
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
	}

	@When("^user enters username and password click on enter$")
	public void user_enters_username_and_password_click_on_enter() {
		new LoginPage(driver).login();
	}

	@Then("^user is navigated to the home page$")
	public void user_is_navigated_to_the_home_page() {
		System.out.println("User is in login page");
	
	}
}

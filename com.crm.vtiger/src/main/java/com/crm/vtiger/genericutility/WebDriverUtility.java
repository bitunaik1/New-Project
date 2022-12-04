package com.crm.vtiger.genericutility;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

public class WebDriverUtility {
	/**
	 * This method is used to take the screeshot.
	 * @param screenshotName
	 * @param driver
	 * @return
	 */
	public String takeScreenshot(String screenshotName,WebDriver driver) {
		String dateAndTime = Calendar.getInstance().getTime().toString().replace(':', '_');
		String path="./errorShot"+screenshotName+dateAndTime+".png";
		TakesScreenshot ts=(TakesScreenshot)driver;
		File source=ts.getScreenshotAs(OutputType.FILE);
		File destination=new File(path);
		try {
			FileUtils.copyFile(source, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return destination.getAbsolutePath();
	}
	
	/**
	 * 
	 * @param driver
	 * @param by
	 * @return
	 */
	public static boolean IsWebElementExists(WebDriver driver, By by) {
		boolean blnExist = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(0));
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			blnExist = true;
		} catch (Exception e) {
			blnExist = false;
		}
		return blnExist;
	}
	
	
	
	public static void ScrollByPage(WebDriver driver) {
	      
		 JavascriptExecutor js=(JavascriptExecutor)driver;
	     js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	    }
	
	public static boolean VerifyPopupDialogDisplayed(WebDriver driver) {
		
		boolean blnStatus = false;
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(WebDriverUtility.IsWebElementExists(driver, By.className("alertify-dialog"))) {
			blnStatus = true;
		}else if(WebDriverUtility.IsWebElementExists(driver, By.className("modal-dialog"))){
			blnStatus = true;
		}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//[@class='modal-dialog' and count(parent::[@aria-hidden='false'  and (@style='display: block;')] ) > 0]"))) {
			blnStatus = true;
		}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//[contains(@class,'modal-dialog') and count(ancestor::[contains(@style,'display: block')])=1]"))) {
			blnStatus = true;
		}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//*[contains(@class,'modal') and contains(@class,'close')]"))) {
			blnStatus = true;
		}else if(WebDriverUtility.IsWebElementExists(driver, By.className("has-close-icon"))) {
			blnStatus = true; 
		}
		else {
			System.out.println("FAILED: POPUP DIALOG IS NOT DISPLAYED");
		}
		
		return blnStatus;
		
	}
	
	
	public static void funcUtil_HandleSync_Wait(WebDriver driver) {

		while(WebDriverUtility.IsWebElementExists(driver, By.xpath("//*[contains(@class,'loading')]"))) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static WebElement GetSection(WebDriver driver, String strSection) {

		WebElement objSection = null;
		// Handle Forms and its corresponding fields
		if (WebDriverUtility.IsWebElementExists(driver,By.xpath("//*[contains(@class,'logintitle') and (text()='" + strSection + "')]"))) {
			By xpathForm = By.xpath("//[contains(@class,'logintitle') and (text()='" + strSection + "')]/parent::/parent::*");
			if (WebDriverUtility.IsWebElementExists(driver, xpathForm)) {
				objSection = driver.findElement(xpathForm);
			}
		}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//*[contains(@class,'modal-title') and (text()='"+strSection+"')]"))) {
			By xpathForm = By.xpath("//*[contains(@class,'modal-title') and (text()='" + strSection + "')]/ancestor::form");
			if (WebDriverUtility.IsWebElementExists(driver, xpathForm)) {
				objSection = driver.findElement(xpathForm);
			}
		}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//*[@class='heading' and text()='"+strSection+"']"))) {
			By xpathForm = By.xpath("//[@class='heading' and text()='"+strSection+"']/parent::/parent::*");
			if (WebDriverUtility.IsWebElementExists(driver, xpathForm)) {
				objSection = driver.findElement(xpathForm);
				if(objSection.getTagName().equalsIgnoreCase("h3")) {
					objSection = objSection.findElement(By.xpath("parent::/parent::"));
				}
			}
		}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//h3[text()='"+strSection+"']"))) {
			By xpathForm = By.xpath("//h3[text()='"+strSection+"']/ancestor::div[(contains(@class,'tab-pane'))]");
			if (WebDriverUtility.IsWebElementExists(driver, xpathForm)) {
				objSection = driver.findElement(xpathForm);
			}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//h3[text()='"+strSection+"']/ancestor::[count(parent::[contains(@class,'formcontainer')])>0]"))) {
				objSection = driver.findElement(By.xpath("//h3[text()='"+strSection+"']/ancestor::[count(parent::[contains(@class,'form')])>0]"));
			}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//*[text()='"+strSection+"']/ancestor::div[contains(@class,'isp-content-body')]"))) {
				objSection = driver.findElement(By.xpath("//*[text()='"+strSection+"']/ancestor::div[contains(@class,'isp-content-body')]"));	
			}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//*[text()='"+strSection+"']/ancestor::form"))) {
				objSection = driver.findElement(By.xpath("//*[text()='"+strSection+"']/ancestor::form"));
			}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//*[text()='"+strSection+"']"))) {
				objSection=driver.findElement(By.xpath("//h3[text()='"+strSection+"']/parent::*[@class='body-content']"));
			}
		}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//h3[contains(text(),'"+strSection+"')]"))) {
			By xpathForm = By.xpath("//h3[contains(text(),'"+strSection+"')]/ancestor::div[(contains(@class,'tab-pane'))]");
			if (WebDriverUtility.IsWebElementExists(driver, xpathForm)) {
				objSection = driver.findElement(xpathForm);
			}		
		}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//b[text()='"+strSection+"']/ancestor::div[contains(@class,'contentContainer') and not(contains(@class,'viewmode'))]"))) {
			objSection = driver.findElement(By.xpath("//b[text()='"+strSection+"']/ancestor::div[contains(@class,'contentContainer') and not(contains(@class,'viewmode'))]"));
		}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//[text()='"+strSection+"']/ancestor::[@role='tabpanel']"))) {
			objSection = driver.findElement(By.xpath("//[text()='"+strSection+"']/ancestor::[@role='tabpanel']"));
		}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//*[@class='page-title' and text()='"+strSection+"']"))) {
			objSection = driver.findElement(By.xpath("//[@class='page-title' and text()='"+strSection+"']/ancestor::[contains(@class,'content')]"));
		}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//h5[text()='"+strSection+"']"))) {
			objSection = driver.findElement(By.xpath("//h5[text()='"+strSection+"']/ancestor::*[contains(@class,'page-data')]"));
		}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//*[contains(text(),'"+strSection+"') and @class='page-title']"))) {
			objSection=driver.findElement(By.xpath("//[contains(text(),'"+strSection+"') and @class='page-title']/ancestor::[contains(@class,'content')]"));
		}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//h5[contains(.,'"+strSection+"')]"))) {
			objSection=driver.findElement(By.xpath("//h5[contains(.,'"+strSection+"')]/ancestor::div[@class='visitor-wrapper']"));
		}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//span[contains(@class,'input-label') and text()='"+strSection+"']"))) {
			objSection=driver.findElement(By.xpath("//span[contains(@class,'input-label') and text()='"+strSection+"']/ancestor::div[@class='kiosk-data']"));
		}
		

		return objSection;

	}
	
	
	public static boolean scrollIntoView(WebDriver driver, WebElement element) {
		boolean flag = true;
		if (element != null) {
			try {
				// JavascriptExecutor jExecutor = (JavascriptExecutor)driver;
				// jExecutor.executeScript("arguments[0].scrollIntoView(true)",element);
				Actions action = new Actions(driver);
				action.moveToElement(element).build().perform();
			} catch (Exception e) {
				System.out.println("Scroll to element : Unexpected error occured." + e.getMessage());
				flag = false;
			}
		} else {
			System.out.println("Scroll to element : Element is not found.");
			flag = false;
		}

		return flag;
	}
	
public static boolean VerifyPageDisplayed(WebDriver driver, String strPageTitle) {
		
		boolean blnStatus = false;
		//WebDriverUtility.funcUtil_HandleSync_Wait(driver);
		if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//*[@class='page-header-title']"))) {
			WebElement objPageTitle = driver.findElement(By.xpath("//*[@class='page-header-title']"));
			if(objPageTitle.getText().contains(strPageTitle)||objPageTitle.getText().equalsIgnoreCase(strPageTitle)) {
				System.out.println("PASSED: USER IS NAVIGATED TO THE '"+strPageTitle+"' PAGE");
				blnStatus = true;
			}else {
				System.out.println("FAILED: "+strPageTitle+" PAGE IS NOT DISPLAYED");
			}
		}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//*[@class='page-title']"))) {
			WebElement objPageTitle = driver.findElement(By.xpath("//*[@class='page-title']"));
			if(objPageTitle.getText().contains(strPageTitle)||objPageTitle.getText().equalsIgnoreCase(strPageTitle)) {
				System.out.println("PASSED: USER IS NAVIGATED TO THE '"+strPageTitle+"' PAGE");
				blnStatus = true;
			}else {
				System.out.println("FAILED: "+strPageTitle+" PAGE IS NOT DISPLAYED");
			}
		}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//h3[text()='"+strPageTitle+"']"))) {
			WebElement objPageTitle = driver.findElement(By.xpath("//h3[text()='"+strPageTitle+"']"));
			if(objPageTitle.getText().contains(strPageTitle)||objPageTitle.getText().equalsIgnoreCase(strPageTitle)) {
				System.out.println("PASSED: USER IS NAVIGATED TO THE '"+strPageTitle+"' PAGE");
				blnStatus = true;
			}else {
				System.out.println("FAILED: "+strPageTitle+" PAGE IS NOT DISPLAYED");
			}
		}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//h2[text()='"+strPageTitle+"']"))) {
			WebElement objPageTitle = driver.findElement(By.xpath("//h2[text()='"+strPageTitle+"']"));
			if(objPageTitle.getText().contains(strPageTitle)||objPageTitle.getText().equalsIgnoreCase(strPageTitle)) {
				System.out.println("PASSED: USER IS NAVIGATED TO THE '"+strPageTitle+"' PAGE");
				blnStatus = true;
			}else {
				System.out.println("FAILED: "+strPageTitle+" PAGE IS NOT DISPLAYED");
			}
		}
		else {
			System.out.println("FAILED: PAGE TITLE IS NOT DISPLAYED");
		}
		
		
		return blnStatus;
		
	}




public static boolean SetFieldValue(WebDriver driver, String strSection, String strControl, String strValue) {
	try {
		boolean blnStatus = false;
		if(!strValue.isEmpty() && !strValue.equalsIgnoreCase("blank")) {
			if (!strSection.isEmpty()) {
				WebElement objForm = GetSection(driver, strSection);
				// Handle Forms and its corresponding fields
				if (objForm!=null) {
					if (WebDriverUtility.IsWebElementExists(driver,  By.xpath("descendant::label[contains(@class,'control') and contains(text(),'"+strControl+"') and count(parent::*[not(contains(@style,'display: none'))]) > 0]"))) {
						//CALENDAR INPUT
						if(WebDriverUtility.IsWebElementExists(driver,  By.xpath("descendant::label[contains(@class,'control') and contains(text(),'"+strControl+"')]/following-sibling::input[@data-format='MM/dd/yyyy']"))) {
							//							if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::label[contains(@class,'control') and contains(text(),'"+strControl+"')]/parent::div[contains(@data-bind,'datepicker')]"))) {
							WebElement objCalendarIcon = objForm.findElement(By.xpath("descendant::label[contains(@class,'control') and contains(text(),'"+strControl+"')]/following-sibling::span[contains(@class,'date_icon') or count(descendant::*[contains(@class,'calendar-icon')>0])]"));
							objCalendarIcon.click();
							if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//div[contains(@class,'datetimepicker') and contains(@style,'display: block')]"))) {
								WebElement objDateTimePicker = driver.findElement(By.xpath("//div[contains(@class,'datetimepicker') and contains(@style,'display: block')]"));
								objDateTimePicker.findElement(By.xpath("descendant::*[@class='datepicker-days']//*[@class='switch']")).click();
								objDateTimePicker.findElement(By.xpath("descendant::*[@class='datepicker-months']//*[@class='switch']")).click();
								SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy");
								String Expdate = null;
								if(strValue.equalsIgnoreCase("TODAY")||strValue.contains("Today"))
								{
									Date date=new Date();
									Expdate=format.format(date);
								}else if (strValue.contains("TODAY+")) {
									Date dt = new Date();
									Calendar c = Calendar.getInstance(); 
									c.setTime(dt); 
									c.add(Calendar.DATE, Integer.valueOf(strValue.replace("TODAY+", "")));
									dt = c.getTime();
									Expdate=format.format(dt);
								}
								else if(strValue.equalsIgnoreCase("TOMORROW")||(strValue.contains("Tomorrow")))
								{
									long date=new Date().getTime()+1*24*60*60*1000;
									Expdate=format.format(date);
								}
								else if(strValue.equalsIgnoreCase("DAYAFTERTOMORROW")||(strValue.contains("DayAfterTomorrow")))
								{
									long date=new Date().getTime()+2*24*60*60*1000;
									Expdate=format.format(date);
								}
								Date objDate = new SimpleDateFormat("MM/dd/yyyy").parse(Expdate);
								SimpleDateFormat objMonthFormat = new SimpleDateFormat("MMM");
								System.out.println(objMonthFormat.format(objDate));
								SimpleDateFormat objYearFormat = new SimpleDateFormat("yyyy");
								System.out.println(objYearFormat.format(objDate));
								SimpleDateFormat objDayFormat = new SimpleDateFormat("d");
								System.out.println(objDayFormat.format(objDate));
								try {
									objDateTimePicker.findElement(By.xpath("descendant::span[contains(@class,'year') and text()='"+objYearFormat.format(objDate)+"']")).click();
									objDateTimePicker.findElement(By.xpath("descendant::span[contains(@class,'month') and text()='"+objMonthFormat.format(objDate)+"']")).click();
									objDateTimePicker.findElement(By.xpath("descendant::td[contains(@class,'day') and not(contains(@class,'disabled')) and text()='"+objDayFormat.format(objDate)+"']")).click();
									objCalendarIcon.click();
									WebDriverUtility.writeLogs("PASSED: SET THE SECTION - " + strSection + " FIELD - " + strControl + " with the VALUE - " + strValue);
									blnStatus = true;
								}catch(Exception e) {
									WebDriverUtility.writeLogs("FAILED: SET THE SECTION - " + strSection + " FIELD - " + strControl + " with the VALUE - " + strValue);
									return false;
								}
							}else {
								WebDriverUtility.writeLogs("FAILED: CALENAR DATE TIME PICKER POPUP IS NOT DISPLAYED");
								return false;
							}
						}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::label[contains(@class,'control') and contains(text(),'"+strControl+"')]/parent::div[contains(@data-bind,'datepicker')]"))) {
							WebElement objCalendarIcon = objForm.findElement(By.xpath("descendant::label[contains(@class,'control') and contains(text(),'"+strControl+"')]/parent::*/descendant::span[contains(@class,'calender')]"));
							objCalendarIcon.click();
							if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//div[contains(@class,'datetimepicker') and contains(@style,'display: block')]"))) {
								WebElement objDateTimePicker = driver.findElement(By.xpath("//div[contains(@class,'datetimepicker') and contains(@style,'display: block')]"));
								objDateTimePicker.findElement(By.xpath("descendant::*[@class='datepicker-days']//*[@class='switch']")).click();
								objDateTimePicker.findElement(By.xpath("descendant::*[@class='datepicker-months']//*[@class='switch']")).click();
								SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy");
								String Expdate = null;
								if(strValue.equalsIgnoreCase("TODAY")||strValue.contains("Today"))
								{
									Date date=new Date();
									Expdate=format.format(date);
								}else if (strValue.contains("TODAY+")) {
									Date dt = new Date();
									Calendar c = Calendar.getInstance(); 
									c.setTime(dt); 
									c.add(Calendar.DATE, Integer.valueOf(strValue.replace("TODAY+", "")));
									dt = c.getTime();
									Expdate=format.format(dt);
								}
								else if(strValue.equalsIgnoreCase("TOMORROW")||(strValue.contains("Tomorrow")))
								{
									long date=new Date().getTime()+1*24*60*60*1000;
									Expdate=format.format(date);
								}
								else if(strValue.equalsIgnoreCase("DAYAFTERTOMORROW")||(strValue.contains("DayAfterTomorrow")))
								{
									long date=new Date().getTime()+2*24*60*60*1000;
									Expdate=format.format(date);
								}
								Date objDate = new SimpleDateFormat("MM/dd/yyyy").parse(Expdate);
								SimpleDateFormat objMonthFormat = new SimpleDateFormat("MMM");
								System.out.println(objMonthFormat.format(objDate));
								SimpleDateFormat objYearFormat = new SimpleDateFormat("yyyy");
								System.out.println(objYearFormat.format(objDate));
								SimpleDateFormat objDayFormat = new SimpleDateFormat("d");
								System.out.println(objDayFormat.format(objDate));
								try {
									objDateTimePicker.findElement(By.xpath("descendant::span[contains(@class,'year') and text()='"+objYearFormat.format(objDate)+"']")).click();
									objDateTimePicker.findElement(By.xpath("descendant::span[contains(@class,'month') and text()='"+objMonthFormat.format(objDate)+"']")).click();
									objDateTimePicker.findElement(By.xpath("descendant::td[contains(@class,'day') and not(contains(@class,'disabled')) and not(contains(@class,'old')) and text()='"+objDayFormat.format(objDate)+"']")).click();
									objCalendarIcon.click();
									WebDriverUtility.writeLogs("PASSED: SET THE SECTION - " + strSection + " FIELD - " + strControl + " with the VALUE - " + strValue);
									blnStatus = true;
								}catch(Exception e) {
									WebDriverUtility.writeLogs("FAILED: SET THE SECTION - " + strSection + " FIELD - " + strControl + " with the VALUE - " + strValue);
									return false;
								}
							}else {
								WebDriverUtility.writeLogs("FAILED: CALENAR DATE TIME PICKER POPUP IS NOT DISPLAYED");
								return false;
							}
						}
						//TO HANDLE INPUT TEXT BOX / PASSWORD
						else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::label[contains(@class,'control') and text()='"+strControl+"']/following-sibling::input[(@type='text' or @type='password') and count(parent::*[not(contains(@style,'display: none'))])>0]"))) { 
							List<WebElement> listInputs = objForm.findElements(By.xpath("descendant::label[contains(@class,'control') and text()='"+strControl+"']/following-sibling::input[(@type='text' or @type='password') and count(parent::*[not(contains(@style,'display: none'))])>0]"));
							for(WebElement objInput:listInputs) {
								if(objInput.isDisplayed()) {
									if (objInput.getAttribute("type").equalsIgnoreCase("text") ||objInput.getAttribute("type").equalsIgnoreCase("password")  ) {
										objInput.clear();
										objInput.sendKeys(strValue);
										objInput.sendKeys(Keys.TAB);
										WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl
												+ " with the VALUE - " + strValue);
										blnStatus = true;
										break;
									}	
								}
							}
							//TO HANDLE DROPDOWN
						}					//TO HANDLE INPUT TEXT BOX / PASSWORD
						else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::label[contains(@class,'control') and normalize-space(text())='"+strControl+"']/following-sibling::input[(@type='text' or @type='password') and count(parent::*[not(contains(@style,'display: none'))])>0]"))) { 
							List<WebElement> listInputs = objForm.findElements(By.xpath("descendant::label[contains(@class,'control') and normalize-space(text())='"+strControl+"']/following-sibling::input[count(parent::*[not(contains(@style,'display: none'))])>0]"));
							for(WebElement objInput:listInputs) {
								if(objInput.isDisplayed()) {
									if (objInput.getAttribute("type").equalsIgnoreCase("text") ||objInput.getAttribute("type").equalsIgnoreCase("password")  ) {
										objInput.clear();
										objInput.sendKeys(strValue);
										objInput.sendKeys(Keys.TAB);
										WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl
												+ " with the VALUE - " + strValue);
										blnStatus = true;
										break;
									}	
								}
							}
						}
						else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::label[contains(@class,'control') and normalize-space(text())='"+strControl+"']/following-sibling::*/input[(@type='text' or @type='password') and count(parent::*[not(contains(@style,'display: none'))])>0 and count(parent::*[not(contains(@class,'dateTimePicker'))])>0]"))) { 
							List<WebElement> listInputs = objForm.findElements(By.xpath("descendant::label[contains(@class,'control') and normalize-space(text())='"+strControl+"']/following-sibling::*/input[count(parent::*[not(contains(@style,'display: none'))])>0]"));
							for(WebElement objInput:listInputs) {
								if(objInput.isDisplayed()) {
									if (objInput.getAttribute("type").equalsIgnoreCase("text") ||objInput.getAttribute("type").equalsIgnoreCase("password")  ) {
										objInput.clear();
										objInput.sendKeys(strValue);
										objInput.sendKeys(Keys.TAB);
										Thread.sleep(1000);
										WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl
												+ " with the VALUE - " + strValue);
										blnStatus = true;
										break;
									}	
								}
							}
						}
						else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::label[contains(@class,'control') and normalize-space(text())='"+strControl+"']/following-sibling::password/input[count(parent::*[not(contains(@style,'display: none'))])>0]"))) { 
							List<WebElement> listInputs = objForm.findElements(By.xpath("descendant::label[contains(@class,'control') and normalize-space(text())='"+strControl+"']/following-sibling::password/input[count(parent::*[not(contains(@style,'display: none'))])>0]"));
							for(WebElement objInput:listInputs) {
								if(objInput.isDisplayed()) {
									if (objInput.getAttribute("type").equalsIgnoreCase("text") ||objInput.getAttribute("type").equalsIgnoreCase("password")  ) {
										objInput.clear();
										objInput.sendKeys(strValue);
										objInput.sendKeys(Keys.TAB);
										WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl
												+ " with the VALUE - " + strValue);
										blnStatus = true;
										break;
									}	
								}
							}
							//TO HANDLE DROPDOWN
						}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::*[contains(@class,'control-label') and (text()='"+strControl+"')]/following-sibling::*[name()='dropdown' or name()='statusdropdown' or name()='selectdropdown'  or name()='pageddropdown']/div[@class='ispdropdown']"))) {
							//NEW DROPDOWN CHANGES HANDLER
							WebElement objInput = null;
							List<WebElement> listInputs = objForm.findElements(By.xpath("descendant::*[contains(@class,'control-label') and (text()='"+strControl+"') and count(following-sibling::*[name()='dropdown' or name()='statusdropdown' or name()='selectdropdown' or name()='pageddropdown']) > 0]"));
							for(WebElement objInputs:listInputs) {
								if(objInputs.isDisplayed()) {
									objInput = objInputs;													
								}
							}
							//										WebElement objInput = objForm.findElement(By.xpath("descendant::*[contains(@class,'control-label') and (contains(text(),\""+strControl+"\"))]/following-sibling::*[name()='dropdown' or name()='statusdropdown']"));
							objInput = objInput.findElement(By.xpath("following-sibling::*[name()='dropdown' or name()='statusdropdown' or name()='selectdropdown' or name()='pageddropdown' or name()='lazydropdown']/div[@class='ispdropdown']"));
							WebElement objDropDownArrow = objInput.findElement(By.xpath("descendant::span[contains(@class,'dropArrow')]"));
							objDropDownArrow.click();
							if(WebDriverUtility.IsWebElementExists(driver, (By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]")))){
								if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']"))) {
									driver.findElement(By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']")).click();
									WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " with the VALUE - " + strValue);
									return true;
								}else {
									WebDriverUtility.writeLogs("FAILED: ITEM - '"+strValue+"' IS NOT DISPLAYED IN THE LIST");
									return false;
								}
							}else {
								WebDriverUtility.writeLogs("FAILED: DROPDOWN MENU IS NOT DISPLAYED");
								return false;
							}
						}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::*[contains(@class,'control-label') and (text()='"+strControl+"')]/following-sibling::*[name()='dropdown' or name()='statusdropdown' or name()='selectdropdown'  or name()='pageddropdown' or name()='lazydropdown']/div[@class='ispdropdown']"))) {
							//NEW DROPDOWN CHANGES HANDLER
							WebElement objInput = null;
							List<WebElement> listInputs = objForm.findElements(By.xpath("descendant::*[contains(@class,'control-label') and (contains(text(),'"+strControl+"')) and count(following-sibling::*[name()='dropdown' or name()='statusdropdown' or name()='selectdropdown' or name()='pageddropdown' or name()='lazydropdown']) > 0]"));
							for(WebElement objInputs:listInputs) {
								if(objInputs.isDisplayed()) {
									objInput = objInputs;													
								}
							}
							//								WebElement objInput = objForm.findElement(By.xpath("descendant::*[contains(@class,'control-label') and (contains(text(),\""+strControl+"\"))]/following-sibling::*[name()='dropdown' or name()='statusdropdown']"));
							objInput = objInput.findElement(By.xpath("following-sibling::*[name()='dropdown' or name()='statusdropdown' or name()='selectdropdown' or name()='pageddropdown' or name()='lazydropdown']/div[@class='ispdropdown']"));
							WebElement objDropDownArrow = objInput.findElement(By.xpath("descendant::span[contains(@class,'dropArrow')]"));
							objDropDownArrow.click();
							if(WebDriverUtility.IsWebElementExists(driver, (By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]")))){
								if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']"))) {
									driver.findElement(By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']")).click();
									WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " with the VALUE - " + strValue);
									return true;
								}else {
									WebDriverUtility.writeLogs("FAILED: ITEM - '"+strValue+"' IS NOT DISPLAYED IN THE LIST");
									return false;
								}
							}else {
								WebDriverUtility.writeLogs("FAILED: DROPDOWN MENU IS NOT DISPLAYED");
								return false;
							}
						}
						else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::*[contains(@class,'control-label') and (contains(text(),\""+strControl+"\"))]/following-sibling::div/descendant::*[name()='dropdown' or name()='statusdropdown' or name()='selectdropdown' or name()='pageddropdown' or name()='lazydropdown']/div[@class='ispdropdown']"))) {
							//NEW DROPDOWN CHANGES HANDLER
							WebElement objInput = null;
							List<WebElement> listInputs = objForm.findElements(By.xpath("descendant::*[contains(@class,'control-label') and (contains(text(),\""+strControl+"\")) and count(following-sibling::div/descendant::*[name()='dropdown' or name()='statusdropdown' or name()='selectdropdown' or name()='pageddropdown' or name()='lazydropdown']) > 0]"));
							for(WebElement objInputs:listInputs) {
								if(objInputs.isDisplayed()) {
									objInput = objInputs;													
								}
							}
							//								WebElement objInput = objForm.findElement(By.xpath("descendant::*[contains(@class,'control-label') and (contains(text(),\""+strControl+"\"))]/following-sibling::*[name()='dropdown' or name()='statusdropdown']"));
							objInput = objInput.findElement(By.xpath("following-sibling::div/descendant::*[name()='dropdown' or name()='statusdropdown' or name()='selectdropdown' or name()='pageddropdown']/div[@class='ispdropdown']"));
							WebElement objDropDownArrow = objInput.findElement(By.xpath("descendant::span[contains(@class,'dropArrow')]"));
							objDropDownArrow.click();
							if(WebDriverUtility.IsWebElementExists(driver, (By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]")))){
								if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']"))) {
									driver.findElement(By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']")).click();
									WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " with the VALUE - " + strValue);
									return true;
								}else {
									WebDriverUtility.writeLogs("FAILED: ITEM - '"+strValue+"' IS NOT DISPLAYED IN THE LIST");
									return false;
								}
							}else {
								WebDriverUtility.writeLogs("FAILED: DROPDOWN MENU IS NOT DISPLAYED");
								return false;
							}
						}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::*[contains(@class,'control-label') and (contains(text(),\""+strControl+"\"))]/following-sibling::div/descendant::dropdown/div[contains(@class,'multiselect')]"))) {
							//							}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::*[contains(@class,'control-label') and (contains(text(),\""+strControl+"\"))]/following-sibling::div/descendant::*[name()='dropdown' or name()='statusdropdown']/div[contains(@class,'multiselect')]"))) {
							//NEW DROPDOWN CHANGE HANDLER
							WebElement objInput = objForm.findElement(By.xpath("//*[contains(@class,'control-label') and (contains(text(),'"+strControl+"'))]/following-sibling::div/descendant::dropdown"));
							//								WebElement objInput = objForm.findElement(By.xpath("//*[contains(@class,'control-label') and (contains(text(),'"+strControl+"'))]/following-sibling::div/descendant::*[name()='dropdown' or name()='statusdropdown']"));
							WebElement objDropDownArrow = objInput.findElement(By.xpath("descendant::span[contains(@class,'dropArrow')]"));
							try {
								WebDriverWait objWait = new WebDriverWait(driver, Duration.ofSeconds(15));
								objWait.until(ExpectedConditions.elementToBeClickable(objDropDownArrow));
							}catch(Exception e) {
							}
							objDropDownArrow.click();
							try {
								WebDriverWait objWait = new WebDriverWait(driver, Duration.ofSeconds(15));
								objWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]")));
								objWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]")));
							}catch(Exception e) {
							}
							String[] listValues = strValue.split(";");
							for(String val:listValues) {
								strValue = val;
								if(WebDriverUtility.IsWebElementExists(driver, (By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]")))){
									if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']")) || WebDriverUtility.IsWebElementExists(driver, By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']")) ) {
										if(driver.findElement(By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']/preceding-sibling::input")).isSelected()) {
											WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " with the VALUE - " + strValue);
											blnStatus = true;
										}else {
											driver.findElement(By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']")).click();
											WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " with the VALUE - " + strValue);
											blnStatus = true;
										}
									}else {
										WebDriverUtility.writeLogs("FAILED: ITEM - '"+strValue+"' IS NOT DISPLAYED IN THE LIST");
										blnStatus = false;
									}
								}else {
									WebDriverUtility.writeLogs("FAILED: DROPDOWN MENU IS NOT DISPLAYED");
									blnStatus = false;
								}
							}
							objDropDownArrow = objInput.findElement(By.xpath("descendant::span[contains(@class,'dropArrow')]"));
							objDropDownArrow.click();
							//TO Handle the Confirm Button if available
							if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//*[text()='Confirm' and not(@disabled='true') and contains(@data-bind,'Confirm')]"))) {
								driver.findElement(By.xpath("//*[text()='Confirm' and not(@disabled='true') and contains(@data-bind,'Confirm')]")).click();
							}else {
								JavascriptExecutor objJS = (JavascriptExecutor) driver;
								objJS.executeScript("arguments[0].click()", objDropDownArrow);
								//									objDropDownArrow.click();
							}

							if(blnStatus) WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl
									+ " with the VALUE - " + strValue);
						}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::*[contains(@class,'control-label') and (contains(text(),\""+strControl+"\"))]/following-sibling::*[name()='dropdown' or name()='statusdropdown']/div[@class='ispdropdown multiselect']"))) {
							//NEW DROPDOWN CHANGE HANDLER
							//					WebElement objInput = WebDriverUtility.GetWebElement(driver, By.xpath("descendant::*[contains(@class,'control-label') and (contains(text(),\""+strControl+"\"))]/following-sibling::*[name()='dropdown' or name()='statusdropdown']"));
							////					WebElement objInput = objForm.findElement(By.xpath("descendant::*[contains(@class,'control-label') and (contains(text(),\""+strControl+"\"))]/following-sibling::*[name()='dropdown' or name()='statusdropdown']"));
							////					WebElement objDropDownArrow = objInput.findElement(By.xpath("descendant::span[contains(@class,'dropArrow')]"));
							//					WebElement objDropDownArrow = WebDriverUtility.GetWebElement(driver, objInput,By.xpath("descendant::span[contains(@class,'dropArrow')]"));
							//					try {
							//						WebDriverWait objWait = new WebDriverWait(driver, Duration.ofSeconds(15));
							//						objWait.until(ExpectedConditions.elementToBeClickable(objDropDownArrow));
							//						
							//					}catch(Exception e) {
							//					}
							//					objDropDownArrow.click();
							Thread.sleep(500);
							try {
								WebDriverWait objWait = new WebDriverWait(driver, Duration.ofSeconds(15));
								objWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]")));
								objWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]")));
							}catch(Exception e) {
							}
							String[] listValues = strValue.split(";");
							for(String val:listValues) {
								strValue = val;
								if(WebDriverUtility.IsWebElementExists(driver, (By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]")))){
									if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']"))) {
										if(driver.findElement(By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']/preceding-sibling::input")).isSelected()) {
											WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " with the VALUE - " + strValue);
											blnStatus = true;
										}else {
											driver.findElement(By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']")).click();
											//									driver.findElement(By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']")).click();
											WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " with the VALUE - " + strValue);
											blnStatus = true;
										}
									}else {
										WebDriverUtility.writeLogs("FAILED: ITEM - '"+strValue+"' IS NOT DISPLAYED IN THE LIST");
										blnStatus = false;
									}
								}else {
									WebDriverUtility.writeLogs("FAILED: DROPDOWN MENU IS NOT DISPLAYED");
									blnStatus = false;
								}
							}
							//TO Handle the Confirm Button if available
							if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//*[text()='Confirm' and not(@disabled='true') and contains(@data-bind,'Confirm')]"))) {
								driver.findElement(By.xpath("//*[text()='Confirm' and not(@disabled='true') and contains(@data-bind,'Confirm')]")).click();
							}else {
								//						JavascriptExecutor objJS = (JavascriptExecutor) driver;
								//						objJS.executeScript("arguments[0].click()", objDropDownArrow);
								//						objDropDownArrow.click();
							}
							if(blnStatus) WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl
									+ " with the VALUE - " + strValue);
						}
						else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::*[contains(@class,'control-label') and (count(descendant::*[text()='"+strControl+"'])>0)]/following-sibling::div/descendant::dropdown/div[contains(@class,'multiselect')]"))) {
							//NEW DROPDOWN CHANGE HANDLER WITHOUT LABEL //PAGE ADD SITE OPERATOR - 
							WebElement objInput = objForm.findElement(By.xpath("descendant::*[contains(@class,'control-label') and (count(descendant::*[text()='"+strControl+"'])>0)]/following-sibling::div/descendant::dropdown"));
							WebElement objDropDownArrow = objInput.findElement(By.xpath("descendant::span[contains(@class,'dropArrow')]"));
							try {
								WebDriverWait objWait = new WebDriverWait(driver, Duration.ofSeconds(15));
								objWait.until(ExpectedConditions.elementToBeClickable(objDropDownArrow));
							}catch(Exception e) {
							}
							objDropDownArrow.click();
							Thread.sleep(500);
							try {
								WebDriverWait objWait = new WebDriverWait(driver, Duration.ofSeconds(15));
								objWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]")));
								objWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]")));
							}catch(Exception e) {
							}
							String[] listValues = strValue.split(";");
							for(String val:listValues) {
								strValue = val;
								if(WebDriverUtility.IsWebElementExists(driver, (By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]")))){
									if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']"))||WebDriverUtility.IsWebElementExists(driver, By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']"))) {
										if(driver.findElement(By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']/preceding-sibling::input")).isSelected()) {
											WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " with the VALUE - " + strValue);
											blnStatus = true;
										}else {
											driver.findElement(By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']")).click();
											WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " with the VALUE - " + strValue);
											blnStatus = true;
										}
									}else {
										WebDriverUtility.writeLogs("FAILED: ITEM - '"+strValue+"' IS NOT DISPLAYED IN THE LIST");
										blnStatus = false;
									}
								}else {
									WebDriverUtility.writeLogs("FAILED: DROPDOWN MENU IS NOT DISPLAYED");
									blnStatus = false;
								}
							}
							objDropDownArrow = objInput.findElement(By.xpath("descendant::span[contains(@class,'dropArrow')]"));
							objDropDownArrow.click();
							//TO Handle the Confirm Button if available
							if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//*[text()='Confirm' and not(@disabled='true') and contains(@data-bind,'Confirm')]"))) {
								driver.findElement(By.xpath("//*[text()='Confirm' and not(@disabled='true') and contains(@data-bind,'Confirm')]")).click();
							}else {
								objDropDownArrow.click();
							}
							if(blnStatus) WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl
									+ " with the VALUE - " + strValue);
						}
						else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::label[contains(text(),'"+strControl+"')]/following-sibling::div/descendant::span[contains(@class,'dropArrow')]"))) {
							//To HANDLE OLD DROPDOWNS EMBEDDED WITHIN THE FORMS WITH RADIO BUTTONS
							WebElement objDropDown = driver.findElement(By.xpath("descendant::label[contains(text(),'"+strControl+"')]/following-sibling::div/descendant::span[contains(@class,'dropArrow')]/following-sibling::div"));
							WebElement objDropArrow = objDropDown.findElement(By.xpath("preceding-sibling::span[contains(@class,'dropArrow')]"));
							objDropArrow.click();
							try {
								Thread.sleep(500);
							} catch (Exception e) {
							}
							//To Handle the Options with Radio Buttons
							if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::input[@type='radio']"))) {
								WebElement objCollapse = objDropDown.findElement(By.xpath("descendant::span[contains(@data-bind,'Name')]/ancestor::div[contains(@data-bind,'parentContext')]"));
								if(objCollapse.getAttribute("data-bind").contains("collapse in")) {

								}else if(objCollapse.getAttribute("data-bind").contains("CollapseIn")){
									objCollapse.findElement(By.xpath("parent::*/preceding-sibling::*/span[contains(@data-bind,'parent')]")).click();
								}
								List<WebElement> listOptions = objDropDown.findElements(By.xpath("descendant::span[contains(@data-bind,'Name')]"));
								for(WebElement objOption:listOptions) {
									if(objOption.getText().contains(strValue)) {
										objOption.findElement(By.xpath("preceding-sibling::input")).click();
										WebDriverUtility.writeLogs("SELECTED THE OPTION - "+strValue+" FROM THE FIELD - "+strControl);
										blnStatus=true;
										break;
									}
								}
								objDropArrow.click();
								//To Handle the Options with Checkbox
							}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::input[@type='checkbox']"))) {
								List<WebElement> listOptions = objDropDown.findElements(By.xpath("descendant::span[contains(@data-bind,'Name')]"));
								String[] arrValues = strValue.split(";");
								for (String Val:arrValues) {
									for(WebElement objOption:listOptions) {
										if(objOption.getText().contains(Val)) {
											if(!objOption.findElement(By.xpath("preceding-sibling::input")).isSelected()) {
												objOption.findElement(By.xpath("preceding-sibling::input")).click();
												WebDriverUtility.writeLogs("SELECTED THE OPTION - "+Val+" FROM THE FIELD - "+strControl);
											}
											blnStatus=true;
											break;
										}
									}	
								}
								//TO Handle the Confirm Button if available
								if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//div[contains(@class,'PopupList') and contains(@class,'show')]/descendant::div[text()='Confirm' and not(@disabled='true')]"))) {
									driver.findElement(By.xpath("//div[contains(@class,'PopupList') and contains(@class,'show')]/descendant::div[text()='Confirm' and not(@disabled='true')]")).click();
								}
							}
						}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::label[contains(text(),'"+strControl+"')]/following-sibling::*[1]/descendant::span[contains(@class,'dropArrow')]"))) {
							//To HANDLE OLD DROPDOWNS EMBEDDED WITHIN THE FORMS WITH RADIO BUTTONS
							WebElement objDropDown = objForm.findElement(By.xpath("descendant::label[contains(text(),'"+strControl+"')]/following-sibling::*[1]/descendant::span[contains(@class,'dropArrow')]"));
							objDropDown.click();
							//List<WebElement> listTree = driver.findElements(By.xpath("//*[contains(@class,'hierarchy-dropdown-popup')]/descendant::li"));
							List<WebElement> listTree = driver.findElements(By.xpath("//*[contains(@class,'ispdropdown-popup')]/descendant::li"));
							////*[contains(@class,'ispdropdown-popup')]//descendant::li
							for(WebElement objTree:listTree) {
								if(!(objTree.findElement(By.xpath("descendant::span[contains(@class,'droparrow')]")).getAttribute("class").contains("pen"))) {
									objTree.findElement(By.xpath("descendant::span[contains(@class,'droparrow')]")).click();
									WebDriverUtility.funcUtil_HandleSync_Wait(driver);	
								}
								if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::span[contains(@class,'droparrow')]/following-sibling::input[@type='checkbox']"))) {
									if(objTree.findElement(By.xpath("descendant::span[contains(@class,'droparrow')]/following-sibling::input[@type='checkbox']")).isSelected()) {
										objTree.findElement(By.xpath("descendant::span[contains(@class,'droparrow')]/following-sibling::input[@type='checkbox']")).click();
										Thread.sleep(750);
									}
								}
								List<WebElement> listOptions = objTree.findElements(By.xpath("descendant::input[contains(@name,'dropdown-radio') or contains(@class,'checkbox')]/following-sibling::span"));  
								for(WebElement objOption:listOptions) {
									if(objOption.getText().contains(strValue)||objOption.getText().equalsIgnoreCase(strValue)) {
										objOption.findElement(By.xpath("preceding-sibling::input")).click();
										Thread.sleep(500);
										WebDriverUtility.writeLogs("SET SECTION - '"+strSection+"' FIELD - '"+strControl+"' VALUE - '"+strValue+"'");
										blnStatus = true;
										objDropDown.click();
										if (blnStatus) return true;

									}
								}
							}
						}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::*[contains(text(),'"+strControl+"') ]/parent::*/following-sibling::*/descendant::div[@class='slider round']"))) {
							WebElement objSlider = objForm.findElement(By.xpath("descendant::*[contains(text(),'"+strControl+"') ]/parent::*/following-sibling::*/descendant::div[@class='slider round']"));
							if(strValue.equalsIgnoreCase("ON")) {
								if (objSlider.getCssValue("background-color").toString().equals("rgba(126, 179, 56, 1)")) {
									blnStatus = true;
									WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + "IS ALREADY SELECTED WITH the VALUE - " + strValue);
								} else if (objSlider.getCssValue("background-color").toString().equals("rgba(204, 204, 204, 1)")) {
									blnStatus = true;
									objSlider.click();
									WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " WITH the VALUE - " + strValue);
								}
							}else {
								if (objSlider.getCssValue("background-color").toString().equals("rgba(126, 179, 56, 1)")) {
									blnStatus = true;
									objSlider.click();
									WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " WITH the VALUE - " + strValue);
								} else if (objSlider.getCssValue("background-color").toString().equals("rgba(204, 204, 204, 1)")) {
									blnStatus = true;
									WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + "IS ALREADY SELECTED WITH the VALUE - " + strValue);
								}	
							}
						}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::*[contains(text(),'"+strControl+"') ]/parent::*/descendant::div[@class='slider round']"))) {
							WebElement objSlider = objForm.findElement(By.xpath("descendant::*[contains(text(),'"+strControl+"') ]/parent::*/descendant::div[@class='slider round']"));
							if(strValue.equalsIgnoreCase("ON")) {
								if (objSlider.getCssValue("background-color").toString().equals("rgba(126, 179, 56, 1)")) {
									blnStatus = true;
									WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + "IS ALREADY SELECTED WITH the VALUE - " + strValue);
								} else if (objSlider.getCssValue("background-color").toString().equals("rgba(204, 204, 204, 1)")) {
									blnStatus = true;
									objSlider.click();
									WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " WITH the VALUE - " + strValue);
								}
							}else {
								if (objSlider.getCssValue("background-color").toString().equals("rgba(126, 179, 56, 1)")) {
									blnStatus = true;
									objSlider.click();
									WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " WITH the VALUE - " + strValue);
								} else if (objSlider.getCssValue("background-color").toString().equals("rgba(204, 204, 204, 1)")) {
									blnStatus = true;
									WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + "IS ALREADY SELECTED WITH the VALUE - " + strValue);
								}	
							}
						}
						else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::*[text()='"+strValue+"']/preceding-sibling::input[@type='radio']"))) {
							WebElement objInput = objForm.findElement(By.xpath("descendant::*[text()='"+strValue+"']/preceding-sibling::input[@type='radio']"));
							if(objInput.isSelected()) {
								blnStatus = true;
							}else {
								objInput.click();
								WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " WITH the VALUE - " + strValue);
								blnStatus  = true;
							}
						}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant:://*[text()='"+strControl+"']/input[@type='checkbox']"))) {
							WebElement objCheckBox = objForm.findElement(By.xpath("descendant::*[text()='"+strControl+"']/input[@type='checkbox']"));
							if(strValue.equalsIgnoreCase("ON")) {
								if (objCheckBox.isSelected()) {
									blnStatus = true;
									WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + "IS ALREADY SELECTED WITH the VALUE - " + strValue);
								} else {
									blnStatus = true;
									objCheckBox.click();
									WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " WITH the VALUE - " + strValue);
								}
							}else{
								if (!objCheckBox.isSelected()) {
									blnStatus = true;
									WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + "IS ALREADY SELECTED WITH the VALUE - " + strValue);
								} else {
									blnStatus = true;
									objCheckBox.click();
									WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " WITH the VALUE - " + strValue);
								}
							}
						}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::*[text()='"+strControl+"']/preceding-sibling::input[@type='checkbox']"))) {
							WebElement objCheckBox = objForm.findElement(By.xpath("descendant::*[text()='"+strControl+"']/preceding-sibling::input[@type='checkbox']"));
							if(strValue.equalsIgnoreCase("ON")) {
								if (objCheckBox.isSelected()) {
									blnStatus = true;
									WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + "IS ALREADY SELECTED WITH the VALUE - " + strValue);
								} else {
									blnStatus = true;
									objCheckBox.click();
									WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " WITH the VALUE - " + strValue);
								}
							}else{
								if (!objCheckBox.isSelected()) {
									blnStatus = true;
									WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + "IS ALREADY SELECTED WITH the VALUE - " + strValue);
								} else {
									blnStatus = true;
									objCheckBox.click();
									WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " WITH the VALUE - " + strValue);
								}
							}
						}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::label[contains(@class,'control') and contains(text(),'"+strControl+"')]/following-sibling::div[contains(@class,'isppopup')]"))) {
							WebElement objDropDown = objForm.findElement(By.xpath("descendant::label[contains(@class,'control') and contains(text(),'"+strControl+"')]/following-sibling::div[contains(@class,'isppopup')]"));
							String[] arrParentNodes = strValue.split(":");
							String[] arrChildNodes = strValue.split(";");
							//SELECT THE PARENT NODES
							for(int idx=1;idx< arrParentNodes.length-1;idx++) {
								if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::*[text()='"+arrParentNodes[idx]+"']"))) {
									WebElement objDropArrow = objDropDown.findElement(By.xpath("descendant::*[text()='"+arrParentNodes[idx]+"']/preceding-sibling::span[contains(@class,'accordian_droparrow')]"));
									if(!objDropArrow.getAttribute("class").contains("Open")) {
										objDropArrow.click();
										WebDriverUtility.writeLogs("PASSED: SELECTED THE NODE - '"+arrParentNodes[idx]+"'");
									}
								}
							}
							//SELECT THE CHILD NODES
							for(int idx=1;idx< arrChildNodes.length;idx++) {
								if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::*[text()='"+arrChildNodes[idx]+"']"))) {
									WebElement objCheckBox = objDropDown.findElement(By.xpath("descendant::*[text()='"+arrChildNodes[idx]+"']/preceding-sibling::input[@type='checkbox']"));
									if(!objCheckBox.isSelected()) {
										objCheckBox.click();
										blnStatus = true;
										WebDriverUtility.writeLogs("PASSED: SELECTED THE NODE - '"+arrChildNodes[idx]+"'");
										WebDriverUtility.writeLogs("PASSED: SET THE SECTION - '"+strSection+"' FIELD - '"+strControl+"' WITH THE VALUE - '"+strValue+"'");
									}
								}
							}	
						}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::label[contains(@class,'control') and contains(text(),'"+strControl+"')]/following-sibling::div[contains(@class,'dateTimePicker')]"))) {
							WebElement objCalendarIcon = objForm.findElement(By.xpath("descendant::label[contains(@class,'control') and contains(text(),'"+strControl+"')]/following-sibling::div[contains(@class,'dateTimePicker')]/descendant::span[contains(@class,'calender')]"));
							objCalendarIcon.click();
							if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//div[contains(@class,'datetimepicker') and contains(@style,'display: block')]"))) {
								WebElement objDateTimePicker = driver.findElement(By.xpath("//div[contains(@class,'datetimepicker') and contains(@style,'display: block')]"));
								objDateTimePicker.findElement(By.xpath("descendant::*[@class='datepicker-days']//*[@class='switch']")).click();
								objDateTimePicker.findElement(By.xpath("descendant::*[@class='datepicker-months']//*[@class='switch']")).click();
								SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy");
								String Expdate = null;
								if(strValue.equalsIgnoreCase("TODAY")||strValue.contains("Today")||strValue.contains("TODAY"))
								{
									Date date=new Date();
									Expdate=format.format(date);
								}else if (strValue.contains("TODAY+")) {
									Date dt = new Date();
									Calendar c = Calendar.getInstance(); 
									c.setTime(dt); 
									c.add(Calendar.DATE, Integer.valueOf(strValue.replace("TODAY+", "")));
									dt = c.getTime();
									Expdate=format.format(dt);
								}
								else if(strValue.equalsIgnoreCase("TOMORROW")||(strValue.contains("Tomorrow"))||strValue.contains("TOMORROW"))
								{
									long date=new Date().getTime()+1*24*60*60*1000;
									Expdate=format.format(date);
								}
								else if(strValue.equalsIgnoreCase("DAYAFTERTOMORROW")||(strValue.contains("DayAfterTomorrow"))||strValue.contains("DAYAFTERTOMORROW"))
								{
									long date=new Date().getTime()+2*24*60*60*1000;
									Expdate=format.format(date);
								}else if(strValue.equalsIgnoreCase("YESTERDAY")||(strValue.contains("Yesterday"))||strValue.contains("YESTERDAY")) {
									long date=new Date().getTime()-1*24*60*60*1000;
									Expdate=format.format(date);
								}
								Date objDate = new SimpleDateFormat("MM/dd/yyyy").parse(Expdate);
								SimpleDateFormat objMonthFormat = new SimpleDateFormat("MMM");
								System.out.println(objMonthFormat.format(objDate));
								SimpleDateFormat objYearFormat = new SimpleDateFormat("yyyy");
								System.out.println(objYearFormat.format(objDate));
								SimpleDateFormat objDayFormat = new SimpleDateFormat("d");
								System.out.println(objDayFormat.format(objDate));
								try {
									Thread.sleep(500);
									objDateTimePicker.findElement(By.xpath("descendant::span[contains(@class,'year') and text()='"+objYearFormat.format(objDate)+"']")).click();
									Thread.sleep(500);
									objDateTimePicker.findElement(By.xpath("descendant::span[contains(@class,'month') and text()='"+objMonthFormat.format(objDate)+"']")).click();
									Thread.sleep(500);
									objDateTimePicker.findElement(By.xpath("descendant::td[contains(@class,'day') and not(contains(@class,'disabled')) and not(contains(@class,'old')) and text()='"+objDayFormat.format(objDate)+"']")).click();
									Thread.sleep(500);
									if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::li[contains(@class,'picker-switch')]/descendant::a"))) {
										objDateTimePicker.findElement(By.xpath("descendant::li[contains(@class,'picker-switch')]/descendant::a")).click();	 
									}
									Thread.sleep(500);
									//RESET THE TIME to 00:00:00
									if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::a[@data-action='decrementHours']"))) {
										while(!objDateTimePicker.findElement(By.xpath("descendant::span[@data-action='showHours']")).getText().contains("00")) {
											objDateTimePicker.findElement(By.xpath("descendant::a[@data-action='decrementHours']")).click();
										}
									}
									if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::a[@data-action='decrementMinutes']"))) {
										while(!objDateTimePicker.findElement(By.xpath("descendant::span[@data-action='showMinutes']")).getText().contains("00")) {
											objDateTimePicker.findElement(By.xpath("descendant::a[@data-action='decrementMinutes']")).click();
										}
									}
									if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::a[@data-action='decrementSeconds']"))) {
										while(!objDateTimePicker.findElement(By.xpath("descendant::span[@data-action='showSeconds']")).getText().contains("00")) {
											objDateTimePicker.findElement(By.xpath("descendant::a[@data-action='decrementSeconds']")).click();
										}
									}
									//TO ENTER THE TIME IN THE DATETIMEPICKER
									String strTime = ""; 
									if(strValue.split(" ").length>1) {
										strTime = strValue.split(" ")[1];
									}
									if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::a[@data-action='incrementHours']"))) {
										while(!objDateTimePicker.findElement(By.xpath("descendant::span[@data-action='showHours']")).getText().contains(strTime.split(":")[0])) {
											objDateTimePicker.findElement(By.xpath("descendant::a[@data-action='incrementHours']")).click();
										}
									}
									if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::a[@data-action='incrementMinutes']"))) {
										while(!objDateTimePicker.findElement(By.xpath("descendant::span[@data-action='showMinutes']")).getText().contains(strTime.split(":")[1])) {
											objDateTimePicker.findElement(By.xpath("descendant::a[@data-action='incrementMinutes']")).click();
										}
									}
									if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::a[@data-action='incrementSeconds']"))) {
										while(!objDateTimePicker.findElement(By.xpath("descendant::span[@data-action='showSeconds']")).getText().contains(strTime.split(":")[2])) {
											objDateTimePicker.findElement(By.xpath("descendant::a[@data-action='incrementSeconds']")).click();
										}
									}
									try{objCalendarIcon.click();}catch(Exception e) {}
									WebDriverUtility.writeLogs("PASSED: SET THE SECTION - " + strSection + " FIELD - " + strControl + " with the VALUE - " + strValue);
									blnStatus = true;
								}catch(Exception e) {
									WebDriverUtility.writeLogs("FAILED: SET THE SECTION - " + strSection + " FIELD - " + strControl + " with the VALUE - " + strValue);
									return false;
								}
							}else {
								WebDriverUtility.writeLogs("FAILED: CALENAR DATE TIME PICKER POPUP IS NOT DISPLAYED");
								return false;
							}
						}
						//Condition when Control Label doesnt exist
					}else if(WebDriverUtility.IsWebElementExists(driver,  By.xpath("descendant::*[contains(@class,'control-label') and (count(descendant::*[text()='"+strControl+"'])>0)]/following-sibling::div/descendant::dropdown/div[contains(@class,'multiselect')]"))) {
						//NEW DROPDOWN CHANGE HANDLER WITHOUT LABEL //PAGE ADD SITE OPERATOR - 
						WebElement objInput = objForm.findElement(By.xpath("descendant::*[contains(@class,'control-label') and (count(descendant::*[text()='"+strControl+"'])>0)]/following-sibling::div/descendant::dropdown"));
						WebElement objDropDownArrow = objInput.findElement(By.xpath("descendant::span[contains(@class,'dropArrow')]"));
						objDropDownArrow.click();
						String[] listValues = strValue.split(";");
						for(String val:listValues) {
							strValue = val;
							if(WebDriverUtility.IsWebElementExists(driver, (By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]")))){
								if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']"))||WebDriverUtility.IsWebElementExists(driver, By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']"))) {
									driver.findElement(By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']")).click();
									WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " with the VALUE - " + strValue);
									blnStatus = true;
								}else {
									WebDriverUtility.writeLogs("FAILED: ITEM - '"+strValue+"' IS NOT DISPLAYED IN THE LIST");
									blnStatus = false;
								}
							}else {
								WebDriverUtility.writeLogs("FAILED: DROPDOWN MENU IS NOT DISPLAYED");
								blnStatus = false;
							}
						}
						objDropDownArrow = objInput.findElement(By.xpath("descendant::span[contains(@class,'dropArrow')]"));
						objDropDownArrow.click();
						//TO Handle the Confirm Button if available
						if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//*[text()='Confirm' and not(@disabled='true') and contains(@data-bind,'Confirm')]"))) {
							driver.findElement(By.xpath("//*[text()='Confirm' and not(@disabled='true') and contains(@data-bind,'Confirm')]")).click();
						}else {
							objDropDownArrow.click();
						}
						if(blnStatus) WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl
								+ " with the VALUE - " + strValue);
					}else if(WebDriverUtility.IsWebElementExists(driver,  By.xpath("descendant::span[contains(text(),'"+strControl+ "')]/preceding-sibling::*"))) {
						WebElement objInput = objForm.findElement(By.xpath("//span[contains(text(),'"+strControl+ "')]/preceding-sibling::*"));
						if(strValue.equalsIgnoreCase("ON")) {
							if(!objInput.isSelected()) objInput.click();
							WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl
									+ " with the VALUE - " + strValue);
							blnStatus = true;
						}else {
							if(objInput.isSelected()) objInput.click();
							WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl
									+ " with the VALUE - " + strValue);
							blnStatus = true;
						}
						//CHECKBOX CONTROL
					}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::*[text()='"+strControl+"']/input[@type='checkbox']"))) {
						WebElement objCheckBox = objForm.findElement(By.xpath("descendant::*[text()='"+strControl+"']/input[@type='checkbox']"));
						if(strValue.equalsIgnoreCase("ON")) {
							if (objCheckBox.isSelected()) {
								blnStatus = true;
								WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + "IS ALREADY SELECTED WITH the VALUE - " + strValue);
							} else {
								blnStatus = true;
								objCheckBox.click();
								WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " WITH the VALUE - " + strValue);
							}
						}else{
							if (!objCheckBox.isSelected()) {
								blnStatus = true;
								WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + "IS ALREADY SELECTED WITH the VALUE - " + strValue);
							} else {
								blnStatus = true;
								objCheckBox.click();
								WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " WITH the VALUE - " + strValue);
							}
						}
					}else if(WebDriverUtility.IsWebElementExists(driver,  By.xpath("descendant::*[contains(text(),'"+strControl+"')]/input[@type='checkbox']"))) {
						WebElement objCheckBox = objForm.findElement(By.xpath("descendant::*[contains(text(),'"+strControl+"')]/input[@type='checkbox']"));
						if(strValue.equalsIgnoreCase("ON")) {
							if (objCheckBox.isSelected()) {
								blnStatus = true;
								WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + "IS ALREADY SELECTED WITH the VALUE - " + strValue);
							} else {
								blnStatus = true;
								objCheckBox.click();
								WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " WITH the VALUE - " + strValue);
							}
						}else{
							if (!objCheckBox.isSelected()) {
								blnStatus = true;
								WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + "IS ALREADY SELECTED WITH the VALUE - " + strValue);
							} else {
								blnStatus = true;
								objCheckBox.click();
								WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " WITH the VALUE - " + strValue);
							}
						}
					}else if(WebDriverUtility.IsWebElementExists(driver,  By.xpath("descendant::text()[normalize-space()='"+strControl+"']/parent::*[@class='radio-inline']"))) {
						WebElement objRadio = objForm.findElement(By.xpath("descendant::text()[normalize-space()='"+strControl+"']/parent::*[@class='radio-inline']/input"));
						if(strValue.equalsIgnoreCase("ON")) {
							if (objRadio.isSelected()) {
								blnStatus = true;
								WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + "IS ALREADY SELECTED WITH the VALUE - " + strValue);
							} else {
								blnStatus = true;
								objRadio.click();
								WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " WITH the VALUE - " + strValue);
							}
						}else{
							if (!objRadio.isSelected()) {
								blnStatus = true;
								WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + "IS ALREADY SELECTED WITH the VALUE - " + strValue);
							} else {
								blnStatus = true;
								objRadio.click();
								WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " WITH the VALUE - " + strValue);
							}
						}
					}else {
						WebDriverUtility.writeLogs("WEBELEMENT NOT FOUND - SECTION - " + strSection + " FIELD - " + strControl);
					}
				}else {
					WebDriverUtility.writeLogs("SECTION - '"+strSection+"' IS NOT DISPLAYED");
				}
				//SECTION IS BLANK
			}else {
				//CALENDAR INPUT
				if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//label[contains(@class,'control') and contains(text(),'"+strControl+"')]/following-sibling::input[@data-format='MM/dd/yyyy']"))) {
					WebElement objCalendarIcon = driver.findElement(By.xpath("//label[contains(@class,'control') and contains(text(),'"+strControl+"')]/following-sibling::span[contains(@class,'date_icon')]"));
					objCalendarIcon.click();
					if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//div[contains(@class,'datetimepicker') and contains(@style,'display: block')]"))) {
						WebElement objDateTimePicker = driver.findElement(By.xpath("//div[contains(@class,'datetimepicker') and contains(@style,'display: block')]"));
						objDateTimePicker.findElement(By.xpath("descendant::*[@class='datepicker-days']//*[@class='switch']")).click();
						objDateTimePicker.findElement(By.xpath("descendant::*[@class='datepicker-months']//*[@class='switch']")).click();
						SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy");
						String Expdate = null;
						if(strValue.equalsIgnoreCase("TODAY")||strValue.contains("Today"))
						{
							Date date=new Date();
							Expdate=format.format(date);
						}else if (strValue.contains("TODAY+")) {
							Date dt = new Date();
							Calendar c = Calendar.getInstance(); 
							c.setTime(dt); 
							c.add(Calendar.DATE, Integer.valueOf(strValue.replace("TODAY+", "")));
							dt = c.getTime();
							Expdate=format.format(dt);
						}
						else if(strValue.equalsIgnoreCase("TOMORROW")||(strValue.contains("Tomorrow")))
						{
							long date=new Date().getTime()+1*24*60*60*1000;
							Expdate=format.format(date);
						}
						else if(strValue.equalsIgnoreCase("DAYAFTERTOMORROW")||(strValue.contains("DayAfterTomorrow")))
						{
							long date=new Date().getTime()+2*24*60*60*1000;
							Expdate=format.format(date);
						}
						Date objDate = new SimpleDateFormat("MM/dd/yyyy").parse(Expdate);
						SimpleDateFormat objMonthFormat = new SimpleDateFormat("MMM");
						System.out.println(objMonthFormat.format(objDate));
						SimpleDateFormat objYearFormat = new SimpleDateFormat("yyyy");
						System.out.println(objYearFormat.format(objDate));
						SimpleDateFormat objDayFormat = new SimpleDateFormat("d");
						System.out.println(objDayFormat.format(objDate));
						try {
							objDateTimePicker.findElement(By.xpath("descendant::span[contains(@class,'year') and text()='"+objYearFormat.format(objDate)+"']")).click();
							objDateTimePicker.findElement(By.xpath("descendant::span[contains(@class,'month') and text()='"+objMonthFormat.format(objDate)+"']")).click();
							objDateTimePicker.findElement(By.xpath("descendant::td[contains(@class,'day') and not(contains(@class,'disabled')) and text()='"+objDayFormat.format(objDate)+"']")).click();
							objCalendarIcon.click();
							WebDriverUtility.writeLogs("PASSED: SET THE SECTION - " + strSection + " FIELD - " + strControl + " with the VALUE - " + strValue);
							blnStatus = true;
						}catch(Exception e) {
							WebDriverUtility.writeLogs("FAILED: SET THE SECTION - " + strSection + " FIELD - " + strControl + " with the VALUE - " + strValue);
							return false;
						}
					}else {
						WebDriverUtility.writeLogs("FAILED: CALENAR DATE TIME PICKER POPUP IS NOT DISPLAYED");
						return false;
					}
				}else if (WebDriverUtility.IsWebElementExists(driver, By.xpath("//label[contains(@class,'control') and contains(text(),'"+strControl+"')]/parent::*/descendant::span[contains(@class,'calender')]"))) {
					WebElement objCalendarIcon = driver.findElement(By.xpath("//label[contains(@class,'control') and contains(text(),'"+strControl+"')]/parent::*/descendant::span[contains(@class,'calender')]"));
					objCalendarIcon.click();
					if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//div[contains(@class,'datetimepicker') and contains(@style,'display: block')]"))) {
						WebElement objDateTimePicker = driver.findElement(By.xpath("//div[contains(@class,'datetimepicker') and contains(@style,'display: block')]"));
						objDateTimePicker.findElement(By.xpath("descendant::*[@class='datepicker-days']//*[@class='switch']")).click();
						objDateTimePicker.findElement(By.xpath("descendant::*[@class='datepicker-months']//*[@class='switch']")).click();

						SimpleDateFormat format=new SimpleDateFormat("MM/dd/yyyy");

						String Expdate = null;
						if(strValue.equalsIgnoreCase("TODAY")||strValue.contains("Today")||strValue.contains("TODAY"))
						{
							Date date=new Date();
							Expdate=format.format(date);
						}else if (strValue.contains("TODAY+")) {
							Date dt = new Date();
							Calendar c = Calendar.getInstance(); 
							c.setTime(dt); 
							c.add(Calendar.DATE, Integer.valueOf(strValue.replace("TODAY+", "")));
							dt = c.getTime();
							Expdate=format.format(dt);
						}
						else if(strValue.equalsIgnoreCase("TOMORROW")||(strValue.contains("Tomorrow"))||strValue.contains("TOMORROW"))
						{
							long date=new Date().getTime()+1*24*60*60*1000;
							Expdate=format.format(date);
						}
						else if(strValue.equalsIgnoreCase("DAYAFTERTOMORROW")||(strValue.contains("DayAfterTomorrow"))||strValue.contains("DAYAFTERTOMORROW"))
						{
							long date=new Date().getTime()+2*24*60*60*1000;
							Expdate=format.format(date);
						}else if(strValue.equalsIgnoreCase("YESTERDAY")||(strValue.contains("Yesterday"))||strValue.contains("YESTERDAY")) {
							long date=new Date().getTime()-1*24*60*60*1000;
							Expdate=format.format(date);
						}
						Date objDate = new SimpleDateFormat("MM/dd/yyyy").parse(Expdate);
						SimpleDateFormat objMonthFormat = new SimpleDateFormat("MMM");
						System.out.println(objMonthFormat.format(objDate));
						SimpleDateFormat objYearFormat = new SimpleDateFormat("yyyy");
						System.out.println(objYearFormat.format(objDate));
						SimpleDateFormat objDayFormat = new SimpleDateFormat("d");
						System.out.println(objDayFormat.format(objDate));
						try {
							Thread.sleep(500);
							objDateTimePicker.findElement(By.xpath("descendant::span[contains(@class,'year') and text()='"+objYearFormat.format(objDate)+"']")).click();
							Thread.sleep(500);
							//								 objDateTimePicker.findElement(By.xpath("descendant::span[contains(@class,'month') and text()='"+objMonthFormat.format(objDate)+"']")).click();
							//								 Thread.sleep(500);
							//								 objDateTimePicker.findElement(By.xpath("descendant::td[contains(@class,'day') and not(contains(@class,'disabled')) and not(contains(@class,'old')) and text()='"+objDayFormat.format(objDate)+"']")).click();
							Thread.sleep(500);
							if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::li[contains(@class,'picker-switch')]/descendant::a"))) {
								objDateTimePicker.findElement(By.xpath("descendant::li[contains(@class,'picker-switch')]/descendant::a")).click();	 
							}
							Thread.sleep(500);
							//RESET THE TIME to 00:00:00
							if(WebDriverUtility.IsWebElementExists(driver,  By.xpath("descendant::a[@data-action='decrementHours']"))) {
								while(!objDateTimePicker.findElement(By.xpath("descendant::span[@data-action='showHours']")).getText().contains("00")) {
									objDateTimePicker.findElement(By.xpath("descendant::a[@data-action='decrementHours']")).click();
								}
							}
							if(WebDriverUtility.IsWebElementExists(driver,  By.xpath("descendant::a[@data-action='decrementMinutes']"))) {
								while(!objDateTimePicker.findElement(By.xpath("descendant::span[@data-action='showMinutes']")).getText().contains("00")) {
									objDateTimePicker.findElement(By.xpath("descendant::a[@data-action='decrementMinutes']")).click();
								}
							}
							if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::a[@data-action='decrementSeconds']"))) {
								while(!objDateTimePicker.findElement(By.xpath("descendant::span[@data-action='showSeconds']")).getText().contains("00")) {
									objDateTimePicker.findElement(By.xpath("descendant::a[@data-action='decrementSeconds']")).click();
								}
							}
							//TO ENTER THE TIME IN THE DATETIMEPICKER
							String strTime = ""; 
							if(strValue.split(" ").length>1) {
								strTime = strValue.split(" ")[1];
							}
							if(WebDriverUtility.IsWebElementExists(driver,  By.xpath("descendant::a[@data-action='incrementHours']"))) {
								while(!objDateTimePicker.findElement(By.xpath("descendant::span[@data-action='showHours']")).getText().contains(strTime.split(":")[0])) {
									objDateTimePicker.findElement(By.xpath("descendant::a[@data-action='incrementHours']")).click();
								}
							}
							if(WebDriverUtility.IsWebElementExists(driver,  By.xpath("descendant::a[@data-action='incrementMinutes']"))) {
								while(!objDateTimePicker.findElement(By.xpath("descendant::span[@data-action='showMinutes']")).getText().contains(strTime.split(":")[1])) {
									objDateTimePicker.findElement(By.xpath("descendant::a[@data-action='incrementMinutes']")).click();
								}
							}
							if(WebDriverUtility.IsWebElementExists(driver, By.xpath("descendant::a[@data-action='incrementSeconds']"))) {
								while(!objDateTimePicker.findElement(By.xpath("descendant::span[@data-action='showSeconds']")).getText().contains(strTime.split(":")[2])) {
									objDateTimePicker.findElement(By.xpath("descendant::a[@data-action='incrementSeconds']")).click();
								}
							}
							try{objCalendarIcon.click();}catch(Exception e) {}
							WebDriverUtility.writeLogs("PASSED: SET THE SECTION - " + strSection + " FIELD - " + strControl + " with the VALUE - " + strValue);
							blnStatus = true;
						}catch(Exception e) {
							WebDriverUtility.writeLogs("FAILED: SET THE SECTION - " + strSection + " FIELD - " + strControl + " with the VALUE - " + strValue);
							return false;
						}
					}else {
						WebDriverUtility.writeLogs("FAILED: CALENAR DATE TIME PICKER POPUP IS NOT DISPLAYED");
						return false;
					}
				} else if (WebDriverUtility.IsWebElementExists(driver, By.xpath("//label[contains(@class,'control') and text()='" + strControl + "']/following-sibling::input"))) {
					WebElement objInput = driver.findElement(
							By.xpath("//label[contains(@class,'control') and text()='" + strControl + "']/following-sibling::input"));
					if (objInput.getAttribute("type").equalsIgnoreCase("text")) {
						objInput.clear();
						objInput.sendKeys(strValue);
						WebDriverUtility.writeLogs("SET THE FIELD - " + strControl
								+ " with the VALUE - " + strValue);
						blnStatus = true;
					}
				}else if (WebDriverUtility.IsWebElementExists(driver, By.xpath("//span[text()='" + strControl + "']/parent::label/following-sibling::input"))) {
					WebElement objInput = driver.findElement(
							By.xpath("//span[text()='" + strControl + "']/parent::label/following-sibling::input"));
					if (objInput.getAttribute("type").equalsIgnoreCase("text")) {
						objInput.clear();
						objInput.sendKeys(strValue);
						WebDriverUtility.writeLogs("SET THE FIELD - " + strControl
								+ " with the VALUE - " + strValue);
						blnStatus = true;
					}
				}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//label[contains(@class,'control') and text()='" + strControl + "']/following-sibling::div[contains(@class,'filterSearch')]/input"))) {
					WebElement objInput = driver.findElement(
							By.xpath("//label[contains(@class,'control') and text()='" + strControl + "']/following-sibling::div[contains(@class,'filterSearch')]/input"));
					if (objInput.getAttribute("type").equalsIgnoreCase("text")) {
						objInput.clear();
						objInput.sendKeys(strValue);
						WebDriverUtility.writeLogs("SET THE FIELD - " + strControl
								+ " with the VALUE - " + strValue);
						blnStatus = true;
					}
				}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//*[contains(@class,'control-label') and (contains(text(),\""+strControl+"\"))]/following-sibling::dropdown/div[@class='ispdropdown']"))) {
					//NEW DROPDOWN CHANGE HANDLER
					WebElement objInput = driver.findElement(By.xpath("//*[contains(@class,'control-label') and (contains(text(),\""+strControl+"\"))]/following-sibling::dropdown"));
					WebElement objDropDownArrow = objInput.findElement(By.xpath("descendant::span[contains(@class,'dropArrow')]"));
					objDropDownArrow.click();
					String[] listValues = strValue.split(";");
					for(String val:listValues) {
						strValue = val;
						if(WebDriverUtility.IsWebElementExists(driver, (By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]")))){
							if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']"))) {
								driver.findElement(By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']")).click();
								WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " with the VALUE - " + strValue);
								blnStatus = true;
							}else {
								WebDriverUtility.writeLogs("FAILED: ITEM - '"+strValue+"' IS NOT DISPLAYED IN THE LIST");
								blnStatus = false;
							}
						}else {
							WebDriverUtility.writeLogs("FAILED: DROPDOWN MENU IS NOT DISPLAYED");
							blnStatus = false;
						}
					}
					//TO Handle the Confirm Button if available
					if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//*[text()='Confirm' and not(@disabled='true') and contains(@data-bind,'Confirm')]"))) {
						driver.findElement(By.xpath("//*[text()='Confirm' and not(@disabled='true') and contains(@data-bind,'Confirm')]")).click();
					}else {
						objDropDownArrow.click();
					}
				}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//*[contains(@class,'control-label')]/following-sibling::dropdown/div[contains(@class,'multiselect')]"))){
					//NEW DROPDOWN CHANGE HANDLER
					WebElement objInput = driver.findElement(By.xpath("//*[contains(@class,'control-label') and (contains(text(),\""+strControl+"\"))]/following-sibling::*[name()='dropdown' or name()='statusdropdown']"));
					WebElement objDropDownArrow = objInput.findElement(By.xpath("descendant::span[contains(@class,'dropArrow')]"));
					objDropDownArrow.click();
					String[] listValues = strValue.split(";");
					for(String val:listValues) {
						strValue = val;
						if(WebDriverUtility.IsWebElementExists(driver, (By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]")))){
							if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']"))) {
								driver.findElement(By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']")).click();
								WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " with the VALUE - " + strValue);
								blnStatus = true;
							}else {
								WebDriverUtility.writeLogs("FAILED: ITEM - '"+strValue+"' IS NOT DISPLAYED IN THE LIST");
								blnStatus = false;
							}
						}else {
							WebDriverUtility.writeLogs("FAILED: DROPDOWN MENU IS NOT DISPLAYED");
							blnStatus = false;
						}
					}
					//TO Handle the Confirm Button if available
					if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//*[text()='Confirm' and not(@disabled='true') and contains(@data-bind,'Confirm')]"))) {
						driver.findElement(By.xpath("//*[text()='Confirm' and not(@disabled='true') and contains(@data-bind,'Confirm')]")).click();
					}else {
						objDropDownArrow.click();
					}
				}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//*[contains(@class,'control-label') and (contains(text(),\""+strControl+"\"))]/following-sibling::div/descendant::*[name()='dropdown' or name()='statusdropdown']/div[@class='ispdropdown']"))) {
					//NEW DROPDOWN CHANGE HANDLER
					WebElement objInput = driver.findElement(By.xpath("descendant::*[contains(@class,'control-label') and (contains(text(),\""+strControl+"\"))]/following-sibling::div/descendant::*[name()='dropdown' or name()='statusdropdown']"));
					WebElement objDropDownArrow = objInput.findElement(By.xpath("descendant::span[contains(@class,'dropArrow')]"));
					try {
						WebDriverWait objWait = new WebDriverWait(driver, Duration.ofSeconds(15));
						objWait.until(ExpectedConditions.elementToBeClickable(objDropDownArrow));

					}catch(Exception e) {
					}
					objDropDownArrow.click();
					Thread.sleep(500);
					try {
						WebDriverWait objWait = new WebDriverWait(driver, Duration.ofSeconds(15));
						objWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]")));
						objWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]")));
					}catch(Exception e) {
					}
					String[] listValues = strValue.split(";");
					for(String val:listValues) {
						strValue = val;
						if(WebDriverUtility.IsWebElementExists(driver, (By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]")))){
							if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']"))) {
								driver.findElement(By.xpath("//div[contains(@class,'ispdropdown-popup') and not(contains(@style,'display: none'))]//span[@class='ellipsis' and text()='"+strValue+"']")).click();
								WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " with the VALUE - " + strValue);
								blnStatus = true;
							}else {
								WebDriverUtility.writeLogs("FAILED: ITEM - '"+strValue+"' IS NOT DISPLAYED IN THE LIST");
								blnStatus = false;
							}
						}else {
							WebDriverUtility.writeLogs("FAILED: DROPDOWN MENU IS NOT DISPLAYED");
							blnStatus = false;
						}
					}
					//TO Handle the Confirm Button if available
					//						if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//*[text()='Confirm' and not(@disabled='true') and contains(@data-bind,'Confirm')]"))) {
					//							driver.findElement(By.xpath("//*[text()='Confirm' and not(@disabled='true') and contains(@data-bind,'Confirm')]")).click();
					//						}else {
					//							JavascriptExecutor objJS = (JavascriptExecutor) driver;
					//							objJS.executeScript("arguments[0].click()", objDropDownArrow);
					////							objDropDownArrow.click();
					//						}
					if(blnStatus) WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl
							+ " with the VALUE - " + strValue);
				} else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//*[contains(text(),'"+strControl+"') ]/following-sibling::label/descendant::div[@class='slider round']"))) {
					WebElement objSlider = driver.findElement(By.xpath("//*[contains(text(),'"+strControl+"') ]/following-sibling::label/descendant::div[@class='slider round']"));
					if(strValue.equalsIgnoreCase("ON")) {
						if (objSlider.getCssValue("background-color").toString().equals("rgba(126, 179, 56, 1)")) {
							blnStatus = true;
							WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + "IS ALREADY SELECTED WITH the VALUE - " + strValue);
						} else if (objSlider.getCssValue("background-color").toString().equals("rgba(204, 204, 204, 1)")) {
							blnStatus = true;
							objSlider.click();
							WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " WITH the VALUE - " + strValue);
						}
					}else {
						if (objSlider.getCssValue("background-color").toString().equals("rgba(126, 179, 56, 1)")) {
							blnStatus = true;
							objSlider.click();
							WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " WITH the VALUE - " + strValue);
						} else if (objSlider.getCssValue("background-color").toString().equals("rgba(204, 204, 204, 1)")) {
							blnStatus = true;
							WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + "IS ALREADY SELECTED WITH the VALUE - " + strValue);
						}	
					}
				}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//*[contains(text(),'"+strControl+"')]/preceding-sibling::input[@type='checkbox']"))) {
					WebElement objCheckBox = driver.findElement(By.xpath("//*[contains(text(),'"+strControl+"')]/preceding-sibling::input[@type='checkbox']"));
					if(strValue.equalsIgnoreCase("ON")) {
						if (objCheckBox.isSelected()) {
							blnStatus = true;
							WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + "IS ALREADY SELECTED WITH the VALUE - " + strValue);
						} else {
							blnStatus = true;
							objCheckBox.click();
							WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " WITH the VALUE - " + strValue);
						}
					}else{
						if (!objCheckBox.isSelected()) {
							blnStatus = true;
							WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + "IS ALREADY SELECTED WITH the VALUE - " + strValue);
						} else {
							blnStatus = true;
							objCheckBox.click();
							WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " WITH the VALUE - " + strValue);
						}
					}

				}else if(WebDriverUtility.IsWebElementExists(driver, By.xpath("//label[contains(.,'"+strControl+"')]/descendant::input[@type='checkbox']"))) {
					WebElement objCheckBox = driver.findElement(By.xpath("//label[contains(.,'"+strControl+"')]/descendant::input[@type='checkbox']"));
					if(strValue.equalsIgnoreCase("ON")) {
						if (objCheckBox.isSelected()) {
							blnStatus = true;
							WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + "IS ALREADY SELECTED WITH the VALUE - " + strValue);
						} else {
							blnStatus = true;
							objCheckBox.click();
							WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " WITH the VALUE - " + strValue);
						}
					}else{
						if (!objCheckBox.isSelected()) {
							blnStatus = true;
							WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + "IS ALREADY SELECTED WITH the VALUE - " + strValue);
						} else {
							blnStatus = true;
							objCheckBox.click();
							WebDriverUtility.writeLogs("SET THE SECTION - " + strSection + " FIELD - " + strControl + " WITH the VALUE - " + strValue);
						}
					}

				}else {
					WebDriverUtility.writeLogs("FAILED: UNABLE TO FIND THE FIELD - '"+strControl+"'");
				}

			}

		}else if(strValue.equalsIgnoreCase("blank")||strValue.equalsIgnoreCase("blanks")) {
			blnStatus = true;
		}

		return blnStatus;

	}catch(Exception e) {

	}
	WebDriverUtility.funcUtil_HandleSync_Wait(driver);
	return true;
}

public static HashMap<String, String> suiteProperties = new HashMap<String, String>();
public static void writeLogs(String message) {
	System.out.println(message);
	//Attaching the Screenshots & Logs to the Allure Report
	if (WebDriverUtility.suiteProperties.get("Screenshots_AllSteps").equalsIgnoreCase("true")) {
		try {
			Reporter.log(message);
		} catch (Exception e) {
			System.out.println("Unable to attached the Log / Screenshot to the Allure Report");
		}
	}
}
}

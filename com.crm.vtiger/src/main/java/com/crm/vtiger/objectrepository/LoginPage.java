package com.crm.vtiger.objectrepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.crm.vtiger.genericutility.IPathConstants;

public class LoginPage {
public LoginPage(WebDriver driver){
	PageFactory.initElements(driver, this);
}
@FindBy(name="user_name")
private WebElement userNameTB;

@FindBy(name="user_password")
private WebElement passwordTB;

@FindBy(id="submitButton")
private WebElement loginButton;

public WebElement getUserNameTB() {
	return userNameTB;
}

public WebElement getPasswordTB() {
	return passwordTB;
}

public WebElement getLoginButton() {
	return loginButton;
}

public void login() {
	getUserNameTB().sendKeys(IPathConstants.VTIGER_UN);
	getPasswordTB().sendKeys(IPathConstants.VTIGER_PW);
	getLoginButton().click();
}
}

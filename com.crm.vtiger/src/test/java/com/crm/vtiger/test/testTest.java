package com.crm.vtiger.test;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.crm.vtiger.genericutility.BaseClass;
import com.crm.vtiger.genericutility.RetryAnalyzer;
import com.crm.vtiger.genericutility.WebDriverUtility;
@Listeners(com.crm.vtiger.genericutility.ListenerImplementation.class)
public class testTest/* extends BaseClass */ {
	@Test(retryAnalyzer = RetryAnalyzer.class)
public void test() {
		

	System.out.println("=============practice=============");
	Assert.fail();
	
	
}
}

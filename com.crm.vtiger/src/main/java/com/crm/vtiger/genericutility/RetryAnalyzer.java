package com.crm.vtiger.genericutility;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer{
	int count=0;
	int retryCount=2;
	@Override
	public boolean retry(ITestResult result) {
		// TODO Auto-generated method stub
	
		if(count<retryCount) {
			count++;
			 result.setStatus(ITestResult.FAILURE);
			return true;
		}
		return false;
	}
}

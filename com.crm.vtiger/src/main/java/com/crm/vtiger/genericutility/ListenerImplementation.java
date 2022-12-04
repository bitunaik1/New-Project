package com.crm.vtiger.genericutility;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class ListenerImplementation implements ITestListener{
	public WebDriverUtility wLib=new WebDriverUtility();
	ExtentReports repo;
	ExtentTest test;
	
	@Override
	public void onTestStart(ITestResult result) {
		String methodName=result.getMethod().getMethodName();
	    //step 3: create a test method during the test execution starts
		test=repo.createTest(methodName);
		System.out.println(methodName+"------> execution started");
//		System.out.println(result.getThrowable());	
//		System.out.println(result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String methodName=result.getMethod().getMethodName();
		//step 4: log the pass method
		test.log(Status.PASS, methodName);
		System.out.println(methodName+"------> execution success");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String methodName=result.getMethod().getMethodName();
		//step 6: log fail, take screenshot, attach screenshot to the report, add exception log
		test.log(Status.FAIL, methodName);
		test.log(Status.FAIL, result.getThrowable());
		System.out.println(methodName+"-----> execution failed");
		WebDriverUtility wLib=new WebDriverUtility();
		String path = null;
		try {
		path=wLib.takeScreenshot( methodName, BaseClass.listenerDriver);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		test.addScreenCaptureFromPath(path);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String methodName=result.getMethod().getMethodName();
		//step 5: log skip, show the exception on the report
		test.log(Status.SKIP, methodName);
		test.log(Status.SKIP, result.getThrowable());
	}

	@Override
	public void onStart(ITestContext context) {
		//step 1: extent report configuration
		ExtentSparkReporter reporter= new ExtentSparkReporter("./extentreport"+context.getName()+".html");
		reporter.config().setReportName("Regression Report");
		reporter.config().setDocumentTitle("Autodest execution report");
		reporter.config().setTheme(Theme.DARK);
		//stpe 2: attach the physical report and do the configuration
		repo=new ExtentReports();
		repo.attachReporter(reporter);
		repo.setSystemInfo("OS", "Windows 11");
		repo.setSystemInfo("Environment", "Testing");
		repo.setSystemInfo("URL", "http://localhost:8888");
		repo.setSystemInfo("Author", "Omprakash");
	}

	@Override
	public void onFinish(ITestContext context) {
		//step 7: 
		repo.flush();
	}
}

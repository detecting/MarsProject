package testPackage;

import java.awt.HeadlessException;
import java.io.File;
import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import helperPackage.BrowserFactory;
import org.testng.asserts.SoftAssert;
import pagesPackage.BasePage;
import pagesPackage.AboutPage;
import pagesPackage.LoginPage;
import utilityPackage.ExcelDataRead;

public class BaseClass {

    public static SoftAssert softAssert=new SoftAssert();
    public ExcelDataRead excelDataRead=new ExcelDataRead();

    //    public static SoftAssert softAssert;
    ExtentReports reports;
    public static ExtentTest testLog;
    ExtentHtmlReporter htmlReporter;

    //https://blog.csdn.net/haiweizhourong/article/details/54926563
    @BeforeTest
    public void testInit() {

        htmlReporter = new ExtentHtmlReporter( new File( System.getProperty( "user.dir" ) + "/test-output/MyExtentReport_" + BasePage.GetDateAndTime() + ".html" ) );
        htmlReporter.loadXMLConfig( new File( System.getProperty( "user.dir" ) + "/extent-config.xml" ) );
        reports = new ExtentReports();

        reports.setSystemInfo( "Environment", "QA" );
        reports.setSystemInfo( "Application", "QA Application" );
        reports.setSystemInfo( "Regression", "On Test Environment" );
        reports.attachReporter( htmlReporter );
    }


    @BeforeMethod
    public void setUpTest(Method method) throws HeadlessException, InterruptedException {
        //Assert, create softAssert instance
        //https://blog.csdn.net/u011541946/article/details/78506618?locationNum=10&fps=1
        // Get the methods name of test case
        String testName = method.getName();
        testLog = reports.createTest( testName + "_" + BasePage.GetDateAndTime() );

        //Initiate driver
        BrowserFactory.startBrowser( "chrome" );

        // The log show on the MyExtentReport.html
        testLog.log( Status.INFO, "Test Started" );
        testLog.log( Status.INFO, "Logged Started" );

        //Initiate PO for login page
        LoginPage loginPage = new LoginPage();

        //perform login steps and navigate to AboutPage
        AboutPage aboutPage = loginPage.LoginSteps();
        //Check if the MarsLogo shown up

        testLog.log( Status.INFO, "Logged successfully" );
        //softAssert.assertEquals( true, true, "Test failed after launching url" );

        //Initiate PO for home page and validate
        aboutPage.ValidateHomePage();
        testLog.log( Status.PASS, "Login test Passed" );
    }

    @AfterMethod
    public void tearDownTest(ITestResult result) {
//        SoftAssert softAssert=new SoftAssert();
        if (result.getStatus() == ITestResult.SUCCESS) {
            testLog.log( Status.PASS, "Test passed" );
        } else if (result.getStatus() == ITestResult.FAILURE) {
            testLog.log( Status.FAIL, "Test failed" );
        } else if (result.getStatus() == ITestResult.SKIP) {
            testLog.log( Status.SKIP, "Test skipped" );
        }

    }

    @AfterTest
    public void clearUp() {
        testLog.log( Status.SKIP, "Test Finished" );
        reports.flush();
    }

}

package pagesPackage;


import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import helperPackage.BrowserFactory;
import testPackage.BaseClass;
import utilityPackage.CustomWait;

public class AboutPage extends BasePage {

    //POM constructor
    public AboutPage() {
        PageFactory.initElements(BrowserFactory.driver, this);
    }

    //Edit button
    @FindBy(how = How.XPATH, using = "//i[@class='edit icon']")
    @CacheLookup
    private static WebElement BtnEdit;

    //MarsLogo button
    @FindBy(how = How.XPATH, using = "//a[contains(text(),'Mars Logo')]")
    @CacheLookup
    private static WebElement BtnMarsLogo;

    //ManageListings button
    @FindBy(how = How.XPATH, using = "//a[contains(text(),'Manage Listings')]")
    @CacheLookup
    private static WebElement BtnManageListings;


    //Click ManageListings button
    public ManageListingsPage ManageListings() throws InterruptedException {
        CustomWait.WaitForElements("//a[contains(text(),'Manage Listings')]");
        BtnManageListings.click();
        CustomWait.WaitForUrl("ListingManagement");
        return new ManageListingsPage();
    }

    //Click Edit button
    public EditProfilePage Edit() throws InterruptedException {
        CustomWait.WaitForElements("//i[@class='edit icon']");
        BtnEdit.click();
        while (!BrowserFactory.driver.getCurrentUrl().contains("ProfileEdit")) {
            Thread.sleep(200);
        }
        return new EditProfilePage();
    }

    //Method to check if home page is displayed
    public void ValidateHomePage() {

        //Verification the mars logo
        CustomWait.WaitForElements("//a[contains(text(),'Mars Logo')]");

        if (BtnMarsLogo.isDisplayed() && BtnMarsLogo.isEnabled()) {
            System.out.println("Test Pass, Home Page Verify Pass");
            BaseClass.testLog.log(Status.PASS, "Test Pass, Home Page Verify Pass");
        } else {
            System.out.println("Test Fail,  Home Page Verify Fail");
            BaseClass.testLog.log(Status.FAIL, "Test Fail,  Home Page Verify Fail");
        }
    }

}

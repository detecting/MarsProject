package testPackage;

import com.aventstack.extentreports.Status;
import helperPackage.BrowserFactory;
import org.testng.annotations.Test;

import pagesPackage.AboutPage;
import pagesPackage.EditProfilePage;
import pagesPackage.ManageListingsPage;

import java.io.IOException;

public class TestClass extends BaseClass {
    //A simple test to click on the shareskill
    @Test(description = "Edit Profile")
    public void EditProfile() throws InterruptedException {
        //Move to Home page
        AboutPage aboutPage = new AboutPage();
        //Move to EditProfilePage
        EditProfilePage editProfilePage = aboutPage.Edit();
        //log
        testLog.log( Status.INFO, "Navigate to EditProfilePage !" );
        //Edit Availability values
        editProfilePage.EditAvailability( excelDataRead.vailabilityType, excelDataRead.availableHours, excelDataRead.earnTarget );
        //log
        testLog.log( Status.INFO, "Finish Edit Availability !" );
        //Edit Language values
        editProfilePage.EditLanguages( excelDataRead.addLanguage,excelDataRead.languageLevel );
        //log
        testLog.log( Status.INFO, "Finish Edit Language !" );
        //log
        testLog.log( Status.INFO, "Finish EditProfile Testing !" );
        //Assert verification
        softAssert.assertEquals( editProfilePage.VerifyEditData( excelDataRead.vailabilityType, excelDataRead.availableHours, excelDataRead.earnTarget, excelDataRead.addLanguage, excelDataRead.languageLevel ), true, "Test Failed, EditProfile Data Verify Failed" );
        //************Because the Save function is not work well, so put assertion before it, assertion should after save in industry environment.
        //Click Save Button
        editProfilePage.Save();
        softAssert.assertAll();
    }
    @Test(description = "Management Listing Delete")
    public void DeleteManagementListing() throws InterruptedException, IOException {
        AboutPage aboutPage=new AboutPage();
        ManageListingsPage manageListingsPage=aboutPage.ManageListings();
        testLog.log( Status.INFO, "Navigate to Management Listing Page !" );
        manageListingsPage.SearchAllPagesAndAct("Title","Automation Tester01","DELETE");


    }
}

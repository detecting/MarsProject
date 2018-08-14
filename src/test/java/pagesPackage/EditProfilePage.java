package pagesPackage;

import com.aventstack.extentreports.Status;
import gherkin.lexer.Ru;
import helperPackage.BrowserFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import testPackage.BaseClass;
import utilityPackage.CustomWait;

public class EditProfilePage extends BasePage {
    //Availability--------
    //vailabilityType
    @FindBy(how = How.XPATH, using = "/html[1]/body[1]/div[1]/div[1]/section[2]/div[1]/div[1]/div[1]/form[1]/div[1]/div[1]/div[2]/div[1]")
    @CacheLookup
    private WebElement LocationAvailabilityType;

    @FindBy(how = How.XPATH, using = "/html[1]/body[1]/div[1]/div[1]/section[2]/div[1]/div[1]/div[1]/form[1]/div[1]/div[1]/div[2]/div[1]/div[2]")
    @CacheLookup
    private WebElement SelectAvailabilityType;

    //availableHours
    @FindBy(how = How.XPATH, using = "/html[1]/body[1]/div[1]/div[1]/section[2]/div[1]/div[1]/div[1]/form[1]/div[1]/div[1]/div[3]/div[1]/div[1]")
    @CacheLookup
    private WebElement LocationAvailableHours;

    @FindBy(how = How.XPATH, using = "/html[1]/body[1]/div[1]/div[1]/section[2]/div[1]/div[1]/div[1]/form[1]/div[1]/div[1]/div[3]/div[1]/div[2]")
    @CacheLookup
    private WebElement SelectAvailableHours;

    //earnTarget
    @FindBy(how = How.XPATH, using = "/html[1]/body[1]/div[1]/div[1]/section[2]/div[1]/div[1]/div[1]/form[1]/div[1]/div[1]/div[4]/div[1]/div[1]")
    @CacheLookup
    private WebElement LocationEarnTarget;

    @FindBy(how = How.XPATH, using = "/html[1]/body[1]/div[1]/div[1]/section[2]/div[1]/div[1]/div[1]/form[1]/div[1]/div[1]/div[4]/div[1]/div[2]")
    @CacheLookup
    private WebElement SelectEarnTarget;

    //Languages------
    //Languages AddNew
    @FindBy(how = How.XPATH, using = "//form[@class='ui form']//div[3]//div[1]//div[2]//div[1]//table[1]//thead[1]//tr[1]//th[3]//div[1]")
    @CacheLookup
    private WebElement BtnLanguagesAddNew;

    //Languages Add
    @FindBy(how = How.XPATH, using = "//input[@value='Add']")
    @CacheLookup
    private WebElement BtnLanguagesAdd;

    //Add Language
    @FindBy(how = How.XPATH, using = "//input[@placeholder='Add Language']")
    @CacheLookup
    private WebElement InputAddLanguage;

    //Language Level
    @FindBy(how = How.XPATH, using = "//select[@name='level']")
    @CacheLookup
    private WebElement SelectLanguageLevel;

    //Language Form
    @FindBy(how = How.XPATH, using = "//form[@class='ui form']//div[3]//div[1]//div[2]//div[1]//table[1]")
    @CacheLookup
    private WebElement TableLanguage;

    //Save
    @FindBy(how = How.XPATH, using = "//button[contains(text(),'Save')]")
    @CacheLookup
    private WebElement BtnSave;


    //Select VailabilityType
    private void VailabilityType(String vailabilityType) throws InterruptedException {
        Actions actions = new Actions( BrowserFactory.driver );
        actions.moveToElement( LocationAvailabilityType ).build().perform();
        CustomWait.WaitForElements( "/html[1]/body[1]/div[1]/div[1]/section[2]/div[1]/div[1]/div[1]/form[1]/div[1]/div[1]/div[2]/div[1]/div[2]" );
        for (WebElement webElement : SelectAvailabilityType.findElements( By.tagName( "div" ) )) {
            Thread.sleep( 500 );
            if (webElement.getText().trim().equals( vailabilityType.trim() )) {
                ((JavascriptExecutor) BrowserFactory.driver).executeScript( "arguments[0].click();", webElement );
            }
        }

    }

    //Select AvailableHours
    private void AvailableHours(String availableHours) throws InterruptedException {
        Actions actions = new Actions( BrowserFactory.driver );
        actions.moveToElement( LocationAvailableHours ).build().perform();
        CustomWait.WaitForElements( "/html[1]/body[1]/div[1]/div[1]/section[2]/div[1]/div[1]/div[1]/form[1]/div[1]/div[1]/div[3]/div[1]/div[2]" );
        for (WebElement webElement : SelectAvailableHours.findElements( By.tagName( "div" ) )) {
            Thread.sleep( 500 );
            if (webElement.getText().trim().equals( availableHours.trim() )) {
                ((JavascriptExecutor) BrowserFactory.driver).executeScript( "arguments[0].click();", webElement );
            }
        }

    }

    //Select EarnTarget
    private void EarnTarget(String ernTarget) throws InterruptedException {
        Actions actions = new Actions( BrowserFactory.driver );
        actions.moveToElement( LocationEarnTarget ).build().perform();
        CustomWait.WaitForElements( "/html[1]/body[1]/div[1]/div[1]/section[2]/div[1]/div[1]/div[1]/form[1]/div[1]/div[1]/div[4]/div[1]/div[2]" );
        for (WebElement webElement : SelectEarnTarget.findElements( By.tagName( "div" ) )) {
            Thread.sleep( 500 );
            if (webElement.getText().trim().equals( ernTarget.trim() )) {
                ((JavascriptExecutor) BrowserFactory.driver).executeScript( "arguments[0].click();", webElement );
            }
        }

    }

    //select languageLevel
    private void LanguageLevel(String languageLevel) {
        CustomWait.WaitForElements( "//select[@name='level']" );
        Select select = new Select( SelectLanguageLevel );
        select.selectByVisibleText( languageLevel.trim() );

    }

    //Add language
    private void AddLanguage(String language) {
        CustomWait.WaitForElements( "//input[@placeholder='Add Language']" );
        InputAddLanguage.clear();
        InputAddLanguage.sendKeys( language.trim() );
    }

    //Click Add New button
    private void LanguagesAddNew() {
        CustomWait.WaitForElements( "//form[@class='ui form']//div[3]//div[1]//div[2]//div[1]//table[1]//thead[1]//tr[1]//th[3]//div[1]" );
        BtnLanguagesAddNew.click();
    }

    //Click Add button
    private void LanguagesAdd() {
        CustomWait.WaitForElements( "//form[@class='ui form']//div[3]//div[1]//div[2]//div[1]//table[1]//thead[1]//tr[1]//th[3]//div[1]" );
        BtnLanguagesAdd.click();
    }

    //VerifyAvailability
    private boolean VerifyAvailability(String availabilityType, String availableHours, String earnTarget) {

        CustomWait.WaitForElements( "/html[1]/body[1]/div[1]/div[1]/section[2]/div[1]/div[1]/div[1]/form[1]/div[1]/div[1]/div[2]/div[1]" );
        if ((LocationAvailabilityType.getText().trim().equals( availabilityType.trim() )) &&
                (LocationAvailableHours.getText().trim().equals( availableHours )) &&
                (LocationEarnTarget.getText().trim().equals( earnTarget.trim() ))) {
            BaseClass.testLog.log( Status.PASS, "VerifyAvailability PASS" );
            return true;
        }
        BaseClass.testLog.log( Status.FAIL, "VerifyAvailability Faill" );
        return false;
    }

    //VerifyLanguages
    private boolean VerifyLanguages(String language, String level) {
        if (VerifyTable( TableLanguage, language, level )) {
            BaseClass.testLog.log( Status.PASS, "VerifyLanguages PASS!" );
            return true;
        }
        BaseClass.testLog.log( Status.FAIL, "VerifyLanguages FAIL!" );
        return false;
    }

    //Edit Language
    public void EditLanguages(String language, String languageLevel) {
        //Click Add New Button
        LanguagesAddNew();
        //Fill language and level
        AddLanguage( language );
        LanguageLevel( languageLevel );
        //Click Add button
        LanguagesAdd();
    }

    //Edit Availability
    public void EditAvailability(String vailabilityType, String availableHours, String ernTarget) throws InterruptedException {
        VailabilityType( vailabilityType );
        AvailableHours( availableHours );
        EarnTarget( ernTarget );
    }

    //Now verity the Availability data and Languages data
    public boolean VerifyEditData(String availabilityType, String availableHours, String earnTarget, String language, String level) {
        if (VerifyAvailability( availabilityType, availableHours, earnTarget ) && VerifyLanguages( language, level )) {
            BaseClass.testLog.log( Status.PASS, "VerifyEditData PASS" );
            return true;
        }
        BaseClass.testLog.log( Status.FAIL, "VerifyEditData FAIL" );
        return false;
    }

    //Press Save Button
    public EditProfilePage Save() {
        CustomWait.WaitForElements( "//button[contains(text(),'Save')]" );
        BtnSave.click();
        return new EditProfilePage();
    }
}

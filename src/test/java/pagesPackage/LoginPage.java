package pagesPackage;

import helperPackage.BrowserFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import utilityPackage.CustomWait;
import utilityPackage.ExcelDataConfig;

import java.awt.*;


public class LoginPage extends BasePage {

    //BrowserFactory browserObj;
    //driver constructor for the page
    public LoginPage() {
        PageFactory.initElements( BrowserFactory.driver, this );
    }
//    WebDriverWait wait = new WebDriverWait( BrowserFactory.driver, 20 );

    //Click on Sign In button
    @FindBy(how = How.XPATH, using = "//a[contains(text(),'Sign In')]")
    @CacheLookup
    static WebElement signIn;

    //Username definition
    @FindBy(how = How.XPATH, using = "/html/body/div[2]/div/div/div[1]/form/div[1]/input")
    @CacheLookup
    static WebElement email;

    //password definition
    @FindBy(how = How.XPATH, using = "/html[1]/body[1]/div[2]/div[1]/div[1]/div[1]/form[1]/div[2]/input[1]")
    @CacheLookup
    static WebElement password;

    //Login button definition
    @FindBy(how = How.XPATH, using = "//div[@class='fluid ui teal button']")
    @CacheLookup
    static WebElement loginButton;

    //Operation of each element
    //click signIn button
    public void SignIn() {
        signIn.click();
    }

    //Fill email
    public void Email(String emailInfo) {
        email.clear();
        email.sendKeys( emailInfo );
    }

    //Fill passwd
    public void Password(String passwdInfo) {
        password.clear();
        password.sendKeys( passwdInfo );
    }

    //CLick LoginButton
    public void LoginButton() {
        loginButton.click();
    }


    //Method to perform login actions
    public AboutPage LoginSteps() throws HeadlessException, InterruptedException {

        //Get the data from TestData excel file
        ExcelDataConfig excel = new ExcelDataConfig( "./TestData/TestData.xlsx" );

        //Wait for the signIn to show up
        CustomWait.WaitForElements( "//a[contains(text(),'Sign In')]" );

        //Click on signIn button
        SignIn();

        //Wait fot Login Form to show up
        CustomWait.FluentWait( "//div[@class='content one column stackable center aligned page grid']" );

        //Send Value to the UserName textBox
        Email( excel.getData( "Login", 1, 0 ) );

        //Show info to console
        System.out.println( "Entered Username" );

        //Send value to the password textBox
        Password( excel.getData( "Login", 1, 1 ) );

        //Show info to console
        System.out.println( "Entered password" );

        //Click on Login Button
        while (!loginButton.isEnabled() && loginButton.isDisplayed()) {
            Thread.sleep( 200 );
        }
        LoginButton();

        //Show info to console
        System.out.println( "Clicked on Login button" );
        System.out.println( "Return to Home Page" );

        //Wait for url change and return to Home page
        CustomWait.WaitForUrl( "/Home/About" );
        return new AboutPage();
    }

}

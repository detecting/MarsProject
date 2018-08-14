package pagesPackage;

import helperPackage.BrowserFactory;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BasePage {
    //constructor--
    public BasePage() {
        //initial pagefactory
        PageFactory.initElements( BrowserFactory.driver,this );
        //implicitlyWait
        BrowserFactory.driver.manage().timeouts().implicitlyWait( 50, TimeUnit.SECONDS );

        //Wait for page loading
        ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
            @NullableDecl
            @Override
            public Boolean apply(@NullableDecl WebDriver webDriver) {
                return ((JavascriptExecutor) BrowserFactory.driver).executeScript( "return document.readyState" ).equals( "complete" );
            }
        };
        WebDriverWait wait = new WebDriverWait( BrowserFactory.driver, 50 );
        wait.until( pageLoadCondition );
    }

    //Get time and format
    public static String GetDateAndTime() {
        return new SimpleDateFormat( "yyyy-MM-dd_HH-mm-ss" ).format( new Date() );
    }

    //Table data verify, now only for 2 cols table, will take care in future.
    public boolean VerifyTable(WebElement webElementTable, String valueOne, String valueTow) {
        List<WebElement> tbodys = webElementTable.findElements( By.tagName( "tbody" ) );
        for (WebElement tbody : tbodys) {
            List<WebElement> cols = tbody.findElement( By.tagName( "tr" ) ).findElements( By.tagName( "td" ) );
            int i=0;
            if ((cols.get( i ).getText().trim().equals( valueOne ))&&(cols.get( ++i ).getText().trim().equals( valueTow ))){
                return true;
            }
        }
        return false;
    }
}

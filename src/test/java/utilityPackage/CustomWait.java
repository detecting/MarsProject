package utilityPackage;

import java.time.Duration;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import com.google.common.base.Function;

import helperPackage.BrowserFactory;

//FluentWait
public class CustomWait {
    //Method to FluentWait
    public static void FluentWait(final String xPath) {
        Wait<WebDriver> fluentWait = new FluentWait<>( BrowserFactory.driver ).withTimeout( Duration.ofSeconds( 20 ) )
                .pollingEvery( Duration.ofMillis( 200 ) ).ignoring( NoSuchElementException.class );
        WebElement element = fluentWait.until( new Function<WebDriver, WebElement>() {
            @NullableDecl
            @Override
            public WebElement apply(@NullableDecl WebDriver webDriver) {
                return BrowserFactory.driver.findElement( By.xpath( xPath ) );
            }
        } );
    }
    //WaitForElements
    public static void WaitForElements(String xPath) {
        WebElement element = (new WebDriverWait( BrowserFactory.driver, 20 )).until( ExpectedConditions.presenceOfElementLocated( By.xpath( xPath ) ) );
    }

    //WaitForUrl to change
    public static void WaitForUrl(String url) throws InterruptedException {
        while (!(BrowserFactory.driver.getCurrentUrl().contains( url ))) {
            Thread.sleep( 200 );
        }
    }
}



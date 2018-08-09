package helperPackage;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilityPackage.ConfigReader;
import utilityPackage.MonitoringStatus;

public class BrowserFactory {

    //Global driver
    public static WebDriver driver;
    private static DesiredCapabilities capabilities;
    private static BrowserMobProxy proxy;
    private static Proxy seleniumProxy;
    private static Har har;

    public BrowserFactory() {
        proxy = new BrowserMobProxyServer();
        proxy.start();
        seleniumProxy = ClientUtil.createSeleniumProxy( proxy );
        capabilities = new DesiredCapabilities();
        capabilities.setCapability( CapabilityType.PROXY, seleniumProxy );
        proxy.enableHarCaptureTypes( CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT );
    }

    public static void MMonitorResponse() {
        proxy.newHar( "url" );
        har = proxy.getHar();
        int i = har.getLog().getEntries().size();
        System.out.println( har.getLog().getEntries().get( i - 1 ).getResponse().getStatus() );
    }

//    //Webdriver FluentWait
//    WebDriverWait wait = new WebDriverWait(BrowserFactory.driver, 20);


    //@Parameters("browserName")
    //A custom method to choose the browser on which the test need to be executed
    public static void startBrowser(String browserName) {
        //choose Firefox browser
        if (browserName.equalsIgnoreCase( "firefox" )) {
            driver = new FirefoxDriver();
        }
        //choose Chrome browser
        else if (browserName.equalsIgnoreCase( "chrome" )) {
            System.setProperty( "webdriver.chrome.driver", ConfigReader.getChromePath() );
            driver = new ChromeDriver();
        }
        //choose IE browser
        else if (browserName.equalsIgnoreCase( "ie" )) {
            System.setProperty( "webdriver.ie.driver", ConfigReader.getIEPath() );
            driver = new InternetExplorerDriver();
        }

        //choose chrome Headless browser
        if (browserName.equalsIgnoreCase( "headless" )) {
            System.setProperty( "webdriver.chrome.driver", ConfigReader.getChromePath() );
            driver = new ChromeDriver();
            ChromeOptions options = new ChromeOptions();
            options.addArguments( "headless" );
            options.addArguments( "window-size=1200x600" );
            driver = new ChromeDriver( options );
        }

        //maximize browser
        driver.manage().window().maximize();

        //launch the url
        driver.get( ConfigReader.getURL() );
    }


/**
 //Global driver
 public static WebDriver driver;


 //Webdriver FluentWait
 WebDriverWait wait = new WebDriverWait(BrowserFactory.driver, 20);


 //@Parameters("browserName")
 //A custom method to choose the browser on which the test need to be executed
 public static void startBrowser(String browserName) {
 //choose Firefox browser
 if (browserName.equalsIgnoreCase("firefox")) {
 driver = new FirefoxDriver();
 }
 //choose Chrome browser
 else if (browserName.equalsIgnoreCase("chrome")) {
 System.setProperty("webdriver.chrome.driver", ConfigReader.getChromePath());
 driver = new ChromeDriver();
 }
 //choose IE browser
 else if (browserName.equalsIgnoreCase("ie")) {
 System.setProperty("webdriver.ie.driver", ConfigReader.getIEPath());
 driver = new InternetExplorerDriver();
 }

 //choose chrome Headless browser
 if (browserName.equalsIgnoreCase("headless")) {
 System.setProperty("webdriver.chrome.driver", ConfigReader.getChromePath());
 driver = new ChromeDriver();
 ChromeOptions options = new ChromeOptions();
 options.addArguments("headless");
 options.addArguments("window-size=1200x600");
 driver = new ChromeDriver(options);
 }

 //maximize browser
 driver.manage().window().maximize();

 //launch the url
 driver.get(ConfigReader.getURL());
 }
 **/
}

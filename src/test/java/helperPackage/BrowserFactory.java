package helperPackage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import utilityPackage.ConfigReader;
import utilityPackage.MonitoringStatus;

public class BrowserFactory extends MonitoringStatus {
    //*********************************
    public BrowserFactory(String url) {
        super(url);
    }

    //*******************************
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
//            driver = new ChromeDriver();
            driver = new ChromeDriver(capabilities);
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
}

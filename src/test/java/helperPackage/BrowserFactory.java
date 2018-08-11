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

import utilityPackage.ConfigReader;

import java.io.File;
import java.io.IOException;

public class BrowserFactory {

    //Global driver
    public static WebDriver driver;
    private static DesiredCapabilities capabilities;
    private static BrowserMobProxy proxy;
    private static Proxy seleniumProxy;
    private static Har har;
    private static int beforeMonitor;
    private static int afterMonitor;

    public BrowserFactory() {

    }

    public static void MonitorResponseStart() {
        proxy.newHar(BrowserFactory.driver.getCurrentUrl());
        har = proxy.getHar();
        //Get the NO. of Entries bofore monitor
//        beforeMonitor = har.getLog().getEntries().size();
    }

    //It is used to make sure that the table are loaded completely
    public static void MMonitorResponseEnd() throws IOException, InterruptedException {
        //判断是否符合条件
        //如果不满足条件，重新获取响应的次数
        while (!((har.getLog().getEntries().get(har.getLog().getEntries().size() - 1).getRequest().getUrl().contains("getMultipleServiceListing"))
                &&
                (har.getLog().getEntries().get(har.getLog().getEntries().size() - 1).getResponse().getStatus() == 200))) {
            Thread.sleep(200);
        }
        har.writeTo(new File("har.json"));


        //*************************************
        /**
         *   afterMonitor = har.getLog().getEntries().size();
         *         //如果没有抓到包，则等待，否则，遍历
         *         while ((afterMonitor - beforeMonitor) == 0) {
         *             Thread.sleep(200);
         *             System.out.println("Waiting for get response status!");
         *             //重新获取数目
         *             afterMonitor = har.getLog().getEntries().size();
         *         }
         *
         *         //如果抓到了包
         *         if (beforeMonitor == 0) {
         *             for (int i = beforeMonitor; i < afterMonitor; i++) {
         *                 //如果得到得到的状态不是200，则等待；
         *                 while (!((har.getLog().getEntries().get(i).getRequest().getUrl().contains("getMultipleServiceListing"))
         *                         &&
         *                         (har.getLog().getEntries().get(i).getResponse().getStatus() == 200))) {
         *                     Thread.sleep(200);
         *                 }
         *                 System.out.println(har.getLog().getEntries().get(afterMonitor - 1).getRequest().getUrl());
         *                 System.out.println(har.getLog().getEntries().get(afterMonitor - 1).getResponse().getStatus());
         *                 har.writeTo(new File("har.json"));
         *                 //否则返回ture
         *                 return true;
         *             }
         *         } else {
         *             for (int i = beforeMonitor - 1; i < afterMonitor; i++) {
         *                 //如果得到得到的状态是200
         *                 while (!((har.getLog().getEntries().get(i).getRequest().getUrl().contains("getMultipleServiceListing"))
         *                         &&
         *                         (har.getLog().getEntries().get(i).getResponse().getStatus() == 200))) {
         *                     Thread.sleep(200);
         *                 }
         *                 har.writeTo(new File("har.json"));
         *                 System.out.println(har.getLog().getEntries().get(afterMonitor - 1).getRequest().getUrl());
         *                 System.out.println(har.getLog().getEntries().get(afterMonitor - 1).getResponse().getStatus());
         *                 //否则返回ture
         *                 return true;
         *             }
         *         }
         *         return false;
          */

    }


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
            //****************************
            proxy = new BrowserMobProxyServer();
            proxy.start();
            // get the Selenium proxy object
            seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
            // configure it as a desired capability
            capabilities = new DesiredCapabilities();
            capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
            // start the browser up
            driver = new ChromeDriver(capabilities);
            // enable more detailed HAR capture, if desired (see CaptureType for the complete list)
            proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);

            // create a new HAR with the label "yahoo.com"
            //****************************
//            driver = new ChromeDriver();
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

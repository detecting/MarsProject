package utilityPackage;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class MonitoringStatus {
    private String url;
    public static DesiredCapabilities capabilities;
    public static BrowserMobProxy proxy;
    public static Proxy seleniumProxy;

    public void run() {
        WebDriver driver = new ChromeDriver();
        BrowserMobProxy proxy = new BrowserMobProxyServer();
        proxy.start();
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy( proxy );
        capabilities = new DesiredCapabilities();
        capabilities.setCapability( CapabilityType.PROXY, seleniumProxy );

        driver = new ChromeDriver( capabilities );
        proxy.enableHarCaptureTypes( CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT );
        proxy.newHar( "url" );
        driver.get( "https://www.google.co.nz" );
        Har har = proxy.getHar();
        int i = har.getLog().getEntries().size();
        System.out.println( har.getLog().getEntries().get( i - 1 ).getResponse().getStatus() );


//        System.setProperty("webdriver.chrome.driver", ConfigReader.getChromePath());
//        BrowserMobProxy proxy = new BrowserMobProxyServer();
//        proxy.start();
//        // get the Selenium proxy object
//        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
//        // configure it as a desired capability
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
//        // start the browser up
//        driver = new ChromeDriver(capabilities);
//        // enable more detailed HAR capture, if desired (see CaptureType for the complete list)
//        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
//        // create a new HAR with the label "yahoo.com"
//        proxy.newHar("google.com");
//        // open yahoo.com
//        driver.get("https://www.google.co.nz");
//        // get the HAR data
//        Har har = proxy.getHar();
//        int i = har.getLog().getEntries().size();
//        System.out.println(har.getLog().getEntries().get(i-1).getResponse().getStatus());
    }


}

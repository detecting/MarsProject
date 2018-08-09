package testPackage;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.filters.RequestFilter;
import net.lightbody.bmp.filters.ResponseFilter;
import net.lightbody.bmp.mitm.TrustSource;
import net.lightbody.bmp.proxy.BlacklistEntry;
import net.lightbody.bmp.proxy.CaptureType;
import net.lightbody.bmp.proxy.auth.AuthType;
import net.lightbody.bmp.proxy.dns.AdvancedHostResolver;
import org.littleshoot.proxy.HttpFiltersSource;
import org.littleshoot.proxy.MitmManager;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;
import utilityPackage.ConfigReader;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Temp {
    public static WebDriver driver;

    @Test
    public void test() throws IOException {
        System.setProperty("webdriver.chrome.driver", ConfigReader.getChromePath());
        BrowserMobProxy proxy = new BrowserMobProxyServer();
        proxy.start();
        // get the Selenium proxy object
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        // configure it as a desired capability
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
        // start the browser up
        driver = new ChromeDriver(capabilities);
        // enable more detailed HAR capture, if desired (see CaptureType for the complete list)
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        // create a new HAR with the label "yahoo.com"
        proxy.newHar("google.com");
        // open yahoo.com
        driver.get("https://www.google.co.nz");
        // get the HAR data
        Har har = proxy.getHar();
        har.writeTo(new File("baidu.com.har"));
        System.out.println(har);

    }
}


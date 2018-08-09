package utilityPackage;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import org.openqa.selenium.WebDriver;

//public class ScreenShot {

	//Method to take screenshot and store in screenshot folder
		//public static void captureScreenshot(WebDriver driver, String screenshotName)
		//{
			//try
			//{
		        //File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				//FileUtils.copyFile(src, new File("./Screenshots/"+screenshotName+".png"));
			//}
			//catch(Exception e)
			//{
				//System.out.println(e.getMessage());
			//}
		//}
	
	public class ScreenShot {
		//Method to take screenshot and store in screenshot folder
			public static void captureScreenshot(WebDriver driver, String screenshotName) throws HeadlessException, AWTException, IOException
			{
				// This code will capture screenshot of current screen      
		        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

		        // This will store screenshot on Specific location
		        ImageIO.write(image, "png", new File("./Screenshots/"+screenshotName+".png")); 
			}
	

	
}

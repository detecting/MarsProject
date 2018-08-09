package pagesPackage;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import helperPackage.BrowserFactory;

public class ShareSkillPage {

	public ShareSkillPage()
	{
		//browserObj = new BrowserFactory();
		PageFactory.initElements(BrowserFactory.driver, this);
	}
	
	
	//Click on Sign In button	
		@FindBy(how=How.XPATH, using = "//*[@id='account-profile-section']/div/section[1]/div/div[2]/a")
		@CacheLookup
		static WebElement Skillshare;
		
		
	public void ClickSkill() {
		// TODO Auto-generated method stub
		
		Skillshare.click();
	}
}

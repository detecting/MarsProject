package pagesPackage;

import helperPackage.BrowserFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import utilityPackage.CustomWait;

import java.io.IOException;
import java.util.List;

public class ManageListingsPage extends BasePage {

    public ManageListingsPage() {
        PageFactory.initElements(BrowserFactory.driver, this);
    }

    private static int locationHead;
    //Table define
    @FindBy(how = How.XPATH, using = "//*[@id=\"listing-management-section\"]/div[2]/div[1]/table")
    @CacheLookup
    private WebElement Table;

    @FindBy(how = How.XPATH, using = "//*[@id=\"listing-management-section\"]/div[2]/div[1]/div")
    @CacheLookup
    private WebElement Pagination;
    @FindBy(how = How.XPATH, using = "//button[@class='ui icon positive right labeled button']")
    private WebElement BtnYes;

    private static int getLocationHead() {
        return locationHead;
    }

    private static void setLocationHead(int locationHead) {
        ManageListingsPage.locationHead = locationHead;
    }

    private void Yes() {
        CustomWait.WaitForElements("/html[1]/body[1]/div[2]/div[1]");
        BtnYes.click();
        CustomWait.WaitForElements("//*[@id=\"listing-management-section\"]/div[2]/div[1]/table");
    }

    public void SearchAllPagesAndAct(String headName, String specifiedContent, String actionType) throws IOException, InterruptedException {
        CustomWait.WaitForElements("//*[@id=\"listing-management-section\"]/div[2]/div[1]/div");
        List<WebElement> webElementList = Pagination.findElements(By.tagName("button"));

        //******************逻辑有问题
        do {
            ActionOnSpecifiedItems(headName, specifiedContent, actionType);
            webElementList.get(webElementList.size() - 1).click();
        }
        while ((webElementList.get(webElementList.size() - 1).isDisplayed()) && (webElementList.get(webElementList.size() - 1).isEnabled()));

    }

    //一条一条删除，逻辑有些问题
    private void ActionOnSpecifiedItems(String headName, String specifiedContent, String actionType) throws IOException, InterruptedException {
        CustomWait.WaitForElements("//*[@id=\"listing-management-section\"]/div[2]/div[1]/table");
        List<WebElement> tHeads = Table.findElement(By.tagName("thead")).findElement(By.tagName("tr")).findElements(By.tagName("th"));
        for (int i = 0; i < tHeads.size(); i++) {
            if (tHeads.get(i).getText().trim().equals(headName)) {
                setLocationHead(i);
                break;
            }
        }
        List<WebElement> rows = Table.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
        for (WebElement row : rows) {
            List<WebElement> cols = row.findElements(By.tagName("td"));
            //if head == specifiedContent
            if (cols.get(getLocationHead()).getText().equals(specifiedContent)) {
                //Click delete. now only delete take care. if you want to use Eye and Edit function, please implement them.
                List<WebElement> is = cols.get(cols.size() - 1).findElements(By.tagName("i"));
                switch (actionType) {
                    case "DELETE":
                        //点击删除之前，开始获取状态
                        BrowserFactory.MonitorResponseStart();
                        is.get(is.size() - 1).click();
                        Yes();
                        Thread.sleep(2000);
                        //点击删除之后，获取状态
                        BrowserFactory.MMonitorResponseEnd();
                        break;
                    case "EYE":
                        break;
                    case "EDIT":
                        break;
                    default:
                        break;
                }
            }
        }

    }

}

package pagesPackage;

import helperPackage.BrowserFactory;
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

    private int pageNumber = 1;

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

    private boolean CheckNextButtonClickable() {
        CustomWait.WaitForElements("//*[@id=\"listing-management-section\"]/div[2]/div[1]/div");
        List<WebElement> webElementList = Pagination.findElements(By.tagName("button"));
        if ((webElementList.get(webElementList.size() - 1).isDisplayed()) && (webElementList.get(webElementList.size() - 1).isEnabled())) {
            return true;
        } else {
            return false;

        }
    }

    private boolean ClickNextButtonDynamically() throws IOException, InterruptedException {
        CustomWait.WaitForElements("//*[@id=\"listing-management-section\"]/div[2]/div[1]/div");
        List<WebElement> webElementList = Pagination.findElements(By.tagName("button"));
        if ((webElementList.get(webElementList.size() - 1).isDisplayed()) && (webElementList.get(webElementList.size() - 1).isEnabled())) {
            //click the next button
            //获取status...
            BrowserFactory.MonitorResponseStart();
            webElementList.get(webElementList.size() - 1).click();
            BrowserFactory.MMonitorResponseEnd();
            return true;
        } else {
            return false;

        }

    }

    public void SearchAllPagesAndAct(String headName, String specifiedContent, String actionType) throws IOException, InterruptedException {


        ActionOnSpecifiedItems(headName, specifiedContent, actionType);

    }

    private void DeleteItems(List<WebElement> is) throws InterruptedException, IOException {
        //点击删除之前，开始获取状态
        //点击删除
        is.get(is.size() - 1).click();
        BrowserFactory.MonitorResponseStart();
        //选择yes
        Yes();
//        Thread.sleep(2000);
        //点击删除之后，获取状态
        BrowserFactory.MMonitorResponseEnd();
//        System.out.println("删除加载完成。");
    }


    //检查页面中是否有许哟啊删除的item,如果有,返回true
    private boolean CheckIfItemExist(String specifiedContent) {
        List<WebElement> rows = Table.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
        for (WebElement row : rows) {
            List<WebElement> cols = row.findElements(By.tagName("td"));
            //if head == specifiedContent
            if (cols.get(getLocationHead()).getText().equals(specifiedContent)) {
                return true;
            }
        }
        return false;
    }

    private void SearchPageAndDelete(String headName, String specifiedContent, String actionType) throws IOException, InterruptedException {
        List<WebElement> rows = Table.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
        //循环检测到删除，删除后跳出个for
        for (int i = 0; i < rows.size(); i++) {
            List<WebElement> cols = rows.get(i).findElements(By.tagName("td"));
            //if head == specifiedContent
            if (cols.get(getLocationHead()).getText().equals(specifiedContent)) {
                //Click delete. now only delete take care. if you want to use Eye and Edit function, please implement them.
                List<WebElement> is = cols.get(cols.size() - 1).findElements(By.tagName("i"));
                switch (actionType) {
                    case "DELETE":
                        DeleteItems(is);
                        //执行删除以后，应该在该页面中心检索进行删除
                        //删除以后再从第一行开始
                        i = 0;
                        break;
                    //EYE and EDIT can be implement here!
                    default:
                        break;
                }
            }
        }

    }

    //一条一条删除，逻辑有些问题
    private void ActionOnSpecifiedItems(String headName, String specifiedContent, String actionType) throws IOException, InterruptedException {
        CustomWait.WaitForElements("//*[@id=\"listing-management-section\"]/div[2]/div[1]/table");
        List<WebElement> tHeads = Table.findElement(By.tagName("thead")).findElement(By.tagName("tr")).findElements(By.tagName("th"));
        for (int i = 0; i < tHeads.size(); i++) {
            if (tHeads.get(i).getText().trim().equals(headName)) {
                //记下列号
                setLocationHead(i);
                break;
            }
        }
        //*****************************
        //检查是否有可以删除项
        do {
            //首先检查是否有可删除,进行删除，删除完了在进行查看本页是否有可删除
            while (CheckIfItemExist(specifiedContent)) {
                //进行删除
                SearchPageAndDelete(headName, specifiedContent, actionType);
            }
            //如果没有，就跳转到下一页
            //输出信息，第 I 页没有找到
            System.out.println("Can not find at " + pageNumber + "page");
            pageNumber++;
        }
        while (ClickNextButtonDynamically());


    }
}


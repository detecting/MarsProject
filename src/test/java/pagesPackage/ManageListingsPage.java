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
        PageFactory.initElements( BrowserFactory.driver, this );
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
        CustomWait.WaitForElements( "/html[1]/body[1]/div[2]/div[1]" );
        BtnYes.click();
        CustomWait.WaitForElements( "//*[@id=\"listing-management-section\"]/div[2]/div[1]/table" );
    }

    private boolean ClickNextButtonDynamically() throws IOException, InterruptedException {
        CustomWait.WaitForElements( "//*[@id=\"listing-management-section\"]/div[2]/div[1]/div" );
        List<WebElement> webElementList = Pagination.findElements( By.tagName( "button" ) );
        //click the next button
        if ((webElementList.get( webElementList.size() - 1 ).isDisplayed()) && (webElementList.get( webElementList.size() - 1 ).isEnabled())) {
            //如果可以点击，返回true
            //确定点击next之后，table表加载成功；
            BrowserFactory.MonitorResponseStart();
            webElementList.get( webElementList.size() - 1 ).click();
            BrowserFactory.MMonitorResponseEnd();
            return true;
        } else {
            //如果不能点击，返回false
            return false;
        }
    }

    public void SearchAllPagesAndAct(String headName, String specifiedContent, String actionType) throws IOException, InterruptedException {

        //******************逻辑有问题
        //先看第一页，循环删除符合项
//        do {
        //先查看整个页面，在判断是否下一页还可以点击；
        ActionOnSpecifiedItems( headName, specifiedContent, actionType );
//        } while (ClickNextButtonDynamically());
        //点击下一页

    }

    private void DeleteItems(List<WebElement> is) throws InterruptedException, IOException {
        //点击删除之前，开始获取状态
        //点击删除
        is.get( is.size() - 1 ).click();
        BrowserFactory.MonitorResponseStart();
        //选择yes
        Yes();
//        Thread.sleep(2000);
        //点击删除之后，获取状态
        BrowserFactory.MMonitorResponseEnd();
//        System.out.println("删除加载完成。");
    }


    //检查页面中是否有许哟啊删除的item,如果有,返回true
    private boolean CheckItem(String specifiedContent) {
        List<WebElement> rows = Table.findElement( By.tagName( "tbody" ) ).findElements( By.tagName( "tr" ) );
        for (WebElement row : rows) {
            List<WebElement> cols = row.findElements( By.tagName( "td" ) );
            //if head == specifiedContent
            if (cols.get( getLocationHead() ).getText().equals( specifiedContent )) {
                return true;
            }
        }
        return false;
    }

    //一条一条删除，逻辑有些问题
    private void ActionOnSpecifiedItems(String headName, String specifiedContent, String actionType) throws IOException, InterruptedException {
        CustomWait.WaitForElements( "//*[@id=\"listing-management-section\"]/div[2]/div[1]/table" );
        List<WebElement> tHeads = Table.findElement( By.tagName( "thead" ) ).findElement( By.tagName( "tr" ) ).findElements( By.tagName( "th" ) );
        for (int i = 0; i < tHeads.size(); i++) {
            if (tHeads.get( i ).getText().trim().equals( headName )) {
                //记下列号
                setLocationHead( i );
                break;
            }
        }
        //每次删除完了以后，重新搜索该页面，如果不存在，点击下一页

        //*****************************
        //先检查页面的情况，在点击下一页按钮；
        //重新判断时候有需要删除的元素

        //先检车页面中是否有需要删除的数据,如果没有,进入下一页,如果有,进行删除, 然后再此检查,如此反复.
        //******************************逻辑有些问题,还需要进行修改.....
        do while (CheckItem( specifiedContent )) {
            List<WebElement> rows = Table.findElement( By.tagName( "tbody" ) ).findElements( By.tagName( "tr" ) );
            for (WebElement row : rows) {
                List<WebElement> cols = row.findElements( By.tagName( "td" ) );
                //if head == specifiedContent
                if (cols.get( getLocationHead() ).getText().equals( specifiedContent )) {
                    //Click delete. now only delete take care. if you want to use Eye and Edit function, please implement them.
                    List<WebElement> is = cols.get( cols.size() - 1 ).findElements( By.tagName( "i" ) );
                    switch (actionType) {
                        case "DELETE":
                            DeleteItems( is );
                            break;
                        //EYE and EDIT can be implement here!
                        default:
                            break;
                    }
                }
            }
        }
        while (ClickNextButtonDynamically());


        //删除页面中的所有符合条件的以后，则点击下一页。
        //*********************************

//        List<WebElement> rows = Table.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
//        for (WebElement row : rows) {
//            List<WebElement> cols = row.findElements(By.tagName("td"));
//            //if head == specifiedContent
//            if (cols.get(getLocationHead()).getText().equals(specifiedContent)) {
//                //Click delete. now only delete take care. if you want to use Eye and Edit function, please implement them.
//                List<WebElement> is = cols.get(cols.size() - 1).findElements(By.tagName("i"));
//                switch (actionType) {
//                    case "DELETE":
//                        DeleteItems(is);
//                        break;
////                    case "EYE":
////                        /**
////                         *
////                         */
////                        break;
////                    case "EDIT":
////                        /**
////                         *
////                         */
////                        break;
//                    default:
//                        break;
//                }
//            }
//        }

    }

}

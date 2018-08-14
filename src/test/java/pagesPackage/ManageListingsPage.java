package pagesPackage;

import com.aventstack.extentreports.Status;
import helperPackage.BrowserFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import testPackage.BaseClass;
import utilityPackage.CustomWait;

import java.io.IOException;
import java.util.List;

public class ManageListingsPage extends BasePage {

    //Initial Page Number used for Log
    private int pageNumber = 1;
    private static int locationHead;

    public ManageListingsPage() {
        PageFactory.initElements( BrowserFactory.driver, this );
    }

    //Table define
    @FindBy(how = How.XPATH, using = "//*[@id=\"listing-management-section\"]/div[2]/div[1]/table")
    @CacheLookup
    private WebElement Table;
    //Pagination define
    @FindBy(how = How.XPATH, using = "//*[@id=\"listing-management-section\"]/div[2]/div[1]/div")
    @CacheLookup
    private WebElement Pagination;
    //Yes button define
    @FindBy(how = How.XPATH, using = "//button[@class='ui icon positive right labeled button']")
    @CacheLookup
    private WebElement BtnYes;

    //get the location of specified table head
    private static int getLocationHead() {
        return locationHead;
    }

    //set the location of specified table head
    private static void setLocationHead(int locationHead) {
        ManageListingsPage.locationHead = locationHead;
    }

    //Click Previous button
    private boolean ClickPreviousButtonDynamically() throws InterruptedException, IOException {
        //wait for pagination available
        CustomWait.WaitForElements( "//*[@id=\"listing-management-section\"]/div[2]/div[1]/div" );
        //get all the elements of pagination
        List<WebElement> webElementList = Pagination.findElements( By.tagName( "button" ) );
        //if the next button is clickable,the click previous button
        if ((webElementList.get( 0 ).isDisplayed()) && (webElementList.get( 0 ).isEnabled())) {
            //before click previous button, initialize Har to start to get network status
            BrowserFactory.MonitorResponseStart();
            //click previous
            webElementList.get( 0 ).click();
            //log
            BaseClass.testLog.log( Status.INFO, "Click Previous button to goto precious page !" );
            //check network status to make sure the table is loaded after click next button
            BrowserFactory.MMonitorResponseEnd();
            //return true
            return true;
        } else {
            BaseClass.testLog.log( Status.INFO, "Previous button is not available !" );
            //return true
            return false;
        }
    }

    //Click Yes button
    private void Yes() {
        //wait for the element show
        CustomWait.WaitForElements( "/html[1]/body[1]/div[2]/div[1]" );
        //click button
        BtnYes.click();
        //wait for table show
        CustomWait.WaitForElements( "//*[@id=\"listing-management-section\"]/div[2]/div[1]/table" );
    }

    //Click Next Page button
    private boolean ClickNextButtonDynamically() throws IOException, InterruptedException {
        //wait for pagination available
        CustomWait.WaitForElements( "//*[@id=\"listing-management-section\"]/div[2]/div[1]/div" );
        //get all the elements of pagination
        List<WebElement> webElementList = Pagination.findElements( By.tagName( "button" ) );
        //if the next button is clickable,the click Next button
        if ((webElementList.get( webElementList.size() - 1 ).isDisplayed()) && (webElementList.get( webElementList.size() - 1 ).isEnabled())) {
            //before click Next button, initialize Har to start to get network status
            BrowserFactory.MonitorResponseStart();
            //click next
            webElementList.get( webElementList.size() - 1 ).click();
            //log
            BaseClass.testLog.log( Status.INFO, "Click Next button to goto next page !" );
            //check network status to make sure the table is loaded after click next button
            BrowserFactory.MMonitorResponseEnd();
            //return true
            return true;
        } else {
            BaseClass.testLog.log( Status.INFO, "Next button is not available !" );
            //return true
            return false;
        }
    }

    //Execute delete
    private void DeleteItems(List<WebElement> is) throws InterruptedException, IOException {
        //click delete button
        is.get( is.size() - 1 ).click();
        //begin to capture network package
        BrowserFactory.MonitorResponseStart();
        //click Yes button
        Yes();
        //log
        BaseClass.testLog.log( Status.INFO, "Click Yes to delete" );
        //check the network status
        BrowserFactory.MMonitorResponseEnd();
    }

    //check if the page table has the items to be delete
    private boolean CheckIfItemExist(String specifiedContent) {
        //get rows
        List<WebElement> rows = Table.findElement( By.tagName( "tbody" ) ).findElements( By.tagName( "tr" ) );
        //loop all rows to check
        for (WebElement row : rows) {
            List<WebElement> cols = row.findElements( By.tagName( "td" ) );
            //if there is items to be delete, return true,or return false
            if (cols.get( getLocationHead() ).getText().equals( specifiedContent )) {
                //log
                BaseClass.testLog.log( Status.INFO, "Specified content exists, keep deleting on this page !" );
                return true;
            }
        }
        //log
        BaseClass.testLog.log( Status.INFO, "Specified content does not exist, move to next page !" );
        return false;
    }

    //go through all pages to delete items
    private void SearchOnePageAndDelete(String specifiedContent, String actionType) throws IOException, InterruptedException {
        //get rows
        List<WebElement> rows = Table.findElement( By.tagName( "tbody" ) ).findElements( By.tagName( "tr" ) );
        //loop all the rows
        for (int i = 0; i < rows.size(); i++) {
            List<WebElement> cols = rows.get( i ).findElements( By.tagName( "td" ) );
            //if the specified item checked,
            if (cols.get( getLocationHead() ).getText().equals( specifiedContent )) {
                //Click delete. now only delete take care. if you want to use Eye and Edit function, please implement them.
                List<WebElement> is = cols.get( cols.size() - 1 ).findElements( By.tagName( "i" ) );
                switch (actionType) {
                    case "DELETE":
                        DeleteItems( is );
                        //let i=0 to loop from the 1st rows, because once you delete one item, the table reloaded
                        i = 0;
                        break;
                    //EYE and EDIT can be implement here!
                    default:
                        break;
                }
            }
        }

    }

    //search all pages and delete
    public void SearchAllPagesTablesAndAct(String headName, String specifiedContent, String actionType) throws IOException, InterruptedException {
        //wait for table
        try {
            CustomWait.WaitForElements( "//*[@id=\"listing-management-section\"]/div[2]/div[1]/table" );
            BaseClass.testLog.log( Status.PASS, "Move to ListingManagement Page !" );
        } catch (Exception e) {
            BaseClass.testLog.log( Status.FAIL, e.getMessage() );
        }

        //get table head
        List<WebElement> tHeads = Table.findElement( By.tagName( "thead" ) ).findElement( By.tagName( "tr" ) ).findElements( By.tagName( "th" ) );
        //get the location of head name
        for (int i = 0; i < tHeads.size(); i++) {
            if (tHeads.get( i ).getText().trim().equals( headName )) {
                //set the location of head name
                setLocationHead( i );
                break;
            }
        }
        //start to check the first page if there are specified items available
        do {
            //if there is wanted item
            while (CheckIfItemExist( specifiedContent )) {
                //delete, until the page has no wanted item.
                SearchOnePageAndDelete( specifiedContent, actionType );
                BaseClass.testLog.log( Status.INFO, "Delete " + specifiedContent + " at " + pageNumber + " page" );
            }
            //if current page has no specifiedContent, page number ++
            BaseClass.testLog.log( Status.INFO, "Can not find " + specifiedContent + " at " + pageNumber + " page" );
            pageNumber++;
        }
        //if thr next button available, move to next page.
        while (ClickNextButtonDynamically());
    }

    //AssertionOfDelete
    public boolean AssertionOfDelete(String specifiedContent) throws IOException, InterruptedException {
        //start to check the first page if there are specified items available
        BaseClass.testLog.log( Status.INFO,"Loop Back to assert !" );
        do {
            //if there is wanted item
            while (CheckIfItemExist( specifiedContent )) {
                BaseClass.testLog.log( Status.FAIL, specifiedContent + " exists at " + pageNumber + " page !" );
                return false;
            }
            //if current page has no specifiedContent, page number --
            BaseClass.testLog.log( Status.INFO, specifiedContent + " has been deleted at " + pageNumber + " page !" );
            pageNumber--;
        }
        //if thr next button available, move to next page.
        while (ClickPreviousButtonDynamically());
        BaseClass.testLog.log( Status.INFO, specifiedContent + " has been all deleted !" );
        return true;
    }
}


package reusable;

/**
 * @author: Kedarnath Lute
 */

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.Status;

import utils.DriverManager;
import utils.ExtentManager;

public class General {

    //Customized sendkeys method-> To log sendkeys message for every occ.
    public void sendKeys_custom(WebElement element, String fieldName, String valueToBeSent) {
        try {
            element.sendKeys(valueToBeSent);
            //log success message in exgent report
            ExtentManager.getExtentTest().log(Status.PASS, fieldName+"==> Ented value as: "+valueToBeSent);
        } catch (Exception e) {
            //log failure in extent
            ExtentManager.getExtentTest().log(Status.FAIL, "Value enter in field: "+fieldName + " is failed due to exception: "+e);
        }
    }


    //custom click method to log evey click action in to extent report
    public void click_custom(WebElement element, String fieldName) {
        try {
            element.click();
            //log success message in extent report
            ExtentManager.getExtentTest().log(Status.PASS, fieldName+"==> Clicked Successfully! ");
        } catch (Exception e) {
            //log failure in extent
            ExtentManager.getExtentTest().log(Status.FAIL, "Unable to click on field: " +fieldName +" due to exception: "+e);
        }
    }


    //clear data from field
    public void clear_custom(WebElement element,String fieldName) {
        try {
            element.clear();
            Thread.sleep(250);
            ExtentManager.getExtentTest().log(Status.PASS, fieldName+"==> Data Cleared Successfully! ");
        } catch (Exception e) {
            ExtentManager.getExtentTest().log(Status.FAIL, "Unable to clear Data on field: " +fieldName +" due to exception: "+e);

        }
    }

    //custom mouseHover
    public void moveToElement_custom(WebElement element,String fieldName){
        try{
            //JavascriptExecutor executor = (JavascriptExecutor) DriverManager.getDriver();
            //executor.executeScript("arguments[0].scrollIntoView(true);", element);
            Actions actions = new Actions(DriverManager.getDriver());
            actions.moveToElement(element).build().perform();
            ExtentManager.getExtentTest().log(Status.PASS, fieldName+"==> Mouse hovered Successfully! ");
            Thread.sleep(1000);
        }catch(Exception e){
            ExtentManager.getExtentTest().log(Status.FAIL, "Unable to hover mouse on field: " +fieldName +" due to exception: "+e);

        }
    }


    //check if element is Present
    public boolean isElementPresent_custom(WebElement element,String fieldName){
        boolean flag = false;
        try {
            flag = element.isDisplayed();
            ExtentManager.getExtentTest().log(Status.PASS, fieldName+"==> Presence of field is: "+ flag);
            return flag;
        } catch (Exception e) {
            ExtentManager.getExtentTest().log(Status.FAIL, "Checking for presence of field: " +fieldName +" not tested due to exception: "+e);
            return flag;
        }
    }


    //Select dropdown value value by value
    public void selectDropDownByValue_custom(WebElement element, String fieldName, String ddValue) throws Throwable {
        try {
            Select s = new Select(element);
            s.selectByValue(ddValue);
            ExtentManager.getExtentTest().log(Status.PASS, fieldName+"==> Dropdown Value Selected by visible text: "+ ddValue);
        } catch (Exception e) {
            ExtentManager.getExtentTest().log(Status.FAIL, "Dropdown value not selected for field: " +fieldName +"  due to exception: "+e);
        }
    }


    //Get text from webelement
    public String getText_custom(WebElement element, String fieldName) {
        String text = "";
        try {
            text = element.getText();
            ExtentManager.getExtentTest().log(Status.PASS, fieldName+"==> Text retried is: "+ text);
            return text;
        } catch (Exception e) {
            ExtentManager.getExtentTest().log(Status.FAIL, fieldName+"==> Text not retried due to exception: "+ e);

        }
        return text;
    }

}
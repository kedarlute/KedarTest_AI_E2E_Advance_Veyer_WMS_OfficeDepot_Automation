package tests;

/**
 * @author: Kedarnath Lute
 */

import base.BaseTest;
import com.aventstack.extentreports.Status;
import constants.Constants;
import listeners.RetryAnalyzer;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.aventstack.extentreports.MediaEntityBuilder;
import utils.DriverManager;

public class LoginTest extends BaseTest {


        @Test(priority = 1)
        public void loginTest() {
        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("password123");
        driver.findElement(By.id("loginBtn")).click();

        WebElement dashboard = driver.findElement(By.id("welcomeMsg"));
        Assert.assertTrue(dashboard.isDisplayed(), "Login failed!");
        }
        
        @Test(retryAnalyzer = RetryAnalyzer.class)
        public void testOfficeDepotTitle() {
            test = extent.createTest("VEYER by Office Depot Title Test");
            //WebDriver driver = DriverManager.getDriver();
            driver.get(Constants.BASE_URL);
            String title = driver.getTitle();
            test.info("Title is: " + title);
            test.log(Status.INFO, "Launching browser");


         Assert.assertEquals(driver.getTitle(),"VEYER by Office Depot - 3PL & eCommerce Fulfillment Services");
            test.log(Status.PASS, "Title verified successfully");

            test.pass("Step Passed",
                    MediaEntityBuilder.createScreenCaptureFromPath("screenshot.png").build());


         
        }
    }



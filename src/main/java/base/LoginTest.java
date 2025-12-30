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
import utils.DriverManager;

public class LoginTest extends BaseTest {

   WebDriver driver;

        @BeforeClass
        public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        
        }
        
        @Test(priority = 0)
        public void login_with_AIFindElement() {
       AIFindElement ai = new AIFindElement(driver);
       AILocator usernameLocator = new AILocator(
            By.id("username"),
            List.of(
                By.name("user"),
                SemanticXPath.inputByLabel("Username")
            )
       );
       AILocator passwordLocator = new AILocator(
            By.id("password"),
            List.of(
                By.name("pass"),
                SemanticXPath.inputByLabel("Password")
            )
      );
     AILocator loginbtnLocator = new AILocator(
            By.id("loginBtn"),
            List.of(
                By.name("login"),
                SemanticXPath.inputByLabel("Login")
            )
      );
       AILocator welcomeMsgLocator = new AILocator(
            By.id("welcomeMsg"),
            List.of(
                By.name("welcome"),
                SemanticXPath.inputByLabel("Welcome")
            )
      );

      ai.find("username", usernameLocator).sendKeys("admin");
      ai.find("password", passwordLocator).sendKeys("password123");
      ai.find("Login", loginbtnLocator).click();
      WebElement dashboard = ai.find("Welcome", welcomeMsgLocator);
      Assert.assertTrue(dashboard.isDisplayed(), "Login Passed!");
   }
        
        @Test(retryAnalyzer = RetryAnalyzer.class,dependsOnMethods = "loginTest"))
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

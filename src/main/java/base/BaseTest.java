package base;

/**
 * @author: Kedarnath Lute
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import utils.DriverManager;

import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {
   // protected WebDriver driver;
   // private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected ExtentReports extent;
    protected ExtentTest test;

    @BeforeSuite
    public void setupExtent() {
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
        //extent = ExtentManager.getInstance();
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Author", "Kedarnath Lute");

    }

    @BeforeMethod
    public void setUp() throws MalformedURLException {

            WebDriverManager.chromedriver().setup();
            WebDriver driver = new ChromeDriver();
            DriverManager.setDriver(driver); // Set to ThreadLocal
        // Selenium Grid on Azure Cloud
        //ChromeOptions options = new ChromeOptions();
        //driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
        //driver.manage().window().maximize();

    }

    @AfterMethod
    public void tearDown() {
        DriverManager.getDriver().quit(); // Quit the thread’s driver
        DriverManager.unload();           // Remove from ThreadLocal
    }

    @AfterSuite
    public void tearDownReport() {
        extent.flush();
    }
}

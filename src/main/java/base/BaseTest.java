package base;

import drivers.DriverFactory;
import drivers.DriverManager;
import drivers.DriverManagerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import reports.ExtentReportManager;

public class BaseTest {
    protected final Logger LOG = LogManager.getLogger(getClass());
    protected WebDriver driver;

    protected static final String BASE_URL = "https://demo1.cybersoft.edu.vn/";


    @BeforeSuite
    public void beforeSuite() {
        LOG.info("beforeSuite - Init Extent Report");
        ExtentReportManager.initializeExtentReports();
    }

    @BeforeClass
    public void beforeClass() {
        DriverManager driverManager = DriverManagerFactory.getDriverManager("chrome");
        driver = driverManager.getDriver();
        driver.manage().window().maximize();
        DriverFactory.setDriver(driver);
    }

    @BeforeMethod
    public void beforeMethod(ITestResult result) {
        ExtentReportManager.createTest(result.getMethod().getMethodName());

        driver.get(BASE_URL);

    }

    @AfterMethod
    public void afterMethod(ITestResult result) {
        LOG.info("Test completed");
        if (result.getStatus() == ITestResult.FAILURE) {
            ExtentReportManager.captureScreenshot(driver, result.getMethod().getMethodName());
            ExtentReportManager.fail(result.getThrowable().toString());
        }
    }

    @AfterClass
    public void afterClass() {
        if (driver != null)
            driver.quit();
    }

    @AfterSuite
    public void afterSuite() {
        ExtentReportManager.flushReports();
    }


}

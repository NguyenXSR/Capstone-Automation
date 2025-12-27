package testcases.homepage;

import base.BaseTest;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.components.MainHomeSection;
import reports.ExtentReportManager;

@Listeners(TestListener.class)
public class HeaderNavigationTest extends BaseTest {

    private HomePage homePage;
    private MainHomeSection homeSection;

    //khai báo Page object
    @BeforeMethod
    public void setUpPages() {
        homePage = new HomePage();
        homeSection = new MainHomeSection();
    }

    @Test
    public void TC01_Verify_Logo_Displayed() {

        ExtentReportManager.info("Check logo is visible");
        LOG.info("Check logo is visible");

        homePage.getTopBarNavigation().isLogoVisible();
        Assert.assertTrue(homePage.getTopBarNavigation().isLogoVisible(), "Logo is not visible");
    }

    @Test
    public void TC02_Verify_Logo_Redirects_Homepage() {
        ExtentReportManager.info("Check logo redirect homepage when in different page");
        LOG.info("Check logo redirect homepage when in different page");

        homePage.getTopBarNavigation().navigateRegisterPage();
        homePage.getTopBarNavigation().clickLogo();
        Assert.assertEquals(driver.getCurrentUrl(), "https://demo1.cybersoft.edu.vn/");
    }
    @Test
    public void TC03_Verify_Showtimes_Scroll() {
        ExtentReportManager.info("Check page scroll to Showtimes section");
        LOG.info("Check page scroll to SHowtimes section");

        homePage.getTopBarNavigation().clickShowtimes();
        Assert.assertTrue(homeSection.isShowtimesVisible(), "Showtimes section is not visible after click");
    }

    @Test
    public void TC04_Verify_Cinemas_Scroll() {
        ExtentReportManager.info("Check page scroll to Cinemas section");
        LOG.info("Check page scroll to Cinemas section");
        homePage.getTopBarNavigation().clickCinemas();
        Assert.assertTrue(homeSection.isCinemasVisible(), "Cinemas section is not visible after click");
    }

    @Test
    public void TC05_Verify_News_Scroll() {
        ExtentReportManager.info("Check page scroll to Cinemas section");
        LOG.info("Check page scroll to Cinemas section");
        homePage.getTopBarNavigation().clickNews();
        Assert.assertTrue(homeSection.isNewsVisible(), "News section is not visible after click");
    }

    @Test
    public void TC06_Verify_MobileApp_Scroll() {
        ExtentReportManager.info("Check page scroll to Cinemas section");
        LOG.info("Check page scroll to Cinemas section");
        homePage.getTopBarNavigation().clickMobileApp();
        Assert.assertTrue(homeSection.isMobileAppVisible(), "Mobile App section is not visible after click");
    }

    @Test
    public void TC07_Verify_Login_Redirect() {
        ExtentReportManager.info("Check login page redirect");
        LOG.info("Check login page redirect");
        homePage.getTopBarNavigation().navigateLoginPage();
        Assert.assertTrue(driver.getCurrentUrl().contains("/sign-in"), "Not redirected to Login page");
    }

    @Test
    public void TC08_Verify_Register_Redirect() {
        ExtentReportManager.info("Check register page redirect");
        LOG.info("Check register page redirect");
        homePage.getTopBarNavigation().navigateRegisterPage();
        Assert.assertTrue(driver.getCurrentUrl().contains("/sign-up"), "Not redirected to Register page");
    }



}

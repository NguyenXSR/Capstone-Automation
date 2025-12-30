package testcases.login;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import reports.ExtentReportManager;
import java.time.Duration;


@Listeners(TestListener.class)
public class TC02_LoginTest extends BaseTest {

    @Test(description = "Login test")
    public void testLogin() {
        LoginPage loginPage = new LoginPage();
        HomePage homePage = new HomePage();


        //Step 1: click dang nhap link on the top right
        ExtentReportManager.info("Step 1: Click 'Dang Nhap' link on the top right");
        LOG.info("Step 1: Click 'Dang Nhap' link on the top right");
        homePage.getTopBarNavigation().navigateLoginPage();

        //Step2: enter account to login
        ExtentReportManager.info("Step 2: Enter account to login");
        LOG.info("Step 2: Enter account to login");
        String account = "a68cf217-d33b-4132-b180-864697ac8427"; //exist account
        loginPage.enterAccount(account);

        //Step 3: Enter password to login
        ExtentReportManager.info("Step 3: Enter password to login");
        LOG.info("Step 3: Enter password to login");
        loginPage.enterPassword("Test123456@");

        //Step 4: Click Login
        ExtentReportManager.info("Step 4: Click Login");
        LOG.info("Step 4: Click Login");
        loginPage.clickLogin();

        //VP1: 'Đăng nhập thành công' message displays
        ExtentReportManager.info("VP1: 'Đăng nhập thành công' message displays");
        LOG.info("VP1: 'Đăng nhập thành công' message displays");
        String actualLoginMsg = loginPage.getLoginMsg();
        Assert.assertEquals(actualLoginMsg, "Đăng nhập thành công", "Login message failed!");

        //VP2: Check 'Dang Xuat' button link displays on the top right
        ExtentReportManager.info("VP2: Check 'Dang Xuat' button link displays on the top right");
        LOG.info("VP2: Check 'Dang Xuat' button link displays on the top right");
        boolean isLogoutLinkDisplayed = homePage.getTopBarNavigation().isLogoutLinkDisplayed();
        Assert.assertTrue(isLogoutLinkDisplayed, "'Dang Xuat' link is not displayed!");

        //VP3: Check user profile name displays
        ExtentReportManager.info("VP3: Check user profile name displays");
        LOG.info("VP3: Check user profile name displays");
        String expectedProfileName = "Nguyen Van A";
        String actualProfileName = homePage.getTopBarNavigation().getUserProfileName();
        Assert.assertEquals(actualProfileName, expectedProfileName, "User profile name is incorrect!");

        ExtentReportManager.pass("PASSED");
    }
}

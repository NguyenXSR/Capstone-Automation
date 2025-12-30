package testcases.login;

import base.BaseTest;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import reports.ExtentReportManager;


@Listeners(TestListener.class)
public class TC02_LoginTest extends BaseTest {

    private HomePage homePage;
    private LoginPage loginPage;

    //test data
    private final String VALID_USER = "tester01";
    private final String VALID_PASS = "123456";
    private final String INVALID_USER = "wrong";
    private final String INVALID_PASS = "wrong";


    @BeforeClass
    public void setUpPages() {
        homePage = new HomePage();
        loginPage = new LoginPage();

    }

    @BeforeMethod
    public void navigateToLoginPage() {
        //Step 1: click dang nhap link on the top right
        ExtentReportManager.info("Step 1: Click 'Dang Nhap' link on the top right");
        LOG.info("Step 1: Click 'Dang Nhap' link on the top right");
        homePage.getTopBarNavigation().navigateLoginPage();
    }

    @Test(description = "Login test")
    public void testLogin() {
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


    // test leave fields username and password blank
    @Test(description = "Login with blank username and password")
    public void testLoginWithBlankFields() {

        //Step 2: leave username and password blank and click login
        ExtentReportManager.info("Step 2: Leave username and password blank and click login");
        LOG.info("Step 2: Leave username and password blank and click login");
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.getUsernameRequiredError().contains("Đây là trường bắt buộc !"),
                "Username required error not shown");
        Assert.assertTrue(loginPage.getPasswordRequiredError().contains("Đây là trường bắt buộc !"),
                "Password required error not shown");
    }

    @Test(description = "Login with only username filled")
    public void TC_UsernameOnly_ShowsPasswordRequired() {
        loginPage.enterAccount(VALID_USER);
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.getPasswordRequiredError().contains("Đây là trường bắt buộc !"),
                "Password required error not shown");
    }

    @Test
    public void TC04_InvalidCredentials_ShowsError() {
        loginPage.enterAccount(INVALID_USER);
        loginPage.enterPassword(INVALID_PASS);
        loginPage.clickLogin();

        String err = loginPage.getInvalidCredsError();
        Assert.assertTrue(err.contains("Tài khoản hoặc mật khẩu không đúng!"),
                "Invalid credentials error not shown. Actual: " + err);
    }

    @Test(description = "Toggle password visibility")
    public void TC06_TogglePasswordVisibility() {
        loginPage.enterPassword("StrongPass123");

        Assert.assertEquals(loginPage.getPasswordType(), "password", "Password should be masked initially");
        loginPage.togglePasswordVisibility();
        Assert.assertEquals(loginPage.getPasswordType(), "text", "Password should be visible after toggle");
    }

    @Test(description = "Remember account persists username after reload")
    public void TC07_RememberAccount_PersistsUsername_AfterReload() {
        loginPage.checkRememberAccount();
        loginPage.enterAccount(VALID_USER);
        loginPage.enterPassword(VALID_PASS);
        loginPage.clickLogin();

        // redirect to homepage and then back to login page
        driver.get("https://demo1.cybersoft.edu.vn/sign-in");

        // username should be remembered
        Assert.assertEquals(loginPage.getUsernameValue(), VALID_USER, "Username was not remembered");
    }

    @Test(description = "Press Enter key to submit login form")
    public void TC08_PressEnterKey_LoginProceeds() {
        loginPage.enterAccount(VALID_USER);
        loginPage.enterPassword(VALID_PASS);
        loginPage.pressEnterToLogin();

        Assert.assertEquals(driver.getCurrentUrl(), "https://demo1.cybersoft.edu.vn/",
                "Enter key did not submit login");
    }

}




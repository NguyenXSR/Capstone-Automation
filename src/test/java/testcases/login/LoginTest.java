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
public class LoginTest extends BaseTest {

    private HomePage homePage;
    private LoginPage loginPage;

    //test data
    private final String VALID_USER = "tester01";
    private final String VALID_PASS = "123456";
    private final String INVALID_USER = "invalid123";
    private final String INVALID_PASS = "invalid123";
    String expectedProfileName = "John S";



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


    @Test (description = "Login with invalid data")
    public void TC_InvalidCredentials_ShowsError() {
        ExtentReportManager.info("Step 2: enter account with invalid data");
        LOG.info("Step 2: enter account with invalid data");
        loginPage.enterAccount(INVALID_USER);

        ExtentReportManager.info("Step 3: enter password with invalid data");
        LOG.info("Step 3: enter password with invalid data");
        loginPage.enterPassword(INVALID_PASS);

        ExtentReportManager.info("Step 4: Click Login");
        LOG.info("Step 4: Click Login");
        loginPage.clickLogin();

        ExtentReportManager.info("VP: Verify invalid credentials error message is shown");
        LOG.info("VP: Verify invalid credentials error message is shown");
        String err = loginPage.getInvalidCredsError();
        Assert.assertTrue(err.contains("Tài khoản hoặc mật khẩu không đúng!"),
                "Invalid credentials error not shown. Actual: " + err);
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

    @Test(description = "Remember account persists username after reload")
    public void TC_RememberAccount_PersistsUsername_AfterReload() {
        loginPage.enterAccount(VALID_USER);
        loginPage.enterPassword(VALID_PASS);
        loginPage.checkRememberAccount();
        loginPage.clickLogin();

        // redirect to homepage and then back to login page
        homePage.getTopBarNavigation().navigateLoginPage();

        // username should be remembered
        Assert.assertEquals(loginPage.getUsernameValue(), VALID_USER, "Username was not remembered");
    }


    @Test(description = "Toggle password visibility")
    public void TC06_TogglePasswordVisibility() {
        loginPage.enterPassword("StrongPass123");

        Assert.assertEquals(loginPage.getPasswordType(), "password", "Password should be masked initially");
        loginPage.togglePasswordVisibility();
        Assert.assertEquals(loginPage.getPasswordType(), "text", "Password should be visible after toggle");
    }

    @Test(description = "Press Enter key to submit login form")
    public void TC_PressEnterKey_LoginProceeds() {
        ExtentReportManager.info("Step 2: enter account to login");
        LOG.info("Step 2: enter account to login");
        loginPage.enterAccount(VALID_USER);

        ExtentReportManager.info("Step 3: enter password to login");
        LOG.info("Step 3: enter password to login");
        loginPage.enterPassword(VALID_PASS);
        loginPage.pressEnterToLogin();

        ExtentReportManager.info("VP1: 'Đăng nhập thành công' message displays when pressing Enter key");
        LOG.info("VP1: 'Đăng nhập thành công' message displays when pressing Enter key");
        String actualLoginMsg = loginPage.getLoginMsg();
        Assert.assertEquals(actualLoginMsg, "Đăng nhập thành công", "Login message failed!");

        loginPage.waitLoginMsgDisappear();
        ExtentReportManager.info("VP2: Verify redirected to homepage when pressing Enter key");
        LOG.info("VP2: Verify redirected to homepage when pressing Enter key");
        Assert.assertEquals(driver.getCurrentUrl(), "https://demo1.cybersoft.edu.vn/",
                "Enter key did not submit login");
    }

}




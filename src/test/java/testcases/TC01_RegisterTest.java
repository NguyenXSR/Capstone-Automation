package testcases;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.LoginPage;
import pages.RegisterPage;
import reports.ExtentReportManager;

import java.time.Duration;
import java.util.UUID;

@Listeners(TestListener.class)
public class TC01_RegisterTest extends BaseTest {

    @Test(description = "Register Test ")
    public void testRegister() {
        RegisterPage registerPage = new RegisterPage();
        LoginPage loginPage = new LoginPage();
        HomePage homePage = new HomePage();


        driver.get("https://demo1.cybersoft.edu.vn/"); //navigate 1 site

        WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(30));


        //Step 1: Click link "Đăng Ký"
        ExtentReportManager.info("Step 1: Click link 'Đăng Ký'");
        LOG.info("Step 1: Click link 'Đăng Ký'");
        homePage.getTopBarNavigation().navigateRegisterPage();

        //Step 2: Enter account
        ExtentReportManager.info("Step 2: Enter account");
        LOG.info("Step 2: Enter account");
        String account = UUID.randomUUID().toString();
        System.out.println("account = " + account);
        registerPage.enterAccount(account);

        //Step 3: Enter password
        ExtentReportManager.info("Step 3: Enter password");
        LOG.info("Step 3: Enter password");
        registerPage.enterPassword("Test123456@");

        //Step 4: Enter confirm password
        ExtentReportManager.info("Step 4: Enter confirm password");
        LOG.info("Step 4: Enter confirm password");
        registerPage.enterConfirmPassword("Test123456@");

        //Step 5: Enter name
        ExtentReportManager.info("Step 5: Enter name");
        LOG.info("Step 5: Enter name");
        registerPage.enterName("John A");

        //Step 6: Enter email
        ExtentReportManager.info("Step 6: Enter email");
        LOG.info("Step 6: Enter email");
        String email = account + "@example.com";
        registerPage.enterEmail(email);

        //Step 7: Click register button
        ExtentReportManager.info("Step 7: Click register button");
        LOG.info("Step 7: Click register button");
        registerPage.clickRegister();

        //Step 8: Verify user register successfully
        ExtentReportManager.info("Step 8: Verify user register successfully");
        LOG.info("Step 8: Verify user register successfully");
        //VP1: 'Đăng ký thành công' message display
        ExtentReportManager.info("VP1: 'Đăng ký thành công' message display");
        LOG.info("VP1: 'Đăng ký thành công' message display");
        String actualRegisterMsg = registerPage.getRegisterMessage();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(actualRegisterMsg, "Đăng ký thành công", "Register message failed!");

        registerPage.waitRegisterMessageDisappear();

        //VP2: Check register page still displays
        ExtentReportManager.info("VP2: Check register page still displays");
        LOG.info("VP2: Check register page still displays");
        String url = driver.getCurrentUrl();
        softAssert.assertEquals(url, "https://demo1.cybersoft.edu.vn/sign-up", "Register page does not display!");
        softAssert.assertAll(); // buoc nay moi kiem tra tong ket

        //VP3: Check new account logins successfully
        ExtentReportManager.info("VP3: Check new account logins successfully");
        LOG.info("VP3: Check new account logins successfully");
        //Click on Login button
        ExtentReportManager.info("Click on Login button");
        LOG.info("Click on Login button");
        registerPage.getTopBarNavigation().navigateLoginPage();

        //Enter account to login
        ExtentReportManager.info("Enter account to login");
        LOG.info("Enter account to login");
        loginPage.enterAccount(account);


        //Enter password to login
        ExtentReportManager.info("Enter password to login");
        LOG.info("Enter password to login");
        loginPage.enterPassword("Test123456@");

        //Click Login
        ExtentReportManager.info("Click Login");
        LOG.info("Click Login");
        loginPage.clickLogin();

        //VP3.1: 'Đăng nhập thành công' message displays
        ExtentReportManager.info("VP3.1: 'Đăng nhập thành công' message displays");
        LOG.info("VP3.1: 'Đăng nhập thành công' message displays");
        String actualLoginMsg = loginPage.getLoginMsg();
        Assert.assertEquals(actualLoginMsg, "Đăng nhập thành công", "Login message failed!");

        ExtentReportManager.pass("PASSED");
    }

}



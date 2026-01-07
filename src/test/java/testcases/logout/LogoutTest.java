package testcases.logout;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.components.LogOutConfirmModal;
import reports.ExtentReportManager;

import java.time.Duration;

@Listeners(TestListener.class)
public class LogoutTest extends BaseTest {


    private HomePage homePage;
    private LogOutConfirmModal confirmModal;
    private LoginPage loginPage;

    @BeforeMethod
    public void setUpPages() {
        homePage = new HomePage();
        confirmModal = new LogOutConfirmModal();
        loginPage = new LoginPage();

    }

    private void loginAsValidUser() {
        homePage.getTopBarNavigation().navigateLoginPage();
        loginPage.enterAccount("tester_1767361653915");
        loginPage.enterPassword("StrongPass123");
        loginPage.clickLogin();
        String actualLoginMsg = loginPage.getLoginMsg();
        Assert.assertEquals(actualLoginMsg, "Đăng nhập thành công", "Login message failed!");

    }


    @Test(description = "Logout modal - Cancel should stay logged in")
    public void TC_Logout_Modal_Cancel_Should_Stay_Logged_In() {

        ExtentReportManager.info("Login with valid credentials");
        LOG.info("Login with valid credentials");
        loginAsValidUser();

        ExtentReportManager.info("Click Đăng Xuất > modal appears");
        LOG.info("Click Đăng Xuất > modal appears");
        homePage.getTopBarNavigation().clickLogoutButton();
        confirmModal.waitLogoutConfirmModalVisible();

        Assert.assertTrue(confirmModal.isVisible(), "Confirmation modal is not visible");
        String msg = confirmModal.getModalText();
        Assert.assertTrue(msg.toLowerCase().contains("đăng xuất") || msg.toLowerCase().contains("logout"),
                "Modal message not about logout. Actual: " + msg);

        ExtentReportManager.info("Click Cancel > modal closes and user remains logged in");
        confirmModal.clickCancelButton();
        confirmModal.waitConfirmModalClosed();

        Assert.assertTrue(homePage.getTopBarNavigation().isLoggedInIndicatorVisible(),
                "User should remain logged in after cancel");
    }


    @Test (description = "Double click Logout should show only one modal", priority = 1)
    public void TC_Double_Click_Logout_Should_Show_Only_One_Modal() {

        ExtentReportManager.info("Login with valid credentials");
        //  loginAsValidUser();

        ExtentReportManager.info("Click Đăng Xuất multiple times quickly");
        homePage.getTopBarNavigation().clickLogoutButton();
        homePage.getTopBarNavigation().clickLogoutButton();

        confirmModal.waitLogoutConfirmModalVisible();

        ExtentReportManager.info("Only one modal should appear");
        LOG.info("Only one modal should appear");
        Assert.assertTrue(confirmModal.isVisible(), "Modal should appear");

        // count elements to make sure only one modal exists
        int modalCount = driver.findElements(org.openqa.selenium.By.cssSelector(".modal, .MuiDialog-root, [role='dialog']")).size();
        Assert.assertEquals(modalCount, 1, "Only one modal should be shown");
    }

    @Test(description = "Check token cleared after logout", priority = 2)
    public void TC_Check_Token_Cleared_After_Logout() {
        //login
        ExtentReportManager.info("Step 1: Login with valid credentials");
        LOG.info("Step 1: Login with valid credentials");
        //  loginAsValidUser();


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // pre-condition
        ExtentReportManager.info("Step 2: Verify currentUser (token) exists in localStorage");
        LOG.info("Step 2: Verify currentUser (token) exists in localStorage");
        wait.until(d -> ((JavascriptExecutor) d)
                .executeScript("return window.localStorage.getItem('currentUser');") != null);

        // pre-condition
        ExtentReportManager.info("Step 3: Verify accessToken exists inside currentUser");
        LOG.info("Step 3: Verify accessToken exists inside currentUser");
        String tokenBefore = (String) ((JavascriptExecutor) driver)
                .executeScript(
                        "const u = JSON.parse(window.localStorage.getItem('currentUser'));"
                                + "return u ? u.accessToken : null;"
                );

        Assert.assertNotNull(
                tokenBefore,
                "Pre-condition failed: accessToken not found in currentUser before logout"
        );

        // logout ok
        homePage.getTopBarNavigation().clickLogoutButton();
        confirmModal.waitLogoutConfirmModalVisible();
        confirmModal.clickOkButton();



        ExtentReportManager.info("Step 4: Verify currentUser is removed from localStorage after logout");
        LOG.info("Step 4: Verify currentUser is removed from localStorage after logout");
        wait.until(d -> ((JavascriptExecutor) d)
                .executeScript("return window.localStorage.getItem('currentUser');") == null);

        String currentUserAfter = (String) ((JavascriptExecutor) driver)
                .executeScript("return window.localStorage.getItem('currentUser');");

        Assert.assertNull(
                currentUserAfter,
                "currentUser should be removed from localStorage after logout"
        );


        ExtentReportManager.info("Step 5: /account should be blocked after logout");
        LOG.info("Step 5: /account should be blocked after logout");
        // strong check: /account   is blocked
        driver.get("https://demo1.cybersoft.edu.vn/account");
        Assert.assertEquals(driver.getCurrentUrl(), "https://demo1.cybersoft.edu.vn/", "After logout, /account should be blocked. Actual: " + driver.getCurrentUrl());
    }

}

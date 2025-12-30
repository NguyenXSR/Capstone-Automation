package testcases.logout;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.components.LogOutConfirmModal;
import reports.ExtentReportManager;

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
        loginPage.enterAccount("a68cf217-d33b-4132-b180-864697ac8427");
        loginPage.enterPassword("Test123456@");
        loginPage.clickLogin();
        String actualLoginMsg = loginPage.getLoginMsg();
        Assert.assertEquals(actualLoginMsg, "Đăng nhập thành công", "Login message failed!");

    }

    private String lsGet(String key) {
        return (String) ((JavascriptExecutor) driver).executeScript(
                "return window.localStorage.getItem(arguments[0]);", key);
    }

    private Long lsLength() {
        return (Long) ((JavascriptExecutor) driver).executeScript(
                "return window.localStorage.length;");
    }

    @Test(description = "Logout modal - Cancel should stay logged in")
    public void TC01_Logout_Modal_Cancel_Should_Stay_Logged_In() {

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
        confirmModal.waitModalClosed();

        Assert.assertTrue(homePage.getTopBarNavigation().isLoggedInIndicatorVisible(),
                "User should remain logged in after cancel");
    }


    @Test
    public void TC03_Double_Click_Logout_Should_Show_Only_One_Modal() {

        ExtentReportManager.info("Login with valid credentials");
        loginAsValidUser();

        ExtentReportManager.info("Click Đăng Xuất multiple times quickly");
        homePage.getTopBarNavigation().clickLogoutButton();
        homePage.getTopBarNavigation().clickLogoutButton();

        confirmModal.waitLogoutConfirmModalVisible();
        Assert.assertTrue(confirmModal.isVisible(), "Modal should appear");

        // count elements to make sure only one modal exists
        int modalCount = driver.findElements(org.openqa.selenium.By.cssSelector(".modal, .MuiDialog-root, [role='dialog']")).size();
        Assert.assertEquals(modalCount, 1, "Only one modal should be shown");
    }

    @Test(description = "Check token cleared after logout")
    public void TC_Check_Token_Cleared_After_Logout() {
        String TOKEN_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiN2I4N2M4MzEtZjQxYS00OWRmLWJkOTctMDRmNTA1NTExODU0IiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy9yb2xlIjoiS2hhY2hIYW5nIiwibmJmIjoxNzY3MDkxMDMxLCJleHAiOjE3NjcwOTQ2MzF9.HMoBgFBilehuTr8_ozMorrIVB4myne9sWfMeTJ7buLA";

        // 1) Login
        loginAsValidUser();

        // 2) Pre-condition: token must exist
        String tokenBefore = (String) ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("return window.localStorage.getItem(arguments[0]);", TOKEN_KEY);

        Assert.assertNotNull(tokenBefore, "Pre-condition failed: accessToken not found before logout");

        // 3) Logout OK
        homePage.getTopBarNavigation().clickLogoutButton();
        confirmModal.waitLogoutConfirmModalVisible();
        confirmModal.clickOkButton();

        // 4) Assert token cleared
        String tokenAfter = (String) ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("return window.localStorage.getItem(arguments[0]);", TOKEN_KEY);

        Assert.assertNull(tokenAfter, "accessToken should be removed from localStorage after logout");


        // 5) Strong check: /account is blocked
        driver.get("https://demo1.cybersoft.edu.vn/account");
        Assert.assertTrue(driver.getCurrentUrl().equals("https://demo1.cybersoft.edu.vn/")
                        || driver.getCurrentUrl().contains("/sign-in"),
                "After logout, /account should be blocked. Actual: " + driver.getCurrentUrl());
    }


}

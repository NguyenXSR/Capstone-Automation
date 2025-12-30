package testcases.accountpage;

import base.BaseTest;
import drivers.DriverFactory;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import reports.ExtentReportManager;

@Listeners(TestListener.class)
public class AccountPageTest extends BaseTest {


    private AccountPage accountPage;
    private LoginPage loginPage;
    private HomePage homePage;


    @BeforeClass
    public void setUpPages() {
        accountPage = new AccountPage();
        loginPage = new LoginPage();
        homePage = new HomePage();
    }

    @BeforeMethod
    public void setLoginPage() {
        //Step 0: Login first
        ExtentReportManager.info("Step 0: Login first");
        LOG.info("Step 0: Login first");
        homePage.getTopBarNavigation().navigateLoginPage();
        loginPage.enterAccount("7b87c831-f41a-49df-bd97-04f505511854");
        loginPage.enterPassword("Test123456@");
        loginPage.clickLogin();

        String actualLoginMsg = loginPage.getLoginMsg();
        Assert.assertEquals(actualLoginMsg, "Đăng nhập thành công", "Login message failed!");

    }


    @Test(description = "TC_Verify_Update_Profile_Success_With_Valid_Data")
    public void TC_Verify_Update_Profile_Success_With_Valid_Data() {

        String fullName = "UserA";
        String email = fullName + "@example.com";
        String phone = "0123456789";
        String password = "Test123@@@";


        //Step 1: Navigate to Account Page
        ExtentReportManager.info("Step 1: Navigate to Account Page");
        LOG.info("Step 1: Navigate to Account Page");
        accountPage.openAccountPage();

        //Step 2: Update editable profile information
        ExtentReportManager.info("Step 2: Update profile information");
        LOG.info("Step 2: Update profile information");
        accountPage.setFullName(fullName);
        accountPage.setEmail(email);
        accountPage.setPhone(phone);
        accountPage.setPassword(password);


        //step 3 : Verify password visibility toggle
        ExtentReportManager.info("Step 3: Verify password visibility toggle");
        LOG.info("Step 3: Verify password visibility toggle");
        Assert.assertEquals(accountPage.getPasswordInputType(), "password", "Password input type is not correct");
        accountPage.togglePasswordVisibility();
        Assert.assertEquals(accountPage.getPasswordInputType(), "text", "Password input type is not correct");

        //Step 4: read only fields check
        ExtentReportManager.info("Step 4: read only fields check");
        LOG.info("Step 4: read only fields check");
        Assert.assertTrue(accountPage.isUserNameDisplayed(), "Username field is not displayed");
        Assert.assertTrue(accountPage.isUserRoleDisplayed(), "User role field is not displayed");
//        Assert.assertFalse(accountPage.isUserNameEditable(), "Username field should not be editable");
//        Assert.assertFalse(accountPage.isUserRoleEditable(), "User role field should not be  editable");

        //Step 5: Click Update button
        ExtentReportManager.info("Step 5: Click Update button");
        LOG.info("Step 5: Click Update button");
        accountPage.clickUpdateButton();

        //Step 6: Verify update success message
        ExtentReportManager.info("Step 6: Verify update success message");
        LOG.info("Step 6: Verify update success message");
        accountPage.waitModalVisible();
        String actualUpdateMsg = accountPage.getModalMessage();
        Assert.assertEquals(actualUpdateMsg, "Cập nhật thành công", "Update account message is not correct");
        ExtentReportManager.pass("All STEPS ABOVE PASSED");

        //Step 6 refresh page and verify updated info is saved
        ExtentReportManager.info("Step 7: Refresh page and verify updated info is saved");
        LOG.info("Step 7: Refresh page and verify updated info is saved");
        DriverFactory.getDriver().navigate().refresh();
        Assert.assertEquals(accountPage.getFullNameValue(), fullName, "Full name is not updated correctly");
        Assert.assertEquals(accountPage.getEmailValue(), email, "Email is not updated correctly");
        Assert.assertEquals(accountPage.getPhoneValue(), phone, "Phone is not updated correctly");

        ExtentReportManager.fail("BUG FOUND: PHONE NOT UPDATED PROPERLY, INTENDED TO DEMO SCREENSHOT ON REPORT");

    }

    //validation test cases

    @Test(description = "TC_Verify_Update_Profile_Failure_With_Invalid_FullName")
    public void TC_Verify_Update_Profile_Failure_With_Invalid_FullName() {
        //Step 1: Navigate to Account Page
        ExtentReportManager.info("Step 1: Navigate to Account Page");
        LOG.info("Step 1: Navigate to Account Page");
        accountPage.openAccountPage();

        //Step 2: Update profile information with invalid full name
        ExtentReportManager.info("Step 2: Update profile information with invalid full name");
        LOG.info("Step 2: Update profile information with invalid full name");
        accountPage.setFullName("UserA123"); //invalid full name
        accountPage.clickUpdateButton();
        String fullNameErrorMsg = accountPage.getFullNameError();
        Assert.assertEquals(fullNameErrorMsg, "Họ và tên không chứa số !"
                , "Full name validation message is not correct");
        ExtentReportManager.pass("PASSED AS FULL NAME VALIDATION WORKS");

    }

    //test invalid email without special character '@'
    //bug: no email validation shown at all
    @Test(description = "TC_Verify_Update_Profile_Failure_With_Invalid_Email_Without_@")
    public void TC_Verify_Update_Profile_Failure_With_Invalid_Email_Without_Special_Character() {
        //Step 1: Navigate to Account Page
        ExtentReportManager.info("Step 1: Navigate to Account Page");
        LOG.info("Step 1: Navigate to Account Page");
        accountPage.openAccountPage();


        //Step 2: Update profile information with invalid email
        ExtentReportManager.info("Step 2: Verify error message is displayed for invalid email without '@'");
        LOG.info("Step 2: Verify error message is displayed for invalid email without '@'");
        accountPage.setEmail("testuser.gmail.com"); //invalid email - no special character '@'
        accountPage.clickUpdateButton();
        String error = accountPage.getEmailErrorNegative();
        if (error.isEmpty()) {
            ExtentReportManager.fail(
                    "BUG: Invalid email 'testuser.gmail.com' but no validation message is displayed"
            );
        }

        Assert.assertFalse(
                error.isEmpty(),
                "BUG: Invalid email but no error message is displayed"
        );


    }


    // BUG test invalid email having spaces
    @Test(description = "TC_Verify_Update_Profile_Failure_With_Invalid_Email_With_Spaces")
    public void TC_Verify_Update_Profile_Failure_With_Invalid_Email_With_Spaces() {
        //Step 1: Navigate to Account Page
        ExtentReportManager.info("Step 1: Navigate to Account Page");
        LOG.info("Step 1: Navigate to Account Page");
        accountPage.openAccountPage();


        //Step 2: Update profile information with invalid email
        ExtentReportManager.info("Step 2: Update profile information with invalid email");
        LOG.info("Step 2: Update profile information with invalid email");
        accountPage.setEmail("test A@gmail.com"); //invalid email - having spaces
        accountPage.clickUpdateButton();

        //Step 2 verify error message must be displayed
        ExtentReportManager.info("Step 2 Verify validation error message is displayed");
        String emailErrorMsg = accountPage.getEmailErrorNegative();

        if (emailErrorMsg.isEmpty()) {
            ExtentReportManager.fail("BUG: Invalid email with spaces is accepted and profile is updated successfully");
        }

        Assert.assertFalse(emailErrorMsg.isEmpty(),
                "BUG: Invalid email with spaces but no validation message is displayed");

        Assert.assertEquals(emailErrorMsg, "Email không đúng định dạng !",
                "Email validation message is incorrect");
    }

    // test invalid phone number with characters
    @Test(description = "TC_Verify_Update_Profile_Failure_With_Invalid_Phone_With_Characters")
    public void TC_Verify_Update_Profile_Failure_With_Invalid_Phone_With_Characters() {
        //Step 1: Navigate to Account Page
        ExtentReportManager.info("Step 1: Navigate to Account Page");
        LOG.info("Step 1: Navigate to Account Page");
        accountPage.openAccountPage();

        //Step 2: Update profile information with invalid phone number
        ExtentReportManager.info("Step 2: Update profile information with invalid phone number");
        LOG.info("Step 2: Update profile information with invalid phone number");
        accountPage.setPhone("01234ABCD"); //invalid phone number - having characters
        accountPage.clickUpdateButton();
        String phoneErrorMsg = accountPage.getPhoneError();
        Assert.assertFalse(phoneErrorMsg.isBlank(),
                "Error message for phone number is displayed");
        ExtentReportManager.pass("PASSED");

    }

    //test invalid phone number with less than 10 digits
    @Test(description = "TC_Verify_Update_Profile_Failure_With_Invalid_Phone_Less_Than_10_Digits")
    public void TC_Verify_Update_Profile_Failure_With_Invalid_Phone_Less_Than_10_Digits() {
        //Step 1: Navigate to Account Page
        ExtentReportManager.info("Step 1: Navigate to Account Page");
        LOG.info("Step 1: Navigate to Account Page");
        accountPage.openAccountPage();

        //Step 2: Update profile information with invalid phone number
        ExtentReportManager.info("Step 2: Update profile information with invalid phone number");
        LOG.info("Step 2: Update profile information with invalid phone number");
        accountPage.setPhone("01234567"); //invalid phone number - less than 10 digits
        accountPage.clickUpdateButton();

        //Step 2 verify error message must be displayed
        ExtentReportManager.info("Step 2: Verify validation error message is displayed for invalid phone number");

        String phoneErrorMsg = accountPage.getPhoneErrorNegative();

        if (phoneErrorMsg.isEmpty()) {
            ExtentReportManager.fail("BUG: Phone number with less than 10 digits is accepted and profile is updated");
        }

        Assert.assertFalse(phoneErrorMsg.isEmpty(),
                "BUG: Invalid phone number (<10 digits) but no validation message is displayed");

        Assert.assertEquals(phoneErrorMsg, "Số điện thoại không đúng định dạng !",
                "Phone number validation message is incorrect");

    }


    //  test invalid phone number with more than 10 digits
    @Test(description = "TC_Verify_Update_Profile_Failure_With_Invalid_Phone_More_Than_10_Digits")
    public void TC_Verify_Update_Profile_Failure_With_Invalid_Phone_More_Than_10_Digits() {
        //Step 1: Navigate to Account Page
        ExtentReportManager.info("Step 1: Navigate to Account Page");
        LOG.info("Step 1: Navigate to Account Page");
        accountPage.openAccountPage();


        //Step 2: Update profile information with invalid phone number
        ExtentReportManager.info("Step 2: Update profile information with invalid phone number");
        LOG.info("Step 2: Update profile information with invalid phone number");
        accountPage.setPhone("0123456789123"); //invalid phone number - more than 10 digits
        accountPage.clickUpdateButton();
        ExtentReportManager.info("Step 2: Verify validation error message is displayed for invalid phone number");

        //Step 2 verify error message must be displayed

        String phoneErrorMsg = accountPage.getPhoneErrorNegative();

        if (phoneErrorMsg.isEmpty()) {
            ExtentReportManager.fail("BUG: Phone number with more than 10 digits is accepted and profile is updated");
        }

        Assert.assertFalse(phoneErrorMsg.isEmpty(),
                "BUG: Invalid phone number (>10 digits) but no validation message is displayed");

        Assert.assertEquals(phoneErrorMsg, "Số điện thoại không đúng định dạng !",
                "Phone number validation message is incorrect");
    }

    //test invalid password less than 6 characters
    @Test(description = "TC_Verify_Update_Profile_Failure_With_Invalid_Password_Less_Than_6_Characters")
    public void TC_Verify_Update_Profile_Failure_With_Invalid_Password_Less_Than_6_Characters() {
        //Step 1: Navigate to Account Page
        ExtentReportManager.info("Step 1: Navigate to Account Page");
        LOG.info("Step 1: Navigate to Account Page");
        accountPage.openAccountPage();
        //Step 2: Update profile information with invalid password
        ExtentReportManager.info("Step 2: Update profile information with invalid password");
        LOG.info("Step 2: Update profile information with invalid password");
        accountPage.setPassword("Ab1@"); //invalid password - less than 6 characters
        accountPage.clickUpdateButton();
        String passwordErrorMsg = accountPage.getPasswordError();
        Assert.assertEquals(passwordErrorMsg, "Mật khẩu phải có ít nhất 6 kí tự !"
                , "Password validation message is not correct");
        ExtentReportManager.pass("PASSED");
    }

    //test invalid password having leading spaces
    @Test(description = "TC_Verify_Update_Profile_Failure_With_Invalid_Password_Having_Leading_Spaces")
    public void TC_Verify_Update_Profile_Failure_With_Invalid_Password_Having_Leading_Spaces() {
        //Step 1: Navigate to Account Page
        ExtentReportManager.info("Step 1: Navigate to Account Page");
        LOG.info("Step 1: Navigate to Account Page");
        accountPage.openAccountPage();
        //Step 2: Update profile information with invalid password
        ExtentReportManager.info("Step 2: Update profile information with invalid password having leading spaces");
        LOG.info("Step 2: Update profile information with invalid password having leading spaces");
        accountPage.setPassword("  Abc123@"); //invalid password - having leading spaces
        accountPage.clickUpdateButton();


        //Step 2 verify error message must be displayed
        ExtentReportManager.info("Step 2 Verify validation error message is displayed for invalid password");

        String passwordErrorMsg = accountPage.getPasswordErrorNegative();

        if (passwordErrorMsg.isEmpty()) {
            ExtentReportManager.fail("BUG: Password with leading spaces is accepted and profile is updated");
        }

        Assert.assertFalse(passwordErrorMsg.isEmpty(),
                "BUG: Invalid password with leading spaces but no validation message is displayed");

        Assert.assertEquals(passwordErrorMsg, "Mật khẩu không được chứa khoảng trắng !",
                "Password validation message is incorrect");
    }




}
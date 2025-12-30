package testcases.register;

import base.BaseTest;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.LoginPage;
import pages.RegisterPage;
import reports.ExtentReportManager;

@Listeners(TestListener.class)
public class TC01_RegisterTest extends BaseTest {

    private RegisterPage registerPage;
    private HomePage homePage;
    private LoginPage loginPage;

    @BeforeMethod
    public void setupPages() {
        registerPage = new RegisterPage();
        homePage = new HomePage();
        loginPage = new LoginPage();
        homePage.getTopBarNavigation().navigateRegisterPage();

    }

    @Test(description = "TC_Register_Success_When_Account_Not_Exist")
    public void TC_Register_Success_When_Account_Not_Exist() {
        //Step 1: Click link "Đăng Ký"
        ExtentReportManager.info("Step 1: Click link 'Đăng Ký'");
        LOG.info("Step 1: Click link 'Đăng Ký'");

        //Step 2: Enter account
        ExtentReportManager.info("Step 2: fill registration form with valid data");
        LOG.info("Step 2: fill registration form with valid data");
<<<<<<< HEAD
//        String account = UUID.randomUUID().toString();
//        System.out.println("account = " + account);
//        registerPage.enterAccount(account);
//
//        //Step 3: Enter password
//        ExtentReportManager.info("Step 3: Enter password");
//        LOG.info("Step 3: Enter password");
//        registerPage.enterPassword("Test123456@");
//
//        //Step 4: Enter confirm password
//        ExtentReportManager.info("Step 4: Enter confirm password");
//        LOG.info("Step 4: Enter confirm password");
//        registerPage.enterConfirmPassword("Test123456@");
//
//        //Step 5: Enter name
//        ExtentReportManager.info("Step 5: Enter name");
//        LOG.info("Step 5: Enter name");
//        registerPage.enterName("John A");
//
//        //Step 6: Enter email
//        ExtentReportManager.info("Step 6: Enter email");
//        LOG.info("Step 6: Enter email");
//        String email = account + "@example.com";
//        registerPage.enterEmail(email);

        String account = String.valueOf(System.currentTimeMillis());
        registerPage.fillForm("tester_" + account,
                "tester_" + account + "@example.com",
                "StrongPass123",
                "StrongPass123",
=======
        String account = String.valueOf(System.currentTimeMillis());
        registerPage.fillForm("tester_" + account,
                "tester_" + account + "@example.com",
                "Test123456@",
                "Test123456@",
>>>>>>> f42ce23 (Handle extra cases for  register page and login page)
                "Tester A");


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
        loginPage.enterAccount("tester_"+ account);


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

    // Toggle pw eye icon
    @Test
    public void TC_Toggle_Password_Eye_Icon() {
        ExtentReportManager.info("TC_Toggle_Password_Eye_Icon");
        LOG.info("TC_Toggle_Password_Eye_Icon");
        registerPage.enterPassword("StrongPass123");
        Assert.assertEquals(registerPage.getPasswordType(), "password");

        registerPage.togglePassword();
        Assert.assertEquals(registerPage.getPasswordType(), "text");

        registerPage.togglePassword();
        Assert.assertEquals(registerPage.getPasswordType(), "password");
    }

    @Test
    public void TC_Already_Have_Account_Link_Redirects_Login() {
        ExtentReportManager.info("TC_Already_Have_Account_Link_Redirects_Login");
        LOG.info("TC_Already_Have_Account_Link_Redirects_Login");
        registerPage.clickAlreadyHaveAccount();
        Assert.assertTrue(driver.getCurrentUrl().contains("/sign-in"), "Should redirect to login page");
    }

    @DataProvider(name = "negativeRegisterData")
    public Object[][] negativeRegisterData() {
        return new Object[][]{
            // testcaseName, username, email, pass, confirm, full name, expectedType, expectedField, expectedContains

            // Leave required fields empty
            {"Required fields empty", "", "", "", "","", "FIELD", "username", "required"},

            // Username already exists (requires pre-created user)
            {"Username already exists", "tester01", "", "", "","John S", "GLOBAL", "", "username already exists"},

            // Email already exists
            {"Email already exists", "newuser123", "tester01@example.com", "StrongPass123", "StrongPass123", "John S","GLOBAL", "", "email"},

            // Passwords do not match
            {"Passwords do not match", "newuser123", "newuser123@example.com", "StrongPass123", "StrongPass124","John S","FIELD", "confirm", "match"},

            // Invalid email format
            {"Invalid email missing @", "newuser123", "test.user.example.com", "StrongPass123", "StrongPass123","John S", "FIELD", "email", "valid"},

            // Password too short
            {"Password too short", "newuser123", "newuser123@example.com", "123", "123","John S", "FIELD", "password", "length"},

            //Full name with numbers
            {"Full name with numbers", "newuser123", "newuser123@example.com", "StrongPass123", "StrongPass123","John123", "FIELD", "name", "invalid"},

            // full name with special characters
            {"Full name with special characters", "newuser123", "newuser123@example.com", "StrongPass123", "StrongPass123","John@#", "FIELD", "name", "invalid"},

<<<<<<< HEAD
=======
             // invalid password (leading/trailing  spaces)
            {"Invalid password with leading/trailing spaces", "newuser123", "newuser123@example.com", "  StrongPass123  ", "  StrongPass123  ","John S", "FIELD", "password", "no leading or trailing spaces"}
>>>>>>> f42ce23 (Handle extra cases for  register page and login page)

    };


    }

    @Test(dataProvider = "negativeRegisterData", description = "TC_Negative_Register_Test_Cases")
    public void TC_Negative_Register_Test_Cases(String testcaseName,
                                                String username,
                                                String email,
                                                String password,
                                                String confirmPassword,
                                                String fullName,
                                               String expectedType, //FIELD or GLOBAL
                                                String expectedField, // which field has error
                                                String expectedContains) {
        ExtentReportManager.info("TC_Negative_Register_Test_Cases - " + testcaseName);
        LOG.info("TC_Negative_Register_Test_Cases - " + testcaseName);
        // Enter registration details
        registerPage.fillForm(username, email, password, confirmPassword, fullName);
        // Click register button
        registerPage.clickRegister();

        if (expectedType.equals("FIELD")) {
            // Field-level validation
            String fieldErrorMsg = registerPage.getFieldErrorMessage(expectedField);
            Assert.assertTrue(fieldErrorMsg.toLowerCase().contains(expectedContains.toLowerCase()),
                    String.format("Expected field error message for '%s' to contain '%s', but got '%s'",
                            expectedField, expectedContains, fieldErrorMsg));
        } else if (expectedType.equals("GLOBAL")) {
            // Global validation
            String globalErrorMsg = registerPage.getGlobalMessage();
            Assert.assertTrue(globalErrorMsg.toLowerCase().contains(expectedContains.toLowerCase()),
                    String.format("Expected global error message to contain '%s', but got '%s'",
                            expectedContains, globalErrorMsg));
        } else {
            Assert.fail("Invalid expectedType provided in test data.");
        }



    }

    @Test
    public void TC_Double_Click_Register_Only_One_Request() {
        ExtentReportManager.info("TC_Double_Click_Register_Only_One_Request");
        LOG.info("TC_Double_Click_Register_Only_One_Request");

        String unique = String.valueOf(System.currentTimeMillis());
        registerPage.fillForm("tester_" + unique,
                "tester_" + unique + "@example.com",
                "StrongPass123",
                "StrongPass123",
                "Tester A");

        registerPage.clickRegister();
        registerPage.clickRegister(); // click nhanh lần 2

        // success message
        ExtentReportManager.info("VP3.1: 'Đăng nhập thành công' message displays");
        LOG.info("VP3.1: 'Đăng nhập thành công' message displays");
        String actualLoginMsg = loginPage.getLoginMsg();
        Assert.assertEquals(actualLoginMsg, "Đăng nhập thành công", "Login message failed!");

    }




}









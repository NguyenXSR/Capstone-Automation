package pages;

import base.BasePage;
import drivers.DriverFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class AccountPage extends BasePage {

    private WebDriver driver() {
        return DriverFactory.getDriver();
    }

    //open account page after login
    public void openAccountPage() {
        driver().get("https://demo1.cybersoft.edu.vn/account");
        //this line to ensure page is loaded, the locator is Cài đặt tài khoản chung
        waitForVisibilityOfElementLocated(driver(), By.xpath("//main/form/div/div/h1"));
    }

    //Editable fields
    private final By ByFullNameInput = By.cssSelector("input#hoTen");
    private final By ByEmailInput = By.cssSelector("input#email");
    private final By ByPhoneInput = By.cssSelector("input#soDt");
    private final By ByPasswordInput = By.cssSelector("input#matKhau");
    private final By ByUpdateBtn = By.xpath("//button[normalize-space()='Cập Nhật']");

    //read-only fields
    private final By ByUsernameInput = By.cssSelector("input#taiKhoan");
    private final By ByUserRoleField = By.cssSelector("select[name='maLoaiNguoiDung']");

    //password eye icon
    private final By ByPasswordEyeIcon = By.cssSelector("button.MuiIconButton-edgeEnd");

    // validation message
    private By ByMessageError(By inputLocator) {
        return By.xpath(
                toXpath(inputLocator)
                        + "/ancestor::div[contains(@class,'MuiFormControl-root')]"
                        + "//p[contains(@class,'MuiFormHelperText-root')]"
        );
    }

    private String toXpath(By locator) {
        return "//*";
    }

    //modal update account success
    private final By ByUpdateSuccessModal = By.cssSelector(".swal2-popup.swal2-modal");
    private final By ByUpdateSuccessModalContent = By.cssSelector("h2#swal2-title");


    //  clear input field and type value

    private void clearAndType(By locator, String value) {
        WebElement element = waitForVisibilityOfElementLocated(driver(), locator);

        // Focus first (good for MUI)
        element.click();

        // Clear reliably
        element.sendKeys(Keys.COMMAND + "a");
        element.sendKeys(Keys.DELETE);

        // Type new value
        if (value != null) {
            element.sendKeys(value);
        }
    }


//    private void clearAndType(By locator, String value) {
//        WebElement element = waitForVisibilityOfElementLocated(driver(), locator);
//
//        // Focus the element first (important for MUI)
//        element.click();
//
//        // Clear using multiple methods for MUI reliability
//        clearMUIField(element);
//
//        // Type new value with proper React event triggering
//        if (value != null && !value.isEmpty()) {
//            element.sendKeys(value);
//
//            // Trigger blur to ensure React state updates
//            triggerBlurEvent(element);
//        }
//    }
//
//    private void clearMUIField(WebElement element) {
//        try {
//            // Method 1: Select all and delete (works well with React)
//            element.sendKeys(Keys.COMMAND + "a");
//            element.sendKeys(Keys.BACK_SPACE);
//
//            // Method 2: If text still exists, use standard clear
//            if (!element.getAttribute("value").isEmpty()) {
//                element.clear();
//            }
//
//            // Method 3: JavaScript fallback with React event simulation
//            if (!element.getAttribute("value").isEmpty()) {
//                JavascriptExecutor js = (JavascriptExecutor) driver();
//                js.executeScript(
//                        "arguments[0].value = '';" +
//                                "arguments[0].dispatchEvent(new Event('input', {bubbles: true}));" +
//                                "arguments[0].dispatchEvent(new Event('change', {bubbles: true}));",
//                        element
//                );
//            }
//        } catch (Exception e) {
//            // Ultimate fallback
//            JavascriptExecutor js = (JavascriptExecutor) driver();
//            js.executeScript("arguments[0].value = ''; arguments[0].focus();", element);
//        }
//    }
//
//    private void triggerBlurEvent(WebElement element) {
//        try {
//            JavascriptExecutor js = (JavascriptExecutor) driver();
//            js.executeScript("arguments[0].blur();", element);
//        } catch (Exception e) {
//            // If blur fails, just continue
//        }
//    }




    public void setFullName(String fullName) {
        LOG.info(String.format("enterFullName: %s", fullName));
        clearAndType(ByFullNameInput, fullName);
    }

    public void setEmail(String email) {
        LOG.info(String.format("enterEmail: %s", email));
        clearAndType(ByEmailInput, email);
    }

    public void setPhone(String phone) {
        LOG.info(String.format("enterPhone: %s", phone));
        clearAndType(ByPhoneInput, phone);
    }

    public void setPassword(String password) {
        LOG.info(String.format("enterPassword: %s", password));
        clearAndType(ByPasswordInput, password);
    }

    public void clickUpdateButton() {
        LOG.info("clickUpdateButton");
        click(driver(), ByUpdateBtn);
    }

    //password eye icon
    public void togglePasswordVisibility() {
        LOG.info("togglePasswordVisibility");
        click(driver(), ByPasswordEyeIcon);
    }

    //read only fields check
    public boolean isUserNameDisplayed() {
        return waitForVisibilityOfElementLocated(driver(), ByUsernameInput).isDisplayed();
    }

    public boolean isUserRoleDisplayed() {
        return waitForVisibilityOfElementLocated(driver(), ByUserRoleField).isDisplayed();
    }

    public boolean isUserNameEditable() {
        WebElement el = waitForVisibilityOfElementLocated(driver(), ByUsernameInput);
        return el.getTagName().equalsIgnoreCase("input") && el.isEnabled();
    }

    public boolean isUserRoleEditable() {
        WebElement el = waitForVisibilityOfElementLocated(driver(), ByUserRoleField);
        return el.getTagName().equalsIgnoreCase("select") && el.isEnabled();
    }

    //check pw visibility
    public String getPasswordInputType() {
        WebElement el = waitForVisibilityOfElementLocated(driver(), ByPasswordInput);
        return el.getAttribute("type");
    }

    //modal update account success
    public void waitModalVisible() {
      //  waitForVisibilityOfElementLocated(driver(), ByUpdateSuccessModal);
        // wait for presence first because the modal isn't in the DOM by default
        org.openqa.selenium.support.ui.WebDriverWait wait =
                new org.openqa.selenium.support.ui.WebDriverWait(driver(), java.time.Duration.ofSeconds(15));
        wait.pollingEvery(java.time.Duration.ofMillis(500))
                .ignoring(org.openqa.selenium.NoSuchElementException.class)
                .ignoring(org.openqa.selenium.StaleElementReferenceException.class)
                .until(org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated(ByUpdateSuccessModal));

        // then wait for it to be visible
        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated(ByUpdateSuccessModal));

    }

    public String getModalMessage() {
        return getText(driver(), ByUpdateSuccessModalContent);
    }

   // error messages
    public String getFullNameError() {
        return getText(driver(), ByMessageError(ByFullNameInput));
    }



    public String getEmailError() {
        return getText(driver(), ByMessageError(ByEmailInput));
    }




    public String getPhoneError() {
        return getText(driver(), ByMessageError(ByPhoneInput));
    }

    public String getPasswordError() {
        return getText(driver(), ByMessageError(ByPasswordInput));
    }

    // get current value after update and refresh
    public String getFullNameValue() {
        WebElement el = waitForVisibilityOfElementLocated(driver(), ByFullNameInput);
        return el.getAttribute("value");
    }
    public String getEmailValue() {
        WebElement el = waitForVisibilityOfElementLocated(driver(), ByEmailInput);
        return el.getAttribute("value");
    }
    public String getPhoneValue() {
        WebElement el = waitForVisibilityOfElementLocated(driver(), ByPhoneInput);
        return el.getAttribute("value");
    }

    //for negative test
    //getEmailError for negative test
    public String getEmailErrorNegative() {
        List<WebElement> errors =
                driver().findElements(ByMessageError(ByEmailInput));

        if (errors.isEmpty()) {
            return "";
        }
        return errors.get(0).getText().trim();
    }


    public String getPhoneErrorNegative() {
        List<WebElement> errors = driver().findElements(ByMessageError(ByPhoneInput));

        if (errors.isEmpty()) {
            return "";
        }
        return errors.get(0).getText().trim();
    }

    public String getPasswordErrorNegative() {
        List<WebElement> errors =
                driver().findElements(ByMessageError(ByPasswordInput));

        if (errors.isEmpty()) {
            return "";
        }
        return errors.get(0).getText().trim();
    }

}

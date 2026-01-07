package pages;

import drivers.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class LoginPage extends CommonPage {
    private By byTxtAccountLogin = By.id("taiKhoan");
    private By byTxtPasswordLogin = By.id("matKhau");
    private By byBtnLoginForm = By.xpath("//button[.='Đăng nhập']");
    private By byLblLoginMsg = By.id("swal2-title");

    private  By rememberCheckbox = By.cssSelector("input[name='remember'][type='checkbox']");
    private  By eyeIcon          = By.cssSelector(".MuiIconButton-edgeEnd");

    //error messages
    private  By byUsernameRequiredError = By.id("taiKhoan-helper-text");
    private  By byPasswordRequiredError = By.id("matKhau-helper-text");
    private  By byInvalidCredsError     = By.cssSelector(".MuiAlert-standardError");

    private WebDriver driver() {
        return  DriverFactory.getDriver();
    }

    public void enterAccount(String account) {
        LOG.info(String.format("enterAccount: %s", account));
        sendKeys(driver(), byTxtAccountLogin, account);
    }

    public void enterPassword(String password) {
        LOG.info(String.format("enterPassword: %s", password));
        sendKeys(driver(), byTxtPasswordLogin, password);
    }

    public void clickLogin() {
        LOG.info("clickLogin");
        click(driver(), byBtnLoginForm);
    }

    public String getLoginMsg() {
        LOG.info("getLoginMsg");
        return getText(driver(), byLblLoginMsg);
    }

    public void pressEnterToLogin() {
        waitForVisibilityOfElementLocated(driver(), byTxtPasswordLogin).sendKeys(Keys.ENTER);
    }



    public void togglePasswordVisibility() {
        click(driver(), eyeIcon);
    }

    public String getPasswordType() {
        return waitForVisibilityOfElementLocated(driver(), byTxtPasswordLogin).getAttribute("type");
    }

    public void checkRememberAccount() {
        WebElement cb = waitForVisibilityOfElementLocated(driver(), rememberCheckbox);
        if (!cb.isSelected()) cb.click();
    }

    public String getUsernameValue() {
        return waitForVisibilityOfElementLocated(driver(),byTxtAccountLogin).getAttribute("value");
    }

    // error getters
    public String getUsernameRequiredError() { return getText(driver(), byUsernameRequiredError); }
    public String getPasswordRequiredError() { return getText(driver(), byPasswordRequiredError); }
    public String getInvalidCredsError()     { return getText(driver(), byInvalidCredsError); }


    public void waitLoginMsgDisappear() {
        LOG.info("waitLoginMsgDisappear");
        waitForInvisibilityOfElementLocated(driver(), byLblLoginMsg);
    }

    public void clearAndEnterAccount(String account) {
        clearAndEnter(byTxtAccountLogin, account);
    }


}
